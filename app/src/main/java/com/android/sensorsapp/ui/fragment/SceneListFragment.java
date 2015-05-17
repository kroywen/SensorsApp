package com.android.sensorsapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.sensorsapp.R;
import com.android.sensorsapp.SensorApp;
import com.android.sensorsapp.adapter.SceneAdapter;
import com.android.sensorsapp.adapter.SensorAdapter;
import com.android.sensorsapp.model.Scene;
import com.android.sensorsapp.model.Sensor;
import com.android.sensorsapp.ui.activity.TabBarActivity;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class SceneListFragment extends BaseFragment {

    @InjectView(R.id.listView) ListView listView;
    @InjectView(R.id.emptyView) View emptyView;
    @InjectView(R.id.newSceneBtn) FloatingActionButton mActionBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scene_list, null);
        ButterKnife.inject(this, view);
        updateViews();
        return view;
    }

    private void updateViews() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);

        List<Scene> scenes = SensorApp.getScenes();
        if (scenes != null && !scenes.isEmpty()) {
            SceneAdapter adapter = new SceneAdapter(getActivity(), scenes);
            listView.setAdapter(adapter);
            listView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.INVISIBLE);
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        } else {
            listView.setVisibility(View.INVISIBLE);
            emptyView.setVisibility(View.VISIBLE);
            params.addRule(RelativeLayout.BELOW, R.id.emptyView);
        }

        mActionBtn.setLayoutParams(params);
    }

    @OnClick(R.id.newSceneBtn)
    public void addNewScene() {
        BaseFragment f = new NewSceneFragment();
        f.setTitle(getString(R.string.new_scene));
        f.setHasParent(true);
        ((TabBarActivity) getActivity()).startFragment(f);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.scene_list_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_notification:
                return true;
            default:
                break;
        }
        return false;
    }

}
