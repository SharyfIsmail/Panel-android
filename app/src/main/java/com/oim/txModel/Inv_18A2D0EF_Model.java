package com.oim.txModel;

import androidx.databinding.ObservableField;
import com.oim.candata.DataFromDevice;
import com.oim.candata.DataFromDeviceModel;
import com.oim.myapplication.databinding.ActivityTestBinding;
import com.oim.myapplication.databinding.MainSuperCarBinding;
import com.oim.tx.Inv_18A2D0EF;

public class Inv_18A2D0EF_Model  implements DataFromDeviceModel {
    private ObservableField<String> turnOver;
    private Inv_18A2D0EF inv_18A2D0EF;
    private MainSuperCarBinding mainSuperCarBinding;
   // private  int i = 0;
  //  private Handler threadHandler;

    public ObservableField<String> getTurnOver() {
        return turnOver;
    }
    public void setTurnOver(ObservableField<String> turnOver) {
        this.turnOver = turnOver;
    }
    public void setActivityMainBinding(MainSuperCarBinding mainSuperCarBinding) {
        this.mainSuperCarBinding = mainSuperCarBinding;
    }
    public Inv_18A2D0EF_Model()
    {
        inv_18A2D0EF = new Inv_18A2D0EF();
     //   threadHandler = new Handler(Looper.getMainLooper());
        turnOver = new ObservableField<>(String.valueOf(0));
    }

    @Override
    public void updateModel()
    {
           turnOver.set(String.valueOf(inv_18A2D0EF.getRotationSpeed()));
       // turnOver.set(String.valueOf(i++));
        mainSuperCarBinding.progressBar2.setProgress(rotationToPercent(inv_18A2D0EF.getRotationSpeed()));
        //mainSuperCarBinding.setInv18A2D0EFModel(Inv_18A2D0EF_Model.this);
    }

    @Override
    public DataFromDevice getDataFromDevice() {
        return inv_18A2D0EF;
    }
    private int rotationToPercent(int rotation)
    {
        //max 12000
        return (rotation * 100) /12000;
    }

}
