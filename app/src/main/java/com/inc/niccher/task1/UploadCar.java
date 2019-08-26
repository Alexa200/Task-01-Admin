package com.inc.niccher.task1;

/**
 * Created by niccher on 04/06/19.
 */

public class UploadCar {
    String imname, imUrl;

    public UploadCar() {
    }

    public UploadCar(String imname, String imUrl) {
        this.imname = imname;
        this.imUrl = imUrl;
    }

    public String getImname() {
        return imname;
    }

    public void setImname(String imname) {
        this.imname = imname;
    }

    public String getImUrl() {
        return imUrl;
    }

    public void setImUrl(String imUrl) {
        this.imUrl = imUrl;
    }
}