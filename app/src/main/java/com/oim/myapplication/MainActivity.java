package com.oim.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.common.util.PlatformVersion;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {

    private UsbManager mUsbManger;
    private UsbDeviceConnection mConnection;
    private UsbDevice mDevice;
    private UsbEndpoint mOutEndpoint ;
    private UsbEndpoint mInEndpoint ;
    private UsbInterface usbInterface;
    private TextView someText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        someText = (TextView) findViewById(R.id.Fuck);
        mUsbManger = (UsbManager)getSystemService(Context.USB_SERVICE);
    }
     private UsbDevice findDevice()
    {
        for(UsbDevice usbDevice: mUsbManger.getDeviceList().values())
        {
            if(usbDevice.getDeviceClass() == UsbConstants.USB_CLASS_PER_INTERFACE)
            {
                return usbDevice;
            }
            else
            {
                return usbDevice;
            }

        }
        return null;
    }
   private  UsbInterface findInterface(UsbDevice usbDevice) {
        for (int nIf = 0; nIf < usbDevice.getInterfaceCount(); nIf++) {
            UsbInterface usbInterface = usbDevice.getInterface(nIf);
            if (usbInterface.getInterfaceClass() == UsbConstants.USB_CLASS_PER_INTERFACE) {
                return usbInterface;
            }
        }
        return null;
    }
    public void onResume() {

        super.onResume();
       HashMap<String, UsbDevice> deviceList = mUsbManger.getDeviceList();

        Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();

        someText.setText( "Devices Count:" + deviceList.size() );
        while (deviceIterator.hasNext()) {
            UsbDevice Device = (UsbDevice) deviceIterator.next();
            someText.setText( someText.getText() + "\n" + "Device Name: " + Device.getDeviceName() );
            //пример определения ProductID устройства
            someText.setText( someText.getText() + "\n" + "Device ProductID: " + Device.getProductId() );
           for(int i = 0 ; i < Device.getInterfaceCount(); i++)
           {
               UsbInterface usbInterface = Device.getInterface(i);
               someText.setText( someText.getText() + "\n" + "Device endPoint count: " + usbInterface.getEndpointCount() );
               for ( int j = 0 ; j < usbInterface.getEndpointCount(); j++)
               {
                   UsbEndpoint tmpEndpoint = usbInterface.getEndpoint(j);
                   someText.setText( someText.getText() + "\n" + "endPoint direction: " + tmpEndpoint.getDirection());

                   if ((mOutEndpoint == null)
                           && (tmpEndpoint.getDirection() == UsbConstants.USB_DIR_OUT)) {
                       mOutEndpoint = tmpEndpoint;
                   } else if ((mInEndpoint == null)
                           && (tmpEndpoint.getDirection() == UsbConstants.USB_DIR_IN)) {
                       mInEndpoint = tmpEndpoint;
                   }
                   mDevice =   Device;
               }
//               UsbEndpoint usbEndpoint = usbInterface.g
               UsbDeviceConnection   mConnection =  mUsbManger.openDevice(mDevice);
               if (mConnection == null)
                   someText.setText( someText.getText() + "\n" + "Can't open USB connection:" +mDevice.getDeviceName() );


              // mConnection.claimInterface(usbInterface, true);



           }


        }
       // mDevice = findDevice();
       // UsbSerialDriver usbSerialDriver = UsbSerialProber.probeSingleDevice(mDevice);
        //usbInterface = findInterface(mDevice);
      //  usbInterface.getEndpointCount();
      //  someText.setText( someText.getText() + "\n" + "Device ProductID: " + mDevice.getProductId() );
       // someText.setText( someText.getText() + "\n" + "end point : " +   usbInterface.getEndpointCount() );
        //определяем намерение, описанное в фильтре
        // намерений AndroidManifest.xml
        Intent intent = getIntent();
        someText.setText( someText.getText() + "\n" + "intent: " + intent);
        String action = intent.getAction();

        //если устройство подключено, передаем ссылку в
        //в функцию setDevice()
        UsbDevice device = (UsbDevice)intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
        if (UsbManager.ACTION_USB_DEVICE_ATTACHED.equals(action)) {
            setDevice(device);
            someText.setText( someText.getText() + "\n" + "UsbManager.ACTION_USB_DEVICE_ATTACHED.equals(action) is TRUE");
        } else if (UsbManager.ACTION_USB_DEVICE_DETACHED.equals(action)) {
            if (mDevice != null && mDevice.equals(device)) {
                setDevice(null);
                someText.setText( someText.getText() + "\n" + "UsbManager.ACTION_USB_DEVICE_DETACHED.equals(action) is TRUE");
            }
        }
    }

    private void setDevice(UsbDevice device) {
        someText.setText( someText.getText() + "\n" + "setDevice " + device);
        //определяем доступные интерфейсы устройства
        if (device.getInterfaceCount() != 1) {

            someText.setText( someText.getText() + "\n" + "could not find interface");
            return;
        }
        UsbInterface intf = device.getInterface(0);

        //определяем конечные точки устройства
        if (intf.getEndpointCount() == 0) {

            someText.setText( someText.getText() + "\n" +  "could not find endpoint");
            return;
        } else {
            someText.setText( someText.getText() + "\n" + "Endpoints Count: " + intf.getEndpointCount() );
        }

        UsbEndpoint epIN = null;
        UsbEndpoint epOUT = null;

        //ищем конечные точки для передачи по прерываниям
        for (int i = 0; i < intf.getEndpointCount(); i++) {
            if (intf.getEndpoint(i).getType() == UsbConstants.USB_ENDPOINT_XFER_INT) {
                if (intf.getEndpoint(i).getDirection() == UsbConstants.USB_DIR_IN) {
                    epIN = intf.getEndpoint(i);
                    someText.setText( someText.getText() + "\n" + "IN endpoint: " + intf.getEndpoint(i) );
                }
                else {
                    epOUT = intf.getEndpoint(i);
                    someText.setText( someText.getText() + "\n" + "OUT endpoint: " + intf.getEndpoint(i) );
                }
            } else { someText.setText( someText.getText() + "\n" + "no endpoints for INTERRUPT_TRANSFER"); }
        }

        mDevice = device;

        //открываем устройство для передачи данных
        if (device != null) {
            UsbDeviceConnection connection = mUsbManger.openDevice(device);
            if (connection != null && connection.claimInterface(intf, true)) {

                someText.setText( someText.getText() + "\n" + "open device SUCCESS!");
                mConnection = connection;

            } else {

                someText.setText( someText.getText() + "\n" + "open device FAIL!");
                mConnection = null;
            }
        }
    }
}

