package com.oim.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;

import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import com.oim.myapplication.R;
import com.oim.myapplication.databinding.ActivityTestBinding;
import com.oim.myapplication.databinding.MainSuperCarBinding;
import com.oim.thread.ReceiveThread;
import com.oim.txModel.AllFramesModel;
import com.oim.txModel.Bms_18B4D0F3_Model;
import com.oim.txModel.Inv_18A2D0EF_Model;
import com.oim.txModel.Vcu_1850A0D0_Model;
import com.oim.usbDriver.FtdiSerialDriver;
import com.oim.usbDriver.UsbSerialDriver;
import com.oim.usbDriver.UsbSerialPort;
import com.oim.usbDriver.UsbSerialProber;

public class MainActivity extends AppCompatActivity {
    private TextView connectionId;
    //   private ImageView pointerImage;
    //   private ProgressBar speedProgressBar;
    //   private ProgressBar rotationProgressBar;

    private  UsbDevice device = null;
  //  private ActivityTestBinding activityTestBinding;
    private MainSuperCarBinding mainSuperCarBinding;
    private   UsbDeviceConnection usbConnection;
    //  private Button buttonClick;
    private UsbSerialPort usbSerialPort;
    private ReceiveThread receiveThread ;
    private AllFramesModel allFramesIdMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setContentView(R.layout.activity_main);
        mainSuperCarBinding = DataBindingUtil.setContentView(this, R.layout.main_super_car);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        allFramesIdMap = AllFramesModel.getAllFramesSingelton();
        receiveThread  = new ReceiveThread();
        connectionId = findViewById(R.id.connectionId);

        //  speedProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        //  rotationProgressBar = (ProgressBar) findViewById(R.id.progressBar2);
        //   RotateAnimation rotateAnimation = new RotateAnimation(-27,360, RotateAnimation.RELATIVE_TO_SELF,1.0f,RotateAnimation.RELATIVE_TO_SELF, 1.0f);

        //Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_pointer);
        //pointerImage.startAnimation(animation);
        // animation.setFillAfter(true);
        //animation.setFillEnabled(true);

        //  pointerImage.startAnimation(rotateAnimation);
        // rotateAnimation.setDuration(10000);
        //  rotateAnimation.setFillAfter(true);
        //  rotateAnimation.setFillEnabled(true);
        Vcu_1850A0D0_Model vcu_1850A0D0_model = (Vcu_1850A0D0_Model) allFramesIdMap.getCanId().get(408002768);
        assert vcu_1850A0D0_model != null;
             vcu_1850A0D0_model.setActivityMainBinding(mainSuperCarBinding);
             mainSuperCarBinding.setVcu1850A0D0Model(vcu_1850A0D0_model);
        Inv_18A2D0EF_Model inv_18A2D0EF_model = (Inv_18A2D0EF_Model) allFramesIdMap.getCanId().get(413323503);
        assert inv_18A2D0EF_model != null;
             inv_18A2D0EF_model.setActivityMainBinding(mainSuperCarBinding);
             mainSuperCarBinding.setInv18A2D0EFModel(inv_18A2D0EF_model);
        Bms_18B4D0F3_Model bms_18B4D0F3_model = (Bms_18B4D0F3_Model)allFramesIdMap.getCanId().get(414503155);
        assert bms_18B4D0F3_model != null;
             bms_18B4D0F3_model.setActivityTestBinding(mainSuperCarBinding);
             mainSuperCarBinding.setBms18B4D0F3Model(bms_18B4D0F3_model);
    }

    private void connect() {
        UsbManager mUsbManger = (UsbManager) getSystemService(Context.USB_SERVICE);
        for (UsbDevice usbDevice : mUsbManger.getDeviceList().values()) {
            // if (usbDevice.getDeviceId() == 24577)
            device = usbDevice;
        }
        if (device == null) {
            mainSuperCarBinding.connectionId.setText(String.format("%s Failed", connectionId.getText()));
            return;
        }
         UsbSerialDriver driver = UsbSerialProber.getDefaultProber().probeDevice(device);
        //   UsbSerialDriver driver = new FtdiSerialDriver(device);

        if (driver == null) {
            mainSuperCarBinding.connectionId.setText(String.format("%s Failed", connectionId.getText()));
        }

      /*  for(UsbSerialPort usbSerialPortTest : driver.getPorts())
        {
        }*/
        usbSerialPort = driver.getPorts().get(0);

        usbConnection = mUsbManger.openDevice(driver.getDevice());
        if (usbConnection == null)
            mainSuperCarBinding.connectionId.setText(String.format("%s Failed", connectionId.getText()));
        else {
            mainSuperCarBinding.connectionId.setText(String.format("%s Open", connectionId.getText()));

            try {
                usbSerialPort.open(usbConnection);
                usbSerialPort.setParameters(460800, 8, UsbSerialPort.STOPBITS_1, UsbSerialPort.PARITY_NONE);


            } catch (Exception e) {
                mainSuperCarBinding.connectionId.setText(String.format("%s Failed", connectionId.getText()));
            }
        }
    }

    public void onResume() {
        super.onResume();
        connect();
        receiveThread.setUnitIdMapper(allFramesIdMap.getCanId());
        receiveThread.setUsbConnection(usbConnection);
        receiveThread.setUsbSerialPort(usbSerialPort);
        receiveThread.start();
    }
}