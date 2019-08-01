package com.classtune.classtuneuni.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.response.UniData;

import java.util.List;

public class ListAdapter extends ArrayAdapter<UniData> {

    private int resourceLayout;
    private Context mContext;
    List<UniData>list;

    public ListAdapter(Context context, int resource, List<UniData> items) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.mContext = context;
        this.list = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(resourceLayout, null);
        }

        UniData p = getItem(position);

        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.product_name);
//            TextView tt2 = (TextView) v.findViewById(R.id.categoryId);
//            TextView tt3 = (TextView) v.findViewById(R.id.description);
//
            if (tt1 != null) {
                tt1.setText(p.getName());
            }

            
//
//            if (tt2 != null) {
//                tt2.setText(p.getCategory().getId());
//            }
//
//            if (tt3 != null) {
//                tt3.setText(p.getDescription());
//            }
        }

        return v;
    }

}