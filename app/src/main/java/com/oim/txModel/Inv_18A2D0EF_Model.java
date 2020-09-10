package com.oim.txModel;

import android.os.Handler;
import android.os.Looper;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;

import com.oim.Activities.MainActivity;
import com.oim.candata.DataFromDevice;
import com.oim.candata.DataFromDeviceModel;
import com.oim.myapplication.R;
import com.oim.myapplication.databinding.ActivityTestBinding;
import com.oim.tx.Inv_18A2D0EF;

public class Inv_18A2D0EF_Model  implements DataFromDeviceModel {
    private ObservableField<String> turnOver;
    private Inv_18A2D0EF inv_18A2D0EF;
    private ActivityTestBinding activityTestBinding;
    private Handler threadHandler;
    private ImageView pointer;
    private ProgressBar rotationProgressBar;

    public void setRotationProgressBar(ProgressBar rotationProgressBar) {
        this.rotationProgressBar = rotationProgressBar;
    }

    public ObservableField<String> getTurnOver() {
        return turnOver;
    }
    public void setTurnOver(ObservableField<String> turnOver) {
        this.turnOver = turnOver;
    }
    public void setActivityMainBinding(ActivityTestBinding activityMainBinding) {
        this.activityTestBinding = activityMainBinding;
    }
    public Inv_18A2D0EF_Model()
    {
        inv_18A2D0EF = new Inv_18A2D0EF();
        threadHandler = new Handler(Looper.getMainLooper());
        turnOver = new ObservableField<>();
    }

    @Override
    public void updateModel()
    {
        threadHandler.post(new Runnable() {
            @Override
            public void run() {
                 //RotateAnimation rotateAnimation = new RotateAnimation(previousValue,rotationToDegrees(inv_18A2D0EF.getRotationSpeed()), RotateAnimation.RELATIVE_TO_SELF,1.0f,RotateAnimation.RELATIVE_TO_SELF, 1.0f);
                turnOver.set(String.valueOf(inv_18A2D0EF.getRotationSpeed()));
                rotationProgressBar.setProgress(rotationToPercent(inv_18A2D0EF.getRotationSpeed()));
             //   RotateAnimation rotateAnimation = new RotateAnimation(previousValue,rotationToDegrees(test++), RotateAnimation.RELATIVE_TO_SELF,1.0f,RotateAnimation.RELATIVE_TO_SELF, 1.0f);

              //  pointer.startAnimation(rotateAnimation);
              //  rotateAnimation.setDuration(10);
              //  rotateAnimation.setFillEnabled(true);
             //   rotateAnimation.setFillAfter(true);
               // previousValue = rotationToDegrees(inv_18A2D0EF.getRotationSpeed());

                activityTestBinding.setInv18A2D0EFModel(Inv_18A2D0EF_Model.this);
            }
        });
    }

    @Override
    public DataFromDevice getDataFromDevice() {
        return inv_18A2D0EF;
    }
    private int rotationToPercent(int rotation)
    {
        //max 12000
        int percent = (rotation * 100) /12000;
        return  percent;
    }

    public ImageView getPointer() {
        return pointer;
    }

    public void setPointer(ImageView pointer) {
        this.pointer = pointer;
    }
}
