package com.oim.txModel;


import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.widget.ProgressBar;

import androidx.databinding.BaseObservable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;

import com.oim.candata.DataFromDevice;
import com.oim.candata.DataFromDeviceModel;
import com.oim.myapplication.R;
import com.oim.myapplication.databinding.ActivityMainBinding;
import com.oim.myapplication.databinding.ActivityTestBinding;
import com.oim.myapplication.databinding.MainSuperCarBinding;
import com.oim.tx.Vcu_1850A0D0;


public class Vcu_1850A0D0_Model   implements DataFromDeviceModel
{
   // private ProgressBar speedProgessBar;
    private Vcu_1850A0D0 vcu_1850A0D0;
    private ObservableField<String> speed;
    private ObservableField<String> systemStatus;
    private ObservableField<String> errorStatus;
    private ObservableField<String> time;
    //private Handler threadHandler;
    private MainSuperCarBinding mainSuperCarBinding;

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
    public void setActivityMainBinding(MainSuperCarBinding mainSuperCarBinding) {
        this.mainSuperCarBinding = mainSuperCarBinding;
    }
//    public void setSpeedProgessBar(ProgressBar speedProgessBar) {
//        this.speedProgessBar = speedProgessBar;
//    }

    public  Vcu_1850A0D0_Model()
    {
        vcu_1850A0D0 = new Vcu_1850A0D0();
        speed = new ObservableField<>();
        systemStatus = new ObservableField<>();
        errorStatus = new ObservableField<>();
        time= new ObservableField<>();
       // threadHandler = new Handler(Looper.getMainLooper());
    }
   // int testValue= 0;
    @Override
    public void updateModel()
    {

//        threadHandler.post(new Runnable() {
//            @Override
//            public void run() {
                speed.set(String.valueOf(vcu_1850A0D0.getSpeed()));
                systemStatus.set(String.valueOf(vcu_1850A0D0.getSystemStatus()));
                errorStatus.set(String.valueOf(vcu_1850A0D0.getErrorStatus()));
                time.set(String.valueOf(vcu_1850A0D0.getTime()));
        mainSuperCarBinding.progressBar.setProgress(speedToPercent(vcu_1850A0D0.getSpeed()));
        mainSuperCarBinding.setVcu1850A0D0Model(Vcu_1850A0D0_Model.this);
//            }
//        });

    }

    private int speedToPercent(short speed)
    {
        // max speed is 200
        return speed / 2;
    }
    @Override
    public DataFromDevice getDataFromDevice()
    {
        return vcu_1850A0D0;
    }
}
