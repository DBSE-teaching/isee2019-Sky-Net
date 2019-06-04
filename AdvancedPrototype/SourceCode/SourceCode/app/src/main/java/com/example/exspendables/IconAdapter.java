package com.example.exspendables;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class IconAdapter extends ArrayAdapter<CategoryIcon> {

    public IconAdapter(Context context, ArrayList<CategoryIcon> iconList){
        super(context,0,iconList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        return initView(position,convertView,parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getDropDownView(position, convertView, parent);
        return initView(position,convertView,parent);
    }

    private View initView(int position,View convertView,ViewGroup parent){
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.icon_spinner_row,parent,
                    false);
        }

        ImageView imageViewIcon = convertView.findViewById(R.id.image_icon);
        TextView textViewName = convertView.findViewById(R.id.image_text);

        CategoryIcon categoryIcon = getItem(position);

        if(categoryIcon != null) {
            imageViewIcon.setImageResource(categoryIcon.getmIconImage());
            textViewName.setText(categoryIcon.getmIconName());
        }
        return convertView;
    }
}
