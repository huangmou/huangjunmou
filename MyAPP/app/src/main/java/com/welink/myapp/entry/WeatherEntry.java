package com.welink.myapp.entry;

import java.util.List;

/**
 * Created by dell on 2016/7/20.
 */
public class WeatherEntry {

    public String reason;

    public int error_code;

    public List<Results> result;

    public static class Results{
            public List<Datas> data;
    }

    public static class Datas{
        public List<Realtime> realtime;
        public List<Life> life;
        public List<Weathers> weather;
        public List<Pm25> pm25;
        public String date;
        public int isForeign;
    }

    public static class Realtime{
    }
    public static class Life{
    }
    public static class Weathers{
    }
    public static class Pm25{
    }

}
