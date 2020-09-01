package com.technest.needfood;

public class SliderItem {

    String Title;
    String desk;
    int ScreenImg;

    public SliderItem(String title, String desk, int screenImg) {
        Title = title;
        this.desk = desk;
        ScreenImg = screenImg;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDesk() {
        return desk;
    }

    public void setDesk(String desk) {
        this.desk = desk;
    }

    public int getScreenImg() {
        return ScreenImg;
    }

    public void setScreenImg(int screenImg) {
        ScreenImg = screenImg;
    }
}
