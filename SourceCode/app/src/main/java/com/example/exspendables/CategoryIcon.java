package com.example.exspendables;

public class CategoryIcon {

    private String mIconName;
    private int mIconImage;

    public CategoryIcon(String iconName,int iconImage){
        mIconName = iconName;
        mIconImage = iconImage;
    }

    public String getmIconName() {
        return mIconName;
    }

    public int getmIconImage() {
        return mIconImage;
    }
}
