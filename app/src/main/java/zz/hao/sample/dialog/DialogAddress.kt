package zz.hao.sample.util

import android.app.Activity
import android.content.res.Resources
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import zz.hao.sample.R
import zz.hao.sample.bean.AddressBean
import zz.hao.sample.bean.City
import zz.hao.sample.bean.Count
import zz.hao.sample.bean.ResultBean
import java.lang.Exception

/**
 * DESC:
 * Create By ZWH  On  2019/12/31 0031
 */
class DialogUtils(val bean: AddressBean,val act:Activity,val resultListent:ResultListent ) {
    lateinit var resultArr: List<String>
    var cityList: List<City> = ArrayList()
    var countryList: List<Count> = ArrayList()
    lateinit var cityArray: Array<String?>
    lateinit var pickProvince: NumberPicker
    lateinit var pickCity: NumberPicker
    lateinit var pickCountry: NumberPicker
    lateinit var dialog: AlertDialog
    var resultProvince = ResultBean()
    var resultCity = ResultBean()
    var resultCountry = ResultBean()
    var addressList = ArrayList<ResultBean>()
    var currentIndexCity = 0

    init {
        createDialog()
    }

    fun createDialog() {
        dialog   = AlertDialog.Builder(act).create()
        val view = View.inflate(act, R.layout.dialog_address, null)
        val tvCancel = view.findViewById<TextView>(R.id.tvCancel)
        val tvSure = view.findViewById<TextView>(R.id.tvSure)

        //省级
        pickProvince = view.findViewById(R.id.pickProvince)
        //市级
        pickCity = view.findViewById(R.id.pickCity)
        //县级
        pickCountry = view.findViewById(R.id.pickCountry)
        dialog.setView(view)


        tvCancel.setOnClickListener {
            dialog.dismiss()
        }
        tvSure.setOnClickListener {
            dialog.dismiss()
            addressList.clear()
            addressList.add(resultProvince)
            addressList.add(resultCity)
            addressList.add(resultCountry)
           resultListent.loadData(addressList)

        }




    }

    private fun setCustom() {
        //移除可编辑
        pickProvince.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        pickCity.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        pickCountry.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        //设置分割线颜色
        setNumberPickerDividerColor(pickProvince,ContextCompat.getColor(act,R.color.colorAccent))
        setNumberPickerDividerColor(pickCity,ContextCompat.getColor(act,R.color.colorAccent))
        setNumberPickerDividerColor(pickCountry,ContextCompat.getColor(act,R.color.colorAccent))
        //无限滚动
        pickProvince.wrapSelectorWheel = false
        pickCity.wrapSelectorWheel = false
        pickCountry.wrapSelectorWheel = false
        //设置字体颜色
        setNumberPickerTextColor(pickProvince,ContextCompat.getColor(act,R.color.colorAccent))
        setNumberPickerTextColor(pickCity,ContextCompat.getColor(act,R.color.colorAccent))
        setNumberPickerTextColor(pickCountry,ContextCompat.getColor(act,R.color.colorAccent))
    }

    private fun changCountry(position: Int) {
        var currentIndexCountry = 0
        resultCity.area_name = cityList.get(position).area_name
        resultCity.area_id = cityList.get(position).area_id
        countryList = cityList.get(position).count
        val countryArr = arrayOfNulls<String>(countryList.size)
        for (i in 0 until countryList.size) {
            if (resultArr.size == 3 && resultArr.get(2).equals(countryList.get(i).area_name)) {
                currentIndexCountry = i
            }
            countryArr[i] = countryList.get(i).area_name
        }
        try {
            pickCountry.minValue = 0
            pickCountry.maxValue = countryArr.size - 1
            pickCountry.displayedValues = countryArr
            pickCountry.value = currentIndexCountry

        } catch (e: Exception) {
            pickCountry.displayedValues = countryArr
            pickCountry.minValue = 0
            pickCountry.maxValue = countryArr.size - 1
            pickCountry.value = currentIndexCountry
        }
        //为县级赋值，默认不滑动
        resultCountry.area_name = countryList.get(currentIndexCountry).area_name
        resultCountry.area_id = countryList.get(currentIndexCountry).area_id
    }

    private fun changCity(position: Int) {

        resultProvince.area_name = bean.data.get(position).area_name
        resultProvince.area_id = bean.data.get(position).area_id

        cityList = bean.data.get(position).city
        cityArray = arrayOfNulls(cityList.size)

        for (i in 0 until cityList.size) {
            //选中上次选择城市
            if (resultArr.size == 3 && cityList.get(i).area_name.equals(resultArr[1])) {

                currentIndexCity = i
            }
            cityArray[i] = cityList.get(i).area_name
        }
        //        市级
        try {

            pickCity.minValue = 0
            pickCity.maxValue = cityArray.size - 1
            pickCity.displayedValues = cityArray
            pickCity.value = currentIndexCity
        } catch (e: Exception) {
            pickCity.displayedValues = cityArray
            pickCity.minValue = 0
            pickCity.maxValue = cityArray.size - 1
            pickCity.value = currentIndexCity

        }
        changCountry(currentIndexCity)

    }

    //设置分割线颜色
    private fun setNumberPickerDividerColor(numberPicker: NumberPicker,color: Int) {
        val picker = numberPicker
        val pickerFields = NumberPicker::class.java.declaredFields
        for (pf in pickerFields) {
            if (pf.name == "mSelectionDivider") {
                pf.isAccessible = true
                try {
                    //设置分割线的颜色值
                    pf.set(picker, ColorDrawable(color))
                } catch (e: IllegalArgumentException) {
                    e.printStackTrace()
                } catch (e: Resources.NotFoundException) {
                    e.printStackTrace()
                }
                break
            }
        }
    }
    fun  showDialog(resultArr: List<String>){
        this.resultArr = resultArr

        var currentIndex = 0

        var provinceArray = arrayOfNulls<String>(bean.data.size)

        //组装数据
        for (i in 0 until bean.data.size) {
            if (resultArr.size == 3 && resultArr.get(0).equals(bean.data.get(i).area_name)) {
                currentIndex = i
            }
            provinceArray[i] = bean.data.get(i).area_name
        }
        //移除可编辑、设置分割线颜色
        setCustom()

        pickProvince.displayedValues = provinceArray
        pickProvince.minValue = 0
        pickProvince.maxValue = provinceArray.size - 1
        pickProvince.value = currentIndex



        changCity(currentIndex)
//        省级监听
        pickProvince.setOnValueChangedListener { numberPicker, i, i2 ->
            changCity(i2)

        }
        changCountry(currentIndexCity)
        //市级监听
        pickCity.setOnValueChangedListener { picker, oldVal, newVal ->

            changCountry(newVal)

        }
        pickCountry.setOnValueChangedListener { picker, oldVal, newVal ->

            resultCountry.area_name = countryList.get(newVal).area_name
            resultCountry.area_id = countryList.get(newVal).area_id

        }
        dialog.show()
    }

    //设置选择器字体颜色
    fun setNumberPickerTextColor(numberPicker: NumberPicker, color: Int): Boolean {
        var result = false
        val count = numberPicker.childCount
        for (i in 0 until count) {
            val child = numberPicker.getChildAt(i)
            if (child is EditText) {
                try {
                    val selectorWheelPaintField = numberPicker.javaClass
                        .getDeclaredField("mSelectorWheelPaint")
                    selectorWheelPaintField.isAccessible = true
                    (selectorWheelPaintField.get(numberPicker) as Paint).color = color
                    child.setTextColor(color)
                    numberPicker.invalidate()
                    result = true
                } catch (e: NoSuchFieldException) {
                    e.printStackTrace()
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                } catch (e: IllegalArgumentException) {
                    e.printStackTrace()
                }

            }
        }
        return result
    }

    interface ResultListent{
        fun loadData(list:ArrayList<ResultBean>)
    }
}