package com.android.sensorsapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.sensorsapp.R;
import com.android.sensorsapp.model.Sensor;
import com.android.sensorsapp.ui.widget.ToastView;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SensorDetailsFragment extends BaseFragment {

    @InjectView(R.id.sensorLayout) View mSensorLayout;
    @InjectView(R.id.iconView) ImageView mIconView;
    @InjectView(R.id.titleView) TextView mTitleView;
    @InjectView(R.id.stateView) TextView mStateView;
    @InjectView(R.id.iconLowBattery) ImageView mIconLowBattery;
    @InjectView(R.id.iconDeviceTampered) ImageView mIconDeviceTampered;
    @InjectView(R.id.toastView) LinearLayout mToastView;

    private Sensor sensor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            sensor = (Sensor) args.getSerializable("sensor");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sensor_details, null);
        ButterKnife.inject(this, view);
        updateViews();
        return view;
    }

    private void updateViews() {
        if (sensor != null) {
            mSensorLayout.setBackgroundColor(sensor.getColor());
            mIconView.setImageResource(sensor.getIcon());
            mTitleView.setText(sensor.getTitle());
            mStateView.setText(sensor.getState());
            mIconLowBattery.setVisibility(sensor.isLowBattery() ? View.VISIBLE : View.INVISIBLE);
            mIconDeviceTampered.setVisibility(sensor.isTampered() ? View.VISIBLE : View.INVISIBLE);

            if (sensor.isTampered()) {
                View tamperedToast = mToastView.findViewWithTag("tampered");
                if (tamperedToast == null) {
                    showToast("tampered", R.mipmap.icon_device_tampered, R.string.device_tampered);
                }
            }

            if (sensor.isLowBattery()) {
                View lowBatteryToast = mToastView.findViewWithTag("low_battery");
                if (lowBatteryToast == null) {
                    showToast("low_battery", R.mipmap.icon_low_battery,
                            getString(R.string.low_battery, 13));
                }
            }
        }
    }

    private void showToast(String tag, int iconResId, int messageResId) {
        showToast(tag, iconResId, getString(messageResId));
    }

    private void showToast(String tag, int iconResId, String message) {
        ToastView toast = new ToastView(getActivity());
        toast.setTag(tag);
        toast.setIcon(iconResId);
        toast.setMessage(message);
        mToastView.addView(toast);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.sensor_details_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.action_sync:
                return true;
            default:
                break;
        }
        return false;
    }

}
