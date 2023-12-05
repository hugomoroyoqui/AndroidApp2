package com.hugo.firstapp;

import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;

public class LocationAdapter implements ListAdapter {

    FragmentActivity activity;
    ArrayList<LocationSchema> locationArray;

    public LocationAdapter(FragmentActivity activity, ArrayList<LocationSchema> locationArray) {
        this.activity = activity;
        this.locationArray = locationArray;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int i) {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getCount() {
        return locationArray.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            LayoutInflater layoutInflater = LayoutInflater.from(activity);
            view = layoutInflater.inflate(R.layout.item_location, null);

            TextView txtID = view.findViewById(R.id.item_id);

            txtID.setText(locationArray.get(i).getLocation_ID());


        }
        return view;
    }

    @Override
    public int getItemViewType(int i) {
        return i;
    }

    @Override
    public int getViewTypeCount() {
        return locationArray.size();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
