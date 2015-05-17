package com.android.sensorsapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.sensorsapp.R;
import com.android.sensorsapp.model.Scene;
import com.android.sensorsapp.model.Sensor;

import java.util.List;

public class SceneAdapter extends BaseAdapter {

    private Context mContext;
    private List<Scene> mScenes;

    public SceneAdapter(Context context, List<Scene> scenes) {
        mContext = context;
        mScenes = scenes;
    }

    @Override
    public int getCount() {
        return mScenes.size();
    }

    @Override
    public Scene getItem(int position) {
        return mScenes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.scene_list_item, null);
        }

        final Scene scene = getItem(position);

        View sceneLayout = convertView.findViewById(R.id.sceneLayout);
        sceneLayout.setBackgroundColor(scene.getColor());

        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
        checkBox.setChecked(scene.isChecked());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                scene.setChecked(isChecked);
            }
        });

        TextView sceneName = (TextView) convertView.findViewById(R.id.sceneName);
        sceneName.setText(scene.getName());

        TextView sensorsCount = (TextView) convertView.findViewById(R.id.sensorsCount);
        sensorsCount.setText(mContext.getString(R.string.sensors_count, scene.getSensorsCount()));

        View overflowMenu = convertView.findViewById(R.id.overflowMenu);

        return convertView;
    }

}
