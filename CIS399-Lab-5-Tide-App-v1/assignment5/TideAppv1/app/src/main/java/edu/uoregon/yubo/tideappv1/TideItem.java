package edu.uoregon.yubo.tideappv1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhangyu on 7/7/16.
 */
public class TideItem {

    private String date = null;
    private String day = null;
    private String time = null;
    private String predictionInFt = null;
    private String predictionInCm = null;
    private String highlow = null;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPredictionInFt() {
        return predictionInFt;
    }

    public void setPredictionInFt(String predictionInFt) {
        this.predictionInFt = predictionInFt;
    }

    public String getPredictionInCm() {
        return predictionInCm;
    }

    public void setPredictionInCm(String predictionInCm) {
        this.predictionInCm = predictionInCm;
    }


    public String getHighlow() {
        return highlow;
    }

    public void setHighlow(String highlow) {
        this.highlow = highlow;
    }
}
