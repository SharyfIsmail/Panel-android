package com.oim.txModel;

import android.os.Handler;
import android.os.Looper;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.oim.candata.DataFromDevice;
import com.oim.candata.DataFromDeviceModel;
import com.oim.tx.Inv_18A2D0EF;

public class Inv_18A2D0EF_Model extends Animation implements DataFromDeviceModel {
    private Inv_18A2D0EF inv_18A2D0EF;
    private Handler threadHandler;
    private int previousValue = -33;
    private ImageView pointer;
    private int newValue;
int test = 0;
    public Inv_18A2D0EF_Model()
    {
        inv_18A2D0EF = new Inv_18A2D0EF();
        threadHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void updateModel()
    {
        threadHandler.post(new Runnable() {
            @Override
            public void run() {
                 //  RotateAnimation rotateAnimation = new RotateAnimation(previousValue,rotationToDegrees(inv_18A2D0EF.getRotationSpeed()), RotateAnimation.RELATIVE_TO_SELF,1.0f,RotateAnimation.RELATIVE_TO_SELF, 1.0f);
                RotateAnimation rotateAnimation = new RotateAnimation(previousValue,rotationToDegrees(test++), RotateAnimation.RELATIVE_TO_SELF,1.0f,RotateAnimation.RELATIVE_TO_SELF, 1.0f);

                pointer.startAnimation(rotateAnimation);
                rotateAnimation.setDuration(10);
                rotateAnimation.setFillEnabled(true);
                rotateAnimation.setFillAfter(true);
               // previousValue = rotationToDegrees(inv_18A2D0EF.getRotationSpeed());
                previousValue = test;
            }
        });
    }

    @Override
    public DataFromDevice getDataFromDevice() {
        return inv_18A2D0EF;
    }
    private int rotationToDegrees(int rotation)
    {
        int degrees =(int)(rotation * 0.0225) - 33;
        return  degrees;
    }

    public ImageView getPointer() {
        return pointer;
    }

    public void setPointer(ImageView pointer) {
        this.pointer = pointer;
    }
}
