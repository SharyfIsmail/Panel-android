package com.oim.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
//import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;

import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.oim.myapplication.R;
import com.oim.myapplication.databinding.ActivityMainBinding;
import com.oim.thread.ReceiveThread;
import com.oim.tx.AllFrames;
import com.oim.txModel.Vcu_1850A0D0_Model;
import com.oim.usbDriver.FtdiSerialDriver;
import com.oim.usbDriver.UsbSerialDriver;
import com.oim.usbDriver.UsbSerialPort;


import java.io.IOException;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{
    private TextView connectionId;
    private UsbDeviceConnection mConnection;
    private  UsbDevice device = null;
    private ActivityMainBinding activityMainBinding;
    private   UsbDeviceConnection usbConnection;
    private Button buttonClick;
    private UsbSerialPort usbSerialPort;
    private UsbManager mUsbManger;
    private ReceiveThread receiveThread ;
    private AllFrames allFramesIdMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_main);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        allFramesIdMap = AllFrames.getAllFramesSingelton();
        receiveThread  = new ReceiveThread();
        connectionId = (TextView) findViewById(R.id.connectionId);
        Vcu_1850A0D0_Model v = (Vcu_1850A0D0_Model) allFramesIdMap.getCanId().get(407937232);
        v.setActivityMainBinding(activityMainBinding);
        buttonClick = (Button) findViewById(R.id.button);
        buttonClick.setOnClickListener(this);

    }

//    private void connect() {
//        mUsbManger = (UsbManager) getSystemService(Context.USB_SERVICE);
//        //UsbManager usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
//        for (UsbDevice usbDevice : mUsbManger.getDeviceList().values()) {
//            connectionId.setText(connectionId.getText() + "\n" + " device Id = " + usbDevice.getDeviceId());
//            // if (usbDevice.getDeviceId() == 24577)
//            device = usbDevice;
//        }
//        if (device == null) {
//            connectionId.setText(connectionId.getText() + " failed");
//           // connectionId.setText(String.format("%s Failed", connectionId.getText()));
//            return;
//        }
//        // UsbSerialDriver driver = UsbSerialProber.getDefaultProber().probeDevice(device);
//        UsbSerialDriver driver = new FtdiSerialDriver(device);
//        if (driver == null) {
//            connectionId.setText(connectionId.getText() + " failed");
//         //   connectionId.setText(String.format("%s Failed", connectionId.getText()));
//        }
//
//
//        usbSerialPort = driver.getPorts().get(0);
//        UsbDeviceConnection usbConnection = mUsbManger.openDevice(driver.getDevice());
//        if (usbConnection == null) {
//            connectionId.setText(connectionId.getText() + " failed");
//
//            //   connectionId.setText(String.format("%s Failed", connectionId.getText()));
//        }
//        else {
//            try {
//                usbSerialPort.open(usbConnection);
//                usbSerialPort.setParameters(460800, 8, UsbSerialPort.STOPBITS_1, UsbSerialPort.PARITY_NONE);
//              //  connectionId.setText(String.format("%s Open", connectionId.getText()));
//                connectionId.setText(connectionId.getText() + " Open");
//
//            } catch (Exception e) {
//                connectionId.setText(connectionId.getText() + " failed");
//
////                connectionId.setText(String.format("%s Failed", connectionId.getText()));
//            }
//        }
//    }

    private void connect() {
        mUsbManger = (UsbManager) getSystemService(Context.USB_SERVICE);
        //UsbManager usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
        for (UsbDevice usbDevice : mUsbManger.getDeviceList().values()) {
           // if (usbDevice.getDeviceId() == 24577)
                device = usbDevice;
        }
        if (device == null) {
            activityMainBinding.connectionId.setText(connectionId.getText() + " Failed");
            return;
        }
        // UsbSerialDriver driver = UsbSerialProber.getDefaultProber().probeDevice(device);
        UsbSerialDriver driver = new FtdiSerialDriver(device);

        if (driver == null) {
            activityMainBinding.connectionId.setText(connectionId.getText() + " Failed");
        }

      /*  for(UsbSerialPort usbSerialPortTest : driver.getPorts())
        {
        }*/
        usbSerialPort = driver.getPorts().get(0);

        usbConnection = mUsbManger.openDevice(driver.getDevice());
        if (usbConnection == null)
            activityMainBinding.connectionId.setText(connectionId.getText() + " Failed");
        else {
            activityMainBinding.connectionId.setText(connectionId.getText() + " Open");

            try {
                usbSerialPort.open(usbConnection);
                usbSerialPort.setParameters(460800, 8, UsbSerialPort.STOPBITS_1, UsbSerialPort.PARITY_NONE);


            } catch (Exception e) {
                activityMainBinding.connectionId.setText(connectionId.getText() + " Failed");
            }
        }
    }
    public byte[] data = new byte[25];
int a = 0;
    @Override
    public void onClick(View view) {
        activityMainBinding.connectionId.setText("value " + a++);
    }

    class ThreadTest1 extends Thread
    {
        int i = 0;
        int len;
        @Override
        public void run()
        {
            while(!isInterrupted())
            {
                if (usbSerialPort != null) {
                    try {
                        len = usbSerialPort.read(data, 100);

                        if(len > 0)
                        {
                            byte[] array =  Arrays.copyOfRange(data, 0, len);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {


                                }
                            });
                        }
                    } catch(IOException e){
                        e.printStackTrace();
                      }
                }
            }
        }
    }


    public void onResume() {
        super.onResume();
        connect();
        receiveThread.setUnitIdMapper(allFramesIdMap.getCanId());
        receiveThread.setBinding(activityMainBinding);
        receiveThread.setUsbConnection(usbConnection);
        receiveThread.setUsbSerialPort(usbSerialPort);
        receiveThread.start();
        }

    }