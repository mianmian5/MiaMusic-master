package com.example.miamusic_master.adapter;


import static androidx.viewpager.widget.PagerAdapter.POSITION_NONE;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.miamusic_master.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 多Fragment切换、高复用的PagerAdapter
 */
public class MultiFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private static final String TAG = "MultiFragmentPagerAdapt";

    private List<BaseFragment> fragments = new ArrayList<>();

    public MultiFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void init(List<BaseFragment> fragmentList) {
        fragments.clear();
        fragments.addAll(fragmentList);
    }

    @Override
    public Fragment getItem(int i) {
        if (fragments != null && i < fragments.size()) {
            return fragments.get(i);
        }
        return null;
    }

    @Override
    public int getCount() {
        return fragments == null ? 0 : fragments.size();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (getItem(position) instanceof BaseFragment) {
            return ((BaseFragment) getItem(position)).getTitle();
        }
        return super.getPageTitle(position);
    }
}
