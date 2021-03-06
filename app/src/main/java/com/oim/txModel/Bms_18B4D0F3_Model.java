package com.oim.txModel;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableFloat;

import com.oim.candata.DataFromDevice;
import com.oim.candata.DataFromDeviceModel;
import com.oim.myapplication.databinding.ActivityTestBinding;
import com.oim.myapplication.databinding.MainSuperCarBinding;
import com.oim.tx.Bms_18B4D0F3;

public class Bms_18B4D0F3_Model  implements DataFromDeviceModel {
    private Bms_18B4D0F3 bms_18B4D0F3;
    private ObservableFloat totalVoltage;
    private ObservableFloat maxVoltage;
    private ObservableFloat minVoltage;
    private MainSuperCarBinding mainSuperCarBinding;
    //private  int i = 0;

    public Bms_18B4D0F3_Model() {
        bms_18B4D0F3 = new Bms_18B4D0F3();
        totalVoltage = new ObservableFloat(0.0f);
        maxVoltage = new ObservableFloat(0.0f);
        minVoltage = new ObservableFloat(0.0f);
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

    public void setActivityTestBinding(MainSuperCarBinding mainSuperCarBinding) {
        this.mainSuperCarBinding = mainSuperCarBinding;
    }

    @Override
    public void updateModel() {
        totalVoltage.set(bms_18B4D0F3.getBatteryVoltage());
       // totalVoltage.set(i++);
        maxVoltage.set(bms_18B4D0F3.getMaxCellVoltage());
        minVoltage.set(bms_18B4D0F3.getMinCellVoltage());
        mainSuperCarBinding.progressBattery.setProgress(voltageToPercent(bms_18B4D0F3.getBatteryVoltage()));
        mainSuperCarBinding.progressMaxCellVoltage.setProgress(maxVoltageToPercent(bms_18B4D0F3.getMaxCellVoltage()));
        mainSuperCarBinding.progressMinCellVoltage.setProgress(minVoltageToPercent(bms_18B4D0F3.getMinCellVoltage()));
    }

    @Override
    public DataFromDevice getDataFromDevice() {
        return bms_18B4D0F3;
    }

    private int voltageToPercent(float voltage)
    {
        return (int) (100 - ((403 - voltage) * 100/115));
    }
    private int maxVoltageToPercent(float voltage)
    {
        return (int) (((voltage * 10) - 26) * 100) /20;
    }
    private int minVoltageToPercent(float voltage)
    {
        return (int) (((voltage * 10) - 26) * 1000) /20;

    }
}
