package com.histudent.jwsoft.histudent.comment2.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.amap.api.services.help.Tip;
import com.histudent.jwsoft.histudent.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ZhangYT on 2016/10/19.
 */

public class AutoCompleteTipAdapter extends BaseAdapter implements Filterable {

    private List<Tip> list;
    private Context context;
    private MyArrayFilter mFilter;
    private ArrayList<Tip> filerArrayList;

    public AutoCompleteTipAdapter(Context context, List<Tip> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = View.inflate(context, R.layout.tip_item, null);

        TextView tv_address = ((TextView) convertView.findViewById(R.id.tv_name));

        tv_address.setText(list.get(position).getName());

        return convertView;
    }

    @Override
    public Filter getFilter() {

        if (mFilter == null)

            mFilter = new MyArrayFilter();

        return mFilter;
    }

    class MyArrayFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {


            FilterResults filterResults = new FilterResults();
            if (filerArrayList == null) {
                filerArrayList = new ArrayList<>(list);
            }

            if (constraint == null || constraint.length() == 0) {
                ArrayList<Tip> list = filerArrayList;
                filterResults.values = list;
                filterResults.count = list.size();
            }


            String prefixString = constraint.toString();

            ArrayList<Tip> unfilteredValues = filerArrayList;
            int count = unfilteredValues.size();

            ArrayList<Tip> newValues = new ArrayList<Tip>(count);

            for (int i = 0; i < count; i++) {
                Tip tip = unfilteredValues.get(i);
                if (tip != null) {

//                    ||tip.getAddress() != null && tip.getAddress().contains(prefixString)
//                            ||tip.getDistrict() != null && tip.getDistrict().contains(prefixString))
                    if (tip.getName() != null && tip.getName().contains(prefixString)) {
                        newValues.add(tip);
                    }
                }
            }

            filterResults.values = newValues;
            filterResults.count = newValues.size();


            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list = (List<Tip>) results.values;
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }


        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return super.convertResultToString(resultValue);
        }
    }
}
