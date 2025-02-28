package com.example.sp_subjectmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends BaseAdapter {
    private Context context;
    private List<Lession> items;

    public CustomAdapter(Context context, List<Lession> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    public List<Lession> getLessions() {
        return items;
    }

    public void updateLessions(List<Lession> newLessions) {
        this.items = newLessions;
        notifyDataSetChanged();;
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.lession_list_xml, parent, false);
        }

        CheckBox checkBox = convertView.findViewById(R.id.item_checkbox);

        Lession working_lesson = items.get(position);

        checkBox.setText(String.format("%s - сессия %d", working_lesson.Name, working_lesson.getAssocialIndexOfSession()));
        checkBox.setChecked(items.get(position).Completed);

        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            items.get(position).Completed = isChecked;
        });

        return convertView;
    }
}