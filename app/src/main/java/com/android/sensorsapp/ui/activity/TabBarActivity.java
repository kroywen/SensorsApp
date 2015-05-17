package com.android.sensorsapp.ui.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

import com.android.sensorsapp.R;
import com.android.sensorsapp.ui.fragment.BaseFragment;
import com.android.sensorsapp.ui.fragment.NewSceneFragment;
import com.android.sensorsapp.ui.fragment.SceneListFragment;
import com.android.sensorsapp.ui.fragment.SensorListFragment;
import com.android.sensorsapp.ui.widget.TabBarView;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TabBarActivity extends Activity implements TabBarView.OnTabChangedListener {

    @InjectView(R.id.tabBar) TabBarView mTabBarView;
    @InjectView(R.id.tabContent) FrameLayout mTabContent;
    private FrameLayout mTabCurrent;

    private Map<Integer, LinkedList<BaseFragment>> tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbar);
        ButterKnife.inject(this);

        tabHost = new HashMap<>();

        mTabBarView.addTab(getString(R.string.sensors), R.drawable.ic_sensors_background);
        mTabBarView.addTab(getString(R.string.scenes), R.drawable.ic_scenes_background);
//        mTabBarView.addTab(getString(R.string.router), R.drawable.ic_router_background);
        mTabBarView.setOnTabChangedListener(this);
        mTabBarView.setCurrentTab(0);
    }

    @Override
    public void onTabChanged(int index) {
        hideSoftKeyborad();
        selectCurrentTabContent(index);
        LinkedList<BaseFragment> currentTabStack = getCurrentTabStack(index);
        if (currentTabStack != null) {
            BaseFragment f = null;
            if (!currentTabStack.isEmpty()) {
                f = currentTabStack.getLast();
            } else {
                switch (index) {
                    case 0:
                        f = new SensorListFragment();
                        f.setTitle(getString(R.string.sensors_screen_title));
                        break;
                    case 1:
                        f = new SceneListFragment();
                        f.setTitle(getString(R.string.scenes_screen_title));
                        break;
                }
                f.setHasParent(false);
                currentTabStack.addLast(f);
            }
            if (f != null) {
                getFragmentManager().beginTransaction()
                        .replace(mTabCurrent.getId(), f).commit();
                setupActionBar(f);
            }
        }
    }

    private void selectCurrentTabContent(int index) {
        int tabCount = mTabBarView.getChildCount();
        if (index < 0 || index >= tabCount) {
            return;
        }
        for (int i=0; i<tabCount; i++) {
            View layout = mTabContent.findViewById("TAB_".hashCode() + i);
            if (layout == null) {
                layout = new FrameLayout(this);
                layout.setLayoutParams(new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                layout.setId("TAB_".hashCode() + i);
                mTabContent.addView(layout);
            }
            if (i == index) {
                layout.setVisibility(View.VISIBLE);
                mTabCurrent = (FrameLayout) layout;
            } else {
                layout.setVisibility(View.INVISIBLE);
            }
        }
    }

    public void startFragment(BaseFragment f) {
        int index = mTabBarView.getCurrentTabIndex();
        if (index != -1) {
            LinkedList<BaseFragment> currentTabStack = getCurrentTabStack(index);
            if (currentTabStack != null && f != null) {
                currentTabStack.addLast(f);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.replace(mTabCurrent.getId(), f).commit();
                setupActionBar(f);
            }
        }
    }

    private LinkedList<BaseFragment> getCurrentTabStack(int index) {
        LinkedList<BaseFragment> currentTabStack = tabHost.get(index);
        if (currentTabStack == null) {
            currentTabStack = new LinkedList<>();
            tabHost.put(index, currentTabStack);
        }
        return currentTabStack;
    }

    private void setupActionBar(BaseFragment f) {
        ActionBar actionBar = getActionBar();
        if (actionBar != null && f != null) {
            actionBar.setTitle(f.getTitle());
            actionBar.setDisplayHomeAsUpEnabled(f.hasParent());
        }
    }

    @Override
    public void onBackPressed() {
        boolean closed = closeScreenIfNeeded();
        if (!closed) {
            super.onBackPressed();
        }
    }

    public boolean closeScreenIfNeeded() {
        int index = mTabBarView.getCurrentTabIndex();
        if (index != -1) {
            LinkedList<BaseFragment> currentTabStack = getCurrentTabStack(index);
            if (currentTabStack.size() > 1) {
                currentTabStack.removeLast();
                BaseFragment f = currentTabStack.getLast();

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                ft.replace(mTabCurrent.getId(), f).commit();
                setupActionBar(f);
                return true;
            }
        }
        return false;
    }

    public void hideSoftKeyborad() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

}
