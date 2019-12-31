package zz.hao.myapplication;

import java.util.List;

/**
 * Created by ZhangWenHao on 2017/4/14 0014.
 */
public class CityBeanB {


    public int status;
    public String message;
    public Object other;

    public List<DataBean> data;

    public static class DataBean {
        public String area_id;
        public String area_name;


        public List<CityBean> city;

        public static class CityBean {
            public String area_id;
            public String area_name;

            /**
             * area_id : 500
             * area_name : 东城区
             */

            public List<CountBean> count;

            public static class CountBean {
                public String area_id;
                public String area_name;
            }
        }
    }
}
