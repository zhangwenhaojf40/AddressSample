package zz.hao.myapplication;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;


import androidx.appcompat.app.AlertDialog;

import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class ChooseCityUtilB implements View.OnClickListener, NumberPicker.OnValueChangeListener {
      
        Context context;
        AlertDialog dialog;
        ChooseCityInterfaceB cityInterface;
        NumberPicker npProvince, npCity, npCounty;
        TextView tvCancel, tvSure;
    ArrayList<AddressBean> list = new ArrayList<>();
        CityBeanB beanB;
    private AddressBean addressBean0;
    private AddressBean addressBean1;
    private AddressBean addressBean2;

    public void createDialog(Context context, ArrayList<AddressBean> oldlist, ChooseCityInterfaceB cityInterface) {
            this.context = context;  
            this.cityInterface = cityInterface;
            Gson gson = new Gson();

            String body = StreamTools.getStringFromStream("address.json",context);
            beanB = gson.fromJson(body, CityBeanB.class);

        if (addressBean0 == null) {

            addressBean0 = new AddressBean();
        }
          if (addressBean1 == null) {

              addressBean1 = new AddressBean();
        }
          if (addressBean2 == null) {

              addressBean2 = new AddressBean();
        }

        addressBean0.area_name = oldlist.get(0).area_name;
        addressBean0.area_id = oldlist.get(0).area_id;

        addressBean1.area_name = oldlist.get(1).area_name;
        addressBean1.area_id = oldlist.get(1).area_id;

        addressBean2.area_name = oldlist.get(2).area_name;
        addressBean2.area_id = oldlist.get(2).area_id;

        list.add(addressBean0);
        list.add(addressBean1);
        list.add(addressBean2);
            dialog = new AlertDialog.Builder(context).create();
            dialog.show();  
            Window window = dialog.getWindow();
            window.setContentView(R.layout.dialog_choose_city);
            //初始化控件  
            tvCancel = (TextView) window.findViewById(R.id.tvCancel);
            tvSure = (TextView) window.findViewById(R.id.tvSure);
            tvCancel.setOnClickListener(this);  
            tvSure.setOnClickListener(this);  
            npProvince = (NumberPicker) window.findViewById(R.id.npProvince);
            npCity = (NumberPicker) window.findViewById(R.id.npCity);
            npCounty = (NumberPicker) window.findViewById(R.id.npCounty);
            setNomal();  
            //省：设置选择器最小值、最大值、初始值  
            String[] provinceArray = new String[beanB.data.size()];//初始化省数组
            for (int i = 0; i < provinceArray.length; i++) {//省数组填充数据  
                provinceArray[i] = beanB.data.get(i).area_name;
            }  
            npProvince.setDisplayedValues(provinceArray);//设置选择器数据、默认值  
            npProvince.setMinValue(0);  
            npProvince.setMaxValue(provinceArray.length - 1);  
            for (int i = 0; i < provinceArray.length; i++) {  
                if (provinceArray[i].equals(list.get(0).area_name)) {
                    npProvince.setValue(i);  
                    changeCity(i);//联动市数据  
                }  
            }  
        }  
      
        //根据省,联动市数据  B
        private void changeCity(int provinceTag) {  
            List<CityBeanB.DataBean.CityBean> cityList = beanB.data.get(provinceTag).city;
            String[] cityArray = new String[cityList.size()];
            for (int i = 0; i < cityArray.length; i++) {  
                cityArray[i] = cityList.get(i).area_name;
            }
            try {
                npCity.setMinValue(0);  
                npCity.setMaxValue(cityArray.length - 1);  
                npCity.setWrapSelectorWheel(false);  
                npCity.setDisplayedValues(cityArray);//设置选择器数据、默认值  
            } catch (Exception e) {
                npCity.setDisplayedValues(cityArray);//设置选择器数据、默认值  
                npCity.setMinValue(0);  
                npCity.setMaxValue(cityArray.length - 1);  
                npCity.setWrapSelectorWheel(false);  
            }
            for (int i = 0; i < cityArray.length; i++) {  
                if (cityArray[i].equals(list.get(1).area_name)) {
                    npCity.setValue(i);  
                    changeCounty(provinceTag, i);//联动县数据  
                    return;  
                }  
            }  
            npCity.setValue(0);  
            changeCounty(provinceTag, npCity.getValue());//联动县数据  
        }  
      
        //根据市,联动县数据  
        private void changeCounty(int provinceTag, int cityTag) {  
            List<CityBeanB.DataBean.CityBean.CountBean> countyList = beanB.data.get(provinceTag).city.get(cityTag).count;
            String[] countyArray = new String[countyList.size()];
            for (int i = 0; i < countyArray.length; i++) {  
                countyArray[i] = countyList.get(i).area_name.toString();
            }  
            try {  
                npCounty.setMinValue(0);  
                npCounty.setMaxValue(countyArray.length - 1);  
                npCounty.setWrapSelectorWheel(false);  
                npCounty.setDisplayedValues(countyArray);//设置选择器数据、默认值  
            } catch (Exception e) {
                npCounty.setDisplayedValues(countyArray);//设置选择器数据、默认值  
                npCounty.setMinValue(0);  
                npCounty.setMaxValue(countyArray.length - 1);  
                npCounty.setWrapSelectorWheel(false);  
            }
            /*****************************************************************************************************************************/
            for (int i = 0; i < countyArray.length; i++) {  
                if (countyArray[i].equals(list.get(1).area_name)) {  //改动成1
                    npCounty.setValue(i);  
                    return;  
                }  
            }  
            npCounty.setValue(0);  
        }  
      
        //设置NumberPicker的分割线透明、字体颜色、设置监听  
        private void setNomal() {  
            //设置监听  
            npProvince.setOnValueChangedListener(this);  
            npCity.setOnValueChangedListener(this);  
            npCounty.setOnValueChangedListener(this);  
            //去除分割线  
            setNumberPickerDividerColor(npProvince);  
            setNumberPickerDividerColor(npCity);  
            setNumberPickerDividerColor(npCounty);  
            //设置字体颜色  
         /*   setNumberPickerTextColor(npProvince, context.getResources().getColor(R.color.mainColor))*/;
         /*   setNumberPickerTextColor(npCity, context.getResources().getColor(R.color.mainColor));  */
         /*   setNumberPickerTextColor(npCounty, context.getResources().getColor(R.color.mainColor)); */
        }  
      
        @Override
        public void onClick(View v) {
            switch (v.getId()) {  
                case R.id.tvCancel:  
                    dialog.dismiss();  
                    break;  
                case R.id.tvSure:  
                    dialog.dismiss();  
                    cityInterface.sure(list);
                    break;  
            }  
        }  
      
        //选择器选择值监听  
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            switch (picker.getId()) {  
                case R.id.npProvince:  
                    List<CityBeanB.DataBean> dataList = beanB.data;
                    addressBean0.area_name = dataList.get(npProvince.getValue()).area_name;
                    addressBean0.area_id = dataList.get(npProvince.getValue()).area_id;

                    changeCity(npProvince.getValue());

                    addressBean1.area_name = dataList.get(npProvince.getValue()).city.get(0).area_name;
                    addressBean1.area_id = dataList.get(npProvince.getValue()).city.get(0).area_id;
                    addressBean2.area_name = dataList.get(npProvince.getValue()).city.get(0).count.get(0).area_name;
                    addressBean2.area_id = dataList.get(npProvince.getValue()).city.get(0).count.get(0).area_id;
                    break;
                case R.id.npCity:  
                    List<CityBeanB.DataBean.CityBean> cityList = beanB.data.get(npProvince.getValue()).city;
                    addressBean1.area_name = cityList.get(npCity.getValue()).area_name;
                    addressBean1.area_id = cityList.get(npCity.getValue()).area_id;

                    changeCounty(npProvince.getValue(), npCity.getValue());

                    addressBean2.area_name = cityList.get(npCity.getValue()).count.get(0).area_name;
                    addressBean2.area_id = cityList.get(npCity.getValue()).count.get(0).area_id;

                    break;  
                case R.id.npCounty:
                    List<CityBeanB.DataBean.CityBean.CountBean> countyList = beanB.data.get(npProvince.getValue()).city.get(npCity.getValue()).count;

                    addressBean2.area_name = countyList.get(npCounty.getValue()).area_name;
                    addressBean2.area_id = countyList.get(npCounty.getValue()).area_id;
                    break;
            }  
        }  
      
        //设置分割线颜色  
        private void setNumberPickerDividerColor(NumberPicker numberPicker) {
            NumberPicker picker = numberPicker;
            Field[] pickerFields = NumberPicker.class.getDeclaredFields();
            for (Field pf : pickerFields) {
                if (pf.getName().equals("mSelectionDivider")) {  
                    pf.setAccessible(true);  
                    try {  
                        //设置分割线的颜色值  
//                        pf.set(picker, new ColorDrawable(context.getResources().getColor(R.color.transparent)));// pf.set(picker, new Div)
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();  
                    } catch (Resources.NotFoundException e) {
                        e.printStackTrace();  
                    }
                    break;  
                }  
            }  
        }  
      
        //设置选择器字体颜色  
        public static boolean setNumberPickerTextColor(NumberPicker numberPicker, int color) {
            boolean result = false;  
            final int count = numberPicker.getChildCount();  
            for (int i = 0; i < count; i++) {  
                View child = numberPicker.getChildAt(i);
                if (child instanceof EditText) {
                    try {  
                        Field selectorWheelPaintField = numberPicker.getClass()
                                .getDeclaredField("mSelectorWheelPaint");  
                        selectorWheelPaintField.setAccessible(true);  
                        ((Paint) selectorWheelPaintField.get(numberPicker)).setColor(color);
                        ((EditText) child).setTextColor(color);
                        numberPicker.invalidate();  
                        result = true;  
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();  
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();  
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();  
                    }  
                }  
            }  
            return result;  
        }  
    }  