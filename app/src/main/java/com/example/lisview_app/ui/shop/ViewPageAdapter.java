package com.example.lisview_app.ui.shop;

import android.util.SparseArray;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class ViewPageAdapter extends FragmentStatePagerAdapter {
    SparseArray<Fragment> sparseArray=new SparseArray<>();
    private ArrayList<String> data;
    public ViewPageAdapter(FragmentManager fm, ArrayList<String> data)
    {
        super(fm);
        this.data = data;
    }
    @Override
    public int getCount() {
        return data.size();
    }
    @Override
    public ShopFragment getItem(int position)
    {
        return ShopFragment.newInstance(data.get(position));
    }// Optional: only if TabLayout is used
    @Override
    public CharSequence getPageTitle(int position)
    {
        return this.data.get(position);
    }
    @Override
    public int getItemPosition(Object object) {
        int position = data.indexOf(object);
        return position == -1 ? POSITION_NONE : position;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        sparseArray.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        sparseArray.remove(position);
        super.destroyItem(container, position, object);
    }
    public Fragment getRegisteredFragment(int position) {
        return sparseArray.get(position);
    }
}
