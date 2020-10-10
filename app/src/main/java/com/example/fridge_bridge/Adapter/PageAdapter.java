package com.example.fridge_bridge.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.fridge_bridge.TabFragments.ItemFragment;
import com.example.fridge_bridge.TabFragments.ToBuy;

public class PageAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    public PageAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ItemFragment tab1 = new ItemFragment();
                return tab1;
            case 1:
                ToBuy tab2 = new ToBuy();
                return tab2;
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
