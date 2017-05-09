package com.app.tychodemo.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;

import com.app.tychodemo.R;
import com.app.tychodemo.fragment.BaseFragment;
import com.app.tychodemo.fragment.ClassifyFragment;
import com.app.tychodemo.fragment.FoundFragment;
import com.app.tychodemo.fragment.HomeFragment;
import com.app.tychodemo.fragment.MineFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.radiogroup)
    RadioGroup radiogroup;
    private HomeFragment homeFragment;
    private FoundFragment foundFragemnt;
    private ClassifyFragment classifyFragment;
    private MineFragment mineFragent;
    private ArrayList<BaseFragment> fragmentArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        // 初始化界面
        initView();
        //  选择界面
        radiogroupSelected();
    }

    private void initView() {
        homeFragment = new HomeFragment();
        foundFragemnt = new FoundFragment();
        classifyFragment = new ClassifyFragment();
        mineFragent = new MineFragment();
        fragmentArrayList = new ArrayList<>();
        fragmentArrayList.add(homeFragment);
        fragmentArrayList.add(foundFragemnt);
        fragmentArrayList.add(classifyFragment);
        fragmentArrayList.add(mineFragent);
        viewpager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
        viewpager.setOffscreenPageLimit(3);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        radiogroup.check(R.id.tab_home);
                        break;
                    case 1:
                        radiogroup.check(R.id.tab_found);
                        break;
                    case 2:
                        radiogroup.check(R.id.tab_classify);
                        break;
                    case 3:
                        radiogroup.check(R.id.tab_mine);
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    private void radiogroupSelected() {
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.tab_home:
                        viewpager.setCurrentItem(0);
                        break;
                    case R.id.tab_found:
                        viewpager.setCurrentItem(1);
                        break;
                    case R.id.tab_classify:
                        viewpager.setCurrentItem(2);
                        break;
                    case R.id.tab_mine:
                        viewpager.setCurrentItem(3);
                        break;
                }
            }
        });
    }

    private class MyViewPagerAdapter extends FragmentPagerAdapter {

        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentArrayList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentArrayList.size();
        }
    }
}
