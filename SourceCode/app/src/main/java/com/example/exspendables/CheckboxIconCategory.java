package com.example.exspendables;

import android.graphics.drawable.Drawable;

public class CheckboxIconCategory {

    public String mIconName;
    public int mIconImage;
    public boolean mIsChecked;


    public CheckboxIconCategory(String iconName,int iconImage,boolean isChecked){
        mIconName = iconName;
        mIconImage = iconImage;
        mIsChecked = isChecked;
    }


    public String getmIconName() {
        return mIconName;
    }

   public int getmIconImage() {
        return mIconImage;
    }

    public boolean ismIsChecked() {
        return mIsChecked;
    }
}
