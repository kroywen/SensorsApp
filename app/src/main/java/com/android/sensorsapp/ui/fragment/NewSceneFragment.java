package com.android.sensorsapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.sensorsapp.R;
import com.android.sensorsapp.SensorApp;
import com.android.sensorsapp.model.Scene;
import com.android.sensorsapp.model.Sensor;
import com.android.sensorsapp.ui.activity.TabBarActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class NewSceneFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener,
    NumberPicker.OnValueChangeListener {

    @InjectView(R.id.hallLampRadioGroup) RadioGroup mHallLampRadioGroup;

    @InjectView(R.id.bedroomLightRadioGroup) RadioGroup mBedroomLightRadioGroup;
    @InjectView(R.id.bedroomLightDimContent) View mBedroomLightDimContent;
    @InjectView(R.id.bedroomLightDimText) TextView mBedroomLightDimText;
    @InjectView(R.id.bedroomLightDimValue) TextView mBedroomLightDimValue;
    @InjectView(R.id.bedroomLightDimPicker) NumberPicker mBedroomLightDimPicker;
    @InjectView(R.id.bedroomLightDimPickerLayout) View mBedroomLightDimPickerLayout;

    @InjectView(R.id.thermostatCoolingTempContent) View mThermostatCoolingTempContent;
    @InjectView(R.id.thermostatCoolingTempPickerLayout) View mThermostatCoolingTempPickerLayout;
    @InjectView(R.id.thermostatCoolingTempValue) TextView mThermostatCoolingTempValue;
    @InjectView(R.id.thermostatCoolingTempPicker) NumberPicker mThermostatCoolingTempPicker;
    @InjectView(R.id.thermostatCoolingTempText) TextView mThermostatCoolingTempText;

    @InjectView(R.id.thermostatHeatingTempContent) View mThermostatHeatingTempContent;
    @InjectView(R.id.thermostatHeatingTempPickerLayout) View mThermostatHeatingTempPickerLayout;
    @InjectView(R.id.thermostatHeatingTempValue) TextView mThermostatHeatingTempValue;
    @InjectView(R.id.thermostatHeatingTempPicker) NumberPicker mThermostatHeatingTempPicker;
    @InjectView(R.id.thermostatHeatingTempText) TextView mThermostatHeatingTempText;

    @InjectView(R.id.thermostatModeRadioGroup) RadioGroup mThermostatModeRadioGroup;
    @InjectView(R.id.thermostatFanRadioGroup) RadioGroup mThermostatFanRadioGroup;

    @InjectView(R.id.sceneName) EditText mSceneName;
    @InjectView(R.id.sceneDescription) EditText mSceneDescription;

    private Scene scene;

    private boolean mBedroomLightDimPickerVisible;
    private boolean mThermostatCoolingTempPickerVisible;
    private boolean mThermostatHeatingTempPickerVisible;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scene = new Scene();
        scene.setMode(Scene.ThermostatMode.OFF);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_scene, null);
        ButterKnife.inject(this, view);
        initializeViews();
        updateViews();
        return view;
    }

    private void initializeViews() {
        mHallLampRadioGroup.setOnCheckedChangeListener(this);
        mBedroomLightRadioGroup.setOnCheckedChangeListener(this);

        mBedroomLightDimPicker.setMinValue(1);
        mBedroomLightDimPicker.setMaxValue(99);
        mBedroomLightDimPicker.setWrapSelectorWheel(false);
        mBedroomLightDimPicker.setOnValueChangedListener(this);
        int bedroomLight = scene.getBedroomLight();
        if (bedroomLight > 0 && bedroomLight < 100) {
            mBedroomLightDimPicker.setValue(bedroomLight);
        } else {
            mBedroomLightDimPicker.setValue(50);
        }

        mThermostatCoolingTempPicker.setMinValue(35);
        mThermostatCoolingTempPicker.setMaxValue(95);
        mThermostatCoolingTempPicker.setWrapSelectorWheel(false);
        mThermostatCoolingTempPicker.setOnValueChangedListener(this);
        mThermostatCoolingTempPicker.setValue(scene.getCoolingTemp());

        mThermostatHeatingTempPicker.setMinValue(35);
        mThermostatHeatingTempPicker.setMaxValue(95);
        mThermostatHeatingTempPicker.setWrapSelectorWheel(false);
        mThermostatHeatingTempPicker.setOnValueChangedListener(this);
        mThermostatHeatingTempPicker.setValue(scene.getHeatingTemp());

        mThermostatModeRadioGroup.setOnCheckedChangeListener(this);
        mThermostatFanRadioGroup.setOnCheckedChangeListener(this);
    }

    private void updateViews() {
        if (scene != null) {
            mHallLampRadioGroup.check(scene.isHallLampOn() ? R.id.hallLampOn : R.id.hallLampOff);

            if (scene.isBedroomLightOff()) {
                mBedroomLightRadioGroup.check(R.id.bedroomLightOff);
                checkBedroomLightDim(false);
            } else if (scene.isBedroomLightOn()) {
                mBedroomLightRadioGroup.check(R.id.bedroomLightOn);
                checkBedroomLightDim(false);
            } else {
                mBedroomLightRadioGroup.clearCheck();
                checkBedroomLightDim(true);
                mBedroomLightDimValue.setText(String.valueOf(scene.getBedroomLight()));
            }

            Scene.ThermostatMode mode = scene.getMode();
            switch (mode) {
                case AUTO:
                    mThermostatModeRadioGroup.check(R.id.thermostatModeAuto);
                    break;
                case HEAT:
                    mThermostatHeatingTempValue.setText(String.valueOf(scene.getHeatingTemp()));
                    mThermostatModeRadioGroup.check(R.id.thermostatModeHeat);
                    break;
                case COOL:
                    mThermostatCoolingTempValue.setText(String.valueOf(scene.getCoolingTemp()));
                    mThermostatModeRadioGroup.check(R.id.thermostatModeCool);
                    break;
                case OFF:
                    mThermostatModeRadioGroup.check(R.id.thermostatModeOff);
                    break;
            }

            mThermostatHeatingTempValue.setText(String.valueOf(
                    mThermostatHeatingTempPicker.getValue()));
            mThermostatCoolingTempValue.setText(String.valueOf(
                    mThermostatCoolingTempPicker.getValue()));

            mThermostatFanRadioGroup.check(scene.isFanOn() ? R.id.thermostatFanOn : R.id.thermostatFanOff);

            mSceneName.setText(scene.getName());
            mSceneDescription.setText(scene.getDescription());
        }
    }

    private void checkBedroomLightDim(boolean check) {
        int colorOn = getResources().getColor(R.color.blue);
        int colorOff = getResources().getColor(R.color.radiobutton_text_off);
        mBedroomLightDimContent.setBackgroundColor(check ? colorOn : colorOff);
        mBedroomLightDimText.setTextColor(check ? colorOn : colorOff);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.scene_new_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.action_save:
                int scenesCount = SensorApp.getScenes().size();
                int color = (scenesCount == 0) ? 0xff4caf50 :
                        (scenesCount == 1) ? 0xffff9800 : 0xfff44336;
                scene.setColor(color);
                scene.setName(mSceneName.getText().toString().trim());
                scene.setDescription(mSceneDescription.getText().toString().trim());
                SensorApp.addScene(scene);

                ((TabBarActivity) getActivity()).hideSoftKeyborad();
                ((TabBarActivity) getActivity()).closeScreenIfNeeded();
                return true;
            default:
                break;
        }
        return false;
    }

    @OnClick(R.id.bedroomLightDimContent)
    public void bedroomLightDimClicked() {
        mBedroomLightRadioGroup.clearCheck();
        checkBedroomLightDim(true);
        toggleBedroomLightPickerVisibility();
    }

    private void toggleBedroomLightPickerVisibility() {
        mBedroomLightDimPickerVisible = !mBedroomLightDimPickerVisible;
        mBedroomLightDimPickerLayout.setVisibility(
                mBedroomLightDimPickerVisible ? View.VISIBLE : View.GONE);
    }

    @OnClick(R.id.thermostatCoolingTempContent)
    public void thermostatCoolingTempClicked() {
        if (scene.getMode() == Scene.ThermostatMode.COOL) {
            toggleThermostatCoolingTempPickerVisibility();
        }
    }

    private void toggleThermostatCoolingTempPickerVisibility() {
        mThermostatCoolingTempPickerVisible = !mThermostatCoolingTempPickerVisible;
        mThermostatCoolingTempPickerLayout.setVisibility(
                mThermostatCoolingTempPickerVisible ? View.VISIBLE : View.GONE);
    }

    @OnClick(R.id.thermostatHeatingTempContent)
    public void thermostatHeatingTempClicked() {
        if (scene.getMode() == Scene.ThermostatMode.HEAT) {
            toggleThermostatHeatingTempPickerVisibility();
        }
    }

    private void toggleThermostatHeatingTempPickerVisibility() {
        mThermostatHeatingTempPickerVisible = !mThermostatHeatingTempPickerVisible;
        mThermostatHeatingTempPickerLayout.setVisibility(
                mThermostatHeatingTempPickerVisible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (group.getId() == R.id.hallLampRadioGroup) {
            scene.setHallLamp(checkedId == R.id.hallLampOn);
        } else if (group.getId() == R.id.bedroomLightRadioGroup) {
            if (checkedId != -1) {
                checkBedroomLightDim(false);
                if (mBedroomLightDimPickerVisible) {
                    toggleBedroomLightPickerVisibility();
                }
                if (checkedId == R.id.bedroomLightOn) {
                    scene.setBedroomLight(100);
                } else if (checkedId == R.id.bedroomLightOff) {
                    scene.setBedroomLight(0);
                }
            }
        } else if (group.getId() == R.id.thermostatModeRadioGroup) {
            if (mThermostatCoolingTempPickerVisible) {
                toggleThermostatCoolingTempPickerVisibility();
            }
            if (mThermostatHeatingTempPickerVisible) {
                toggleThermostatHeatingTempPickerVisibility();
            }
            switch (checkedId) {
                case R.id.thermostatModeAuto:
                    scene.setMode(Scene.ThermostatMode.AUTO);
                    checkThermostatTemp(false, false);
                    break;
                case R.id.thermostatModeHeat:
                    scene.setMode(Scene.ThermostatMode.HEAT);
                    checkThermostatTemp(false, true);
                    break;
                case R.id.thermostatModeCool:
                    scene.setMode(Scene.ThermostatMode.COOL);
                    checkThermostatTemp(true, false);
                    break;
                case R.id.thermostatModeOff:
                    scene.setMode(Scene.ThermostatMode.OFF);
                    checkThermostatTemp(false, false);
                    break;
            }
        } else if (group.getId() == R.id.thermostatFanRadioGroup) {
            scene.setFan(checkedId == R.id.thermostatFanOn);
        }
    }

    private void checkThermostatTemp(boolean coolingTemp, boolean heatingTemp) {
        int colorOn = getResources().getColor(R.color.blue);
        int colorOff = getResources().getColor(R.color.radiobutton_text_off);

        mThermostatCoolingTempContent.setBackgroundColor(coolingTemp ? colorOn : colorOff);
        mThermostatCoolingTempText.setTextColor(coolingTemp ? colorOn : colorOff);

        mThermostatHeatingTempContent.setBackgroundColor(heatingTemp ? colorOn : colorOff);
        mThermostatHeatingTempText.setTextColor(heatingTemp ? colorOn : colorOff);
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        if (picker.getId() == R.id.bedroomLightDimPicker) {
            scene.setBedroomLight(newVal);
            mBedroomLightDimValue.setText(String.valueOf(newVal));
        } else if (picker.getId() == R.id.thermostatCoolingTempPicker) {
            scene.setCoolingTemp(newVal);
            mThermostatCoolingTempValue.setText(String.valueOf(newVal));
        } else if (picker.getId() == R.id.thermostatHeatingTempPicker) {
            scene.setHeatingTemp(newVal);
            mThermostatHeatingTempValue.setText(String.valueOf(newVal));
        }
    }
}
