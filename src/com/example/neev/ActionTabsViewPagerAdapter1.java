package com.example.neev;
import com.example.neev.view.*;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class ActionTabsViewPagerAdapter1 extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragments;

    public static final int CHAT = 0;
    public static final int FIND = 1;   
    public static final String UI_TAB_CHAT = "CUSTOM";
    public static final String UI_TAB_FIND = "TODAY";  

    public ActionTabsViewPagerAdapter1(FragmentManager fm, ArrayList<Fragment> fragments){
        super(fm);
        this.fragments = fragments;
    }

    public Fragment getItem(int pos){
        return fragments.get(pos);
    }

    public int getCount(){
        return fragments.size();
    }

    public CharSequence getPageTitle(int position) {
        switch (position) {
            case CHAT:
                return UI_TAB_CHAT;
            case FIND:
                return UI_TAB_FIND;            
            default:
                break;
        }
        return null;
    }
}
