package com.oim.thread;

import android.hardware.usb.UsbDeviceConnection;
import android.os.Handler;
import android.os.Looper;

import com.oim.can.Can;
import com.oim.candata.DataFromDeviceModel;
//import com.oim.myapplication.databinding.ActivityMainBinding;
//import com.oim.txModel.Vcu_1850A0D0_Model;
import com.oim.myapplication.databinding.ActivityMainBinding;
import com.oim.txModel.Vcu_1850A0D0_Model;
import com.oim.usb.IUsbCan;
import com.oim.usb.UsbCanPackage;
import com.oim.usbDriver.UsbSerialPort;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

public class ReceiveThread extends Thread
{
    private ActivityMainBinding binding;
    private UsbSerialPort usbSerialPort;
    private Handler threadHandler;
    private Map<Integer, DataFromDeviceModel> canPackage;
    private UsbDeviceConnection usbConnection;



    public void setBinding(ActivityMainBinding binding) {
        this.binding = binding;
    }

    public void setThreadHandler(Handler threadHandler) {
        this.threadHandler = threadHandler;
    }

    public void setCanPackage(Map<Integer, DataFromDeviceModel> canPackage) {
        this.canPackage = canPackage;
    }

    public ActivityMainBinding getBinding() {
        return binding;
    }

    public Handler getThreadHandler() {
        return threadHandler;
    }

    public Map<Integer, DataFromDeviceModel> getCanPackage() {
        return canPackage;
    }

    public void setUsbSerialPort(UsbSerialPort usbSerialPort) {
        this.usbSerialPort = usbSerialPort;
    }

    public void setUsbConnection(UsbDeviceConnection usbConnection) {
        this.usbConnection = usbConnection;
    }

    public ReceiveThread()
    {
        super();
        threadHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void run()
    {
        if(usbConnection != null)
        while(!isInterrupted()) {
            if(usbSerialPort.isOpen()) {
                try {
                    int len = 0;
                    byte [] receiveBuffer = new byte[8192];
                    len = usbSerialPort.read(receiveBuffer, 100);

                    if (len > 0) {
                        byte[] array = Arrays.copyOf(receiveBuffer, len);
                        objectMapping(array);
                        }
                    }catch (IOException e) {
                     e.printStackTrace();
                }
            }
        }
    }
    private void objectMapping(byte[] data)
    {
        IUsbCan usbCanPackage = new UsbCanPackage();
        usbCanPackage.parseUsbPacket(data);
        for(Can can : usbCanPackage.getAllCan())
        {
            if(canPackage.get(can.getId()) != null)
            {
                DataFromDeviceModel dataFromDeviceModel = canPackage.get(can.getId());
                dataFromDeviceModel.getDataFromDevice().parseDataFromCan(can.getData());
                dataFromDeviceModel.updateModel();
            }
        }
    }
    public void setUnitIdMapper(Map<Integer, DataFromDeviceModel> canPackage)
    {
        this.canPackage = canPackage;
    }

}
