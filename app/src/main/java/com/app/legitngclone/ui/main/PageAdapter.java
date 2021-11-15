package com.app.legitngclone.ui.main;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.app.legitngclone.ui.home.eightf.eightFragment;
import com.app.legitngclone.ui.home.elevenf.elevenFragment;
import com.app.legitngclone.ui.home.fivef.fiveFragment;
import com.app.legitngclone.ui.home.fourf.fourFragment;
import com.app.legitngclone.ui.home.ninef.nineFragment;
import com.app.legitngclone.ui.home.onef.oneFragment;
import com.app.legitngclone.ui.home.sevenf.sevenFragment;
import com.app.legitngclone.ui.home.sixf.sixFragment;
import com.app.legitngclone.ui.home.tenf.tenFragment;
import com.app.legitngclone.ui.home.thirteenf.thirteenFragment;
import com.app.legitngclone.ui.home.threef.threeFragment;
import com.app.legitngclone.ui.home.twelvef.twelveFragment;
import com.app.legitngclone.ui.home.twof.twoFragment;

public class PageAdapter extends FragmentPagerAdapter {
    private int numOfTabs;
    public PageAdapter(FragmentManager fm, int numOfTabs){
        super(fm);
        this.numOfTabs = numOfTabs;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new oneFragment();

            case 1:
                return new twoFragment();
            case 2:
                return new threeFragment();

            case 3:
                return new fourFragment();
            case 4:
                return new fiveFragment();

            case 5:
                return new sixFragment();
            case 6:
                return new sevenFragment();

            case 7:
                return new eightFragment();
            case 8:
                return new nineFragment();

            case 9:
                return new tenFragment();
            case 10:
                return new elevenFragment();

            case 11:
                return new twelveFragment();
            case 12:
                return new thirteenFragment();
            default:
                return null;
        }
    }


    @Override
    public int getCount() {
        return numOfTabs;
    }
}
