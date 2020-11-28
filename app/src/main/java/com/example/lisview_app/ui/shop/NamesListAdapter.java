package com.example.lisview_app.ui.shop;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.lisview_app.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.PropertyResourceBundle;

public class NamesListAdapter extends BaseAdapter {
    private static final String TAG="NamesListAdapter";

    private Context mContext;
    int mResource;
    private ArrayList<Names> nombres;
    private ArrayList<Boolean> checked_list;

    public NamesListAdapter(Context context, int resource, ArrayList<Names> objects, ArrayList<Boolean> dato) {
        this.mContext =context;
        this.mResource=resource;
        this.nombres=objects;
        this.checked_list=dato;
    }

    @Override
    public int getCount() {
        return nombres.size();
    }
    @Override
    public Object getItem(int position) {
        return nombres.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater inflater= LayoutInflater.from(mContext);
        Names name=(Names)getItem(position);
        convertView=inflater.inflate(mResource,parent,false);
        TextView tvName=(TextView) convertView.findViewById(R.id.listtext);
        if(checked_list.get(position)==true) tvName.setPaintFlags(tvName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        tvName.setText(name.getNames());
        return convertView;
    }
}
