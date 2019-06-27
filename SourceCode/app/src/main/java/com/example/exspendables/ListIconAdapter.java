package com.example.exspendables;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

class ViewHolder {
    CheckBox checkBox;
    ImageView icon;
    TextView text;
}

public class ListIconAdapter extends ArrayAdapter<CheckboxIconCategory> {

    private Context context;
    private List<CheckboxIconCategory> list;

   public ListIconAdapter(Context c, List<CheckboxIconCategory> l) {
       super(c,0,l);
        context = c;
        list = l;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CheckboxIconCategory getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public boolean isChecked(int position) {
        return list.get(position).mIsChecked;
    }
/*
    public ListIconAdapter(Context context, ArrayList<CheckboxIconCategory> iconList){
        super(context,0,iconList);
    }*/

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);

        View rowView = convertView;

        // reuse views
        ViewHolder viewHolder = new ViewHolder();

        if (rowView == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            rowView = inflater.inflate(R.layout.icon_listview_row, null);

            viewHolder.checkBox = (CheckBox) rowView.findViewById(R.id.rowCheckBox);
            viewHolder.icon = (ImageView) rowView.findViewById(R.id.image_icon);
            viewHolder.text = (TextView) rowView.findViewById(R.id.image_text);
            rowView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) rowView.getTag();
        }

        final int iconStr = list.get(position).mIconImage;
        viewHolder.icon.setImageResource(iconStr);
        viewHolder.checkBox.setChecked(list.get(position).mIsChecked);

        final String itemStr = list.get(position).mIconName;

        viewHolder.text.setText(itemStr);
        viewHolder.checkBox.setTag(position);

        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean newState = !list.get(position).ismIsChecked();
                list.get(position).mIsChecked = newState;
            }
        });

        viewHolder.checkBox.setChecked(isChecked(position));

        return rowView;

        //return initView(position,convertView,parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getDropDownView(position, convertView, parent);
        return initView(position,convertView,parent);
    }

    private View initView(int position,View convertView,ViewGroup parent){
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.icon_listview_row,parent,
                    false);
        }

        CheckBox checkBox = convertView.findViewById(R.id.rowCheckBox);
        ImageView imageViewIcon = convertView.findViewById(R.id.image_icon);
        TextView textViewName = convertView.findViewById(R.id.image_text);

        CheckboxIconCategory checkboxIconCategory = getItem(position);

        if(checkboxIconCategory != null) {
            checkBox.setChecked(checkboxIconCategory.ismIsChecked());
            imageViewIcon.setImageResource(checkboxIconCategory.getmIconImage());
            textViewName.setText(checkboxIconCategory.getmIconName());
        }
        return convertView;
    }




}
