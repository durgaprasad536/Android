package com.figsinc.app.learn.Model;

/**
 * Created by 461883 on 9/25/17.
 */

public class CandleStick {
    String VOLUME;
    String TIMESTAMP;
    String HIGH;
    String LOW;
    String CLOSE;
    String OPEN;

    public String getVOLUME() {
        return VOLUME;
    }

    public void setVOLUME(String VOLUME) {
        this.VOLUME = VOLUME;
    }

    public String getTIMESTAMP() {
        return TIMESTAMP;
    }

    public void setTIMESTAMP(String TIMESTAMP) {
        this.TIMESTAMP = TIMESTAMP;
    }

    public String getHIGH() {
        return HIGH;
    }

    public void setHIGH(String HIGH) {
        this.HIGH = HIGH;
    }

    public String getLOW() {
        return LOW;
    }

    public void setLOW(String LOW) {
        this.LOW = LOW;
    }

    public String getCLOSE() {
        return CLOSE;
    }

    public void setCLOSE(String CLOSE) {
        this.CLOSE = CLOSE;
    }

    public String getOPEN() {
        return OPEN;
    }

    public void setOPEN(String OPEN) {
        this.OPEN = OPEN;
    }


}
