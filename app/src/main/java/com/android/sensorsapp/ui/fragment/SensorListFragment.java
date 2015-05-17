package com.android.sensorsapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.sensorsapp.R;
import com.android.sensorsapp.adapter.SensorAdapter;
import com.android.sensorsapp.model.Sensor;
import com.android.sensorsapp.ui.activity.TabBarActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;

public class SensorListFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    @InjectView(R.id.listView) ListView listView;
    @InjectView(R.id.emptyView) TextView emptyView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sensor_list, null);
        ButterKnife.inject(this, view);
        updateViews();
        return view;
    }

    private void updateViews() {
        SensorAdapter adapter = new SensorAdapter(getActivity(), Sensor.getTempSensors());
        listView.setAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.sensor_list_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sync:
                return true;
            default:
                break;
        }
        return false;
    }

    @OnItemClick(R.id.listView)
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Sensor sensor = (Sensor) parent.getAdapter().getItem(position);
        BaseFragment f = new SensorDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable("sensor", sensor);
        f.setArguments(args);
        f.setTitle(getString(R.string.sensors_screen_title));
        f.setHasParent(true);
        ((TabBarActivity) getActivity()).startFragment(f);
    }
}
