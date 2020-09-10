package com.oim.txModel;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableFloat;

import com.oim.candata.DataFromDevice;
import com.oim.candata.DataFromDeviceModel;
import com.oim.myapplication.databinding.ActivityTestBinding;
import com.oim.tx.Bms_18B4D0F3;

public class Bms_18B4D0F3_Model  implements DataFromDeviceModel {
    private Bms_18B4D0F3 bms_18B4D0F3;
    private ObservableFloat totalVoltage;
    private ObservableFloat maxVoltage;
    private ObservableFloat minVoltage;
    private ActivityTestBinding activityTestBinding;

    public Bms_18B4D0F3_Model() {
        bms_18B4D0F3 = new Bms_18B4D0F3();
        totalVoltage = new ObservableFloat();
        maxVoltage = new ObservableFloat();
        minVoltage = new ObservableFloat();
    }

    public ObservableFloat getTotalVoltage() {
        return totalVoltage;
    }

    public ObservableFloat getMaxVoltage() {
        return maxVoltage;
    }

    public ObservableFloat getMinVoltage() {
        return minVoltage;
    }

    public void setActivityTestBinding(ActivityTestBinding activityTestBinding) {
        this.activityTestBinding = activityTestBinding;
    }

    @Override
    public void updateModel() {
        totalVoltage.set(bms_18B4D0F3.getBatteryVoltage());
        activityTestBinding.progressBattery.setProgress(voltageToPercent(bms_18B4D0F3.getBatteryVoltage()));
        activityTestBinding.setBms18B4D0F3Model(Bms_18B4D0F3_Model.this);
    }

    @Override
    public DataFromDevice getDataFromDevice() {
        return bms_18B4D0F3;
    }

    private int voltageToPercent(float voltage)
    {
        return (int) (100 - ((403 - voltage) * 100/115));
    }
}
