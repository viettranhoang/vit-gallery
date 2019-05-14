package com.example.gallery;

import java.io.Serializable;

public class Image implements Serializable {
    private String mDate;
    private String mPath;

    public Image() {
    }

    public Image(String date, String path) {
        mDate = date;
        mPath = path;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getPath() {
        return mPath;
    }

    public void setPath(String path) {
        mPath = path;
    }
}
