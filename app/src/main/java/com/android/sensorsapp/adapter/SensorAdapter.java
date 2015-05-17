package com.android.sensorsapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.sensorsapp.R;
import com.android.sensorsapp.model.Sensor;

import java.util.List;

public class SensorAdapter extends BaseAdapter {

    private Context mContext;
    private List<Sensor> mSensors;

    public SensorAdapter(Context context, List<Sensor> sensors) {
        mContext = context;
        mSensors = sensors;
    }

    @Override
    public int getCount() {
        return mSensors.size();
    }

    @Override
    public Sensor getItem(int position) {
        return mSensors.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.sensor_list_item, null);
        }

        Sensor sensor = getItem(position);

        View sensorLayout = convertView.findViewById(R.id.sensorLayout);
        sensorLayout.setBackgroundColor(sensor.getColor());

        ImageView iconView = (ImageView) convertView.findViewById(R.id.iconView);
        iconView.setImageResource(sensor.getIcon());

        TextView titleView = (TextView) convertView.findViewById(R.id.titleView);
        titleView.setText(sensor.getTitle());

        TextView stateView = (TextView) convertView.findViewById(R.id.stateView);
        stateView.setText(sensor.getState());

        ImageView iconLowBattery = (ImageView) convertView.findViewById(R.id.iconLowBattery);
        iconLowBattery.setVisibility(sensor.isLowBattery() ? View.VISIBLE : View.INVISIBLE);

        ImageView iconDeviceTampered = (ImageView) convertView.findViewById(R.id.iconDeviceTampered);
        iconDeviceTampered.setVisibility(sensor.isTampered() ? View.VISIBLE : View.INVISIBLE);

        return convertView;
    }

}
