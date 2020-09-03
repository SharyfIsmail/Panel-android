package com.oim.txModel;


import android.app.Activity;
import android.os.Handler;
import android.os.Looper;

import androidx.databinding.BaseObservable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;

import com.oim.candata.DataFromDevice;
import com.oim.candata.DataFromDeviceModel;
import com.oim.myapplication.R;
import com.oim.myapplication.databinding.ActivityMainBinding;
import com.oim.tx.Vcu_1850A0D0;


public class Vcu_1850A0D0_Model extends BaseObservable implements DataFromDeviceModel
{
    private Vcu_1850A0D0 vcu_1850A0D0;
    private ObservableField<String> speed;
    private ObservableField<String> systemStatus;
    private ObservableField<String> errorStatus;
    private ObservableField<String> time;
    private Handler threadHandler;
    private ActivityMainBinding activityMainBinding;
    public Vcu_1850A0D0 getVcu_1850A0D0() {
        return vcu_1850A0D0;
    }

    public ObservableField<String> getSpeed() {
        return speed;
    }

    public ObservableField<String> getSystemStatus() {
        return systemStatus;
    }

    public ObservableField<String> getErrorStatus() {
        return errorStatus;
    }

    public ObservableField<String> getTime() {
        return time;
    }

    public ActivityMainBinding getActivityMainBinding() {
        return activityMainBinding;
    }

    public void setActivityMainBinding(ActivityMainBinding activityMainBinding) {
        this.activityMainBinding = activityMainBinding;
    }

    public  Vcu_1850A0D0_Model()
    {
        vcu_1850A0D0 = new Vcu_1850A0D0();

        speed = new ObservableField<>(String.valueOf(0));
        systemStatus = new ObservableField<>();
        errorStatus = new ObservableField<>();
        time= new ObservableField<>();
        threadHandler = new Handler(Looper.getMainLooper());
    }
    @Override
    public void updateModel()
    {
        threadHandler.post(new Runnable() {
            @Override
            public void run() {
                speed.set(String.valueOf(vcu_1850A0D0.getSpeed()));
                systemStatus.set(String.valueOf(vcu_1850A0D0.getSystemStatus()));
                errorStatus.set("Error: "+String.valueOf(vcu_1850A0D0.getErrorStatus()));
                time.set(String.valueOf(vcu_1850A0D0.getTime()));
                activityMainBinding.setVcu1850A0D0Model(Vcu_1850A0D0_Model.this);
            }
        });

    }

    @Override
    public DataFromDevice getDataFromDevice()
    {
        return vcu_1850A0D0;
    }
}
