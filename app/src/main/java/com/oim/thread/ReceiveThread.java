package com.oim.thread;

import android.hardware.usb.UsbDeviceConnection;

import com.oim.can.Can;
import com.oim.candata.DataFromDeviceModel;
import com.oim.usb.IUsbCan;
import com.oim.usb.UsbCanPackage;
import com.oim.usbDriver.UsbSerialPort;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;


public class ReceiveThread extends Thread
{
    private UsbSerialPort usbSerialPort;
    private Map<Integer, DataFromDeviceModel> canPackage;
    private UsbDeviceConnection usbConnection;

    public void setCanPackage(Map<Integer, DataFromDeviceModel> canPackage) {
        this.canPackage = canPackage;
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

    @Override
    public void run()
    {
        //Test
//        while(true)
//        {
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            DataFromDeviceModel dataFromDeviceModel = canPackage.get(414503155);
//            dataFromDeviceModel.updateModel();
//            DataFromDeviceModel dataFromDeviceModel1 = canPackage.get(413323503);
//            dataFromDeviceModel1.updateModel();
//
//            DataFromDeviceModel dataFromDeviceModel2 = canPackage.get(407937232);
//            dataFromDeviceModel2.updateModel();
//
//        }
        if(usbConnection != null)
        while(!isInterrupted()) {
            if(usbSerialPort.isOpen()) {
                try {
                    byte [] receiveBuffer = new byte[8192];
                    int len = usbSerialPort.read(receiveBuffer, 100);
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
                Objects.requireNonNull(dataFromDeviceModel).getDataFromDevice().parseDataFromCan(can.getData());
                dataFromDeviceModel.updateModel();
            }
        }
    }
    public void setUnitIdMapper(Map<Integer, DataFromDeviceModel> canPackage)
    {
        this.canPackage = canPackage;
    }

}
