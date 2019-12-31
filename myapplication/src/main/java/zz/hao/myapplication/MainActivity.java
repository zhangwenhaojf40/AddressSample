package zz.hao.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<AddressBean> oldList;
    private AddressBean addressBean1;
    private AddressBean addressBean2;
    private AddressBean addressBean3;
    private Button btnAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAddress = (Button) findViewById(R.id.btn_address);
        btnAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseCityDialog();
            }
        });
    }

    //Choose Date 选择省市县
    public void chooseCityDialog() {
         final ChooseCityUtilB cityUtil = new ChooseCityUtilB();
        if (oldList == null) {

            oldList = new ArrayList<>();
        }
        if (addressBean1 == null) {

            addressBean1 = new AddressBean();
        }
        if (addressBean2 == null) {

            addressBean2 = new AddressBean();
        }
        if (addressBean3 == null) {

            addressBean3 = new AddressBean();
        }
        String sAddress= btnAddress.getText().toString();
        if (!sAddress.contains("-")) {
            sAddress = "北京-北京-东城";
        }
        String[] oldCityArray = sAddress.split("-");//将TextView上的文本分割成数组 当做默认值
        addressBean1.area_name = oldCityArray[0];
        addressBean2.area_name = oldCityArray[1];
        addressBean3.area_name = oldCityArray[2];
        /** 此id号 一定要用从服务器传过来的id号  为其赋值 不能写成下面这种死的
         *  原因：（如果弹出来对话框，用户不选择，点击确定。则id号与地区则不匹配）
         */
        addressBean1.area_id = "2";
        addressBean2.area_id = "52";
        addressBean3.area_id = "500";
        oldList.add(addressBean1);
        oldList.add(addressBean2);
        oldList.add(addressBean3);
        cityUtil.createDialog(this, oldList, new ChooseCityInterfaceB() {

            @Override
            public void sure( ArrayList<AddressBean> list) {



                btnAddress .setText(list.get(0).area_name+ "-" + list.get(1).area_name + "-" + list.get(2).area_name);

                //下面是对应的地区id
              String provinceId = list.get(0).area_id;
                String cityId = list.get(1).area_id;
               String countyId = list.get(2).area_id;


            }
        });
    }
}
