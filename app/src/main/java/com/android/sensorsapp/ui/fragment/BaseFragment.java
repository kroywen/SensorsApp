package com.android.sensorsapp.ui.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.android.sensorsapp.ui.activity.TabBarActivity;

import butterknife.ButterKnife;

public class BaseFragment extends Fragment {

    protected String title;
    protected boolean hasParent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case android.R.id.home:
                ((TabBarActivity) getActivity()).closeScreenIfNeeded();
                return true;
            default:
                break;
        }
        return false;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean hasParent() { return hasParent; }

    public void setHasParent(boolean hasParent) { this.hasParent = hasParent; }

}
