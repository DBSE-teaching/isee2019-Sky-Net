package com.example.exspendables;

public class CategoryIcon {

    private String mIconName;
    private int mIconImage;

    public CategoryIcon(String iconName,int iconImage){
        mIconName = iconName;
        mIconImage = iconImage;
    }

    public CategoryIcon(int iconImage){
        mIconImage = iconImage;
    }

    public String getmIconName() {
        return mIconName;
    }

    public int getmIconImage() {
        return mIconImage;
    }

    public void setmIconName(String mIconName) {
        this.mIconName = mIconName;
    }

    public void setmIconImage(int mIconImage) {
        this.mIconImage = mIconImage;
    }
}
