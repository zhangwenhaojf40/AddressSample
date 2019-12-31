package zz.hao.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import zz.hao.sample.bean.*
import zz.hao.sample.dialog.DialogBottom
import zz.hao.sample.dialog.DialogAddress
import zz.hao.sample.util.StreamTools

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val stringJson = StreamTools.getStringJson("address.json", this)
        val gson = Gson()
        val bean = gson.fromJson<AddressBean>(stringJson, AddressBean::class.java)
        //选择地址，回调结果
        val dialogUtils= DialogAddress(
            bean,
            this,
            object : DialogAddress.ResultListent {
                override fun loadData(list: ArrayList<ResultBean>) {
                    //集合中第一个元素为省，第二个为市，第三个为县
                    var mAddress = StringBuffer()
                    for (i in 0 until list.size) {
                        mAddress.append(list.get(i).area_name).append("-")
//                          mAddress.append(addressList.get(i).area_id)
                    }
                    tvAddress.setText(mAddress.substring(0, mAddress.length - 1))

                }

            })

        tvAddress.setOnClickListener {

            dialogUtils.showDialog(tvAddress.text.split("-"))
        }

        btnBottom.setOnClickListener{
            DialogBottom.showDialog(this)
        }
    }
}
