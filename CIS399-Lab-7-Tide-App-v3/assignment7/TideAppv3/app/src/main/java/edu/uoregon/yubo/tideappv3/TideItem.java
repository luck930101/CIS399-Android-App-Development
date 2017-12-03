package edu.uoregon.yubo.tideappv3;

/**
 * Created by zhangyu on 7/7/16.
 */
public class TideItem {


    private String date = null;
    private String time = null;
    private String pred = null;
    private String type = null;

    public String getDate() {return date;}

    public void setDate(String date) {this.date = date;}

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPred() {
        return pred;
    }

    public void setPred(String pred) {
        this.pred = pred;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
