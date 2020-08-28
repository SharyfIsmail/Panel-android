package com.oim.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;

import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.oim.myapplication.R;
import com.oim.usb.FtdiSerialDriver;
import com.oim.usb.UsbSerialDriver;
import com.oim.usb.UsbSerialPort;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private BroadcastReceiver broadcastReceiver;

    private int deviceId, portNum, baudRate;

    private UsbDeviceConnection mConnection;
    private  UsbDevice device = null;
    private TextView someText;
    private TextView id_1;
    private TextView id_2;
    private TextView id_3;
    private TextView id_4;
    private TextView data_0;
    private TextView data_1;
    private TextView data_2;
    private TextView data_3;
    private TextView data_4;
    private TextView data_5;
    private TextView data_6;
    private TextView data_7;
    private TextView countOf;
    private Button buttonClick;
    private UsbSerialPort usbSerialPort;
    private UsbManager mUsbManger;
    private int someData = 0;
    List<TextView> dataList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        someText = (TextView) findViewById(R.id.Fuck);
        id_1 = (TextView) findViewById(R.id.ID_1);
        id_2 = (TextView) findViewById(R.id.ID_2);
        id_3 = (TextView) findViewById(R.id.ID_3);
        id_4 = (TextView) findViewById(R.id.ID_4);
        data_0 = (TextView) findViewById(R.id.Data_0);
        data_1 = (TextView) findViewById(R.id.Data_1);
        data_2 = (TextView) findViewById(R.id.Data_2);
        data_3 = (TextView) findViewById(R.id.Data_3);
        data_4 = (TextView) findViewById(R.id.Data_4);
        data_5 = (TextView) findViewById(R.id.Data_5);
        data_6 = (TextView) findViewById(R.id.Data_6);
        data_7 = (TextView) findViewById(R.id.Data_7);
        countOf = (TextView) findViewById(R.id.CountOf);
        buttonClick = (Button) findViewById(R.id.button);
        buttonClick.setOnClickListener(this);
        dataList = new ArrayList<>();
        dataList.add(id_1);
        dataList.add(id_2);
        dataList.add(id_3);
        dataList.add(id_4);
        dataList.add(data_0);
        dataList.add(data_1);
        dataList.add(data_2);
        dataList.add(data_3);
        dataList.add(data_4);
        dataList.add(data_5);
        dataList.add(data_6);
        dataList.add(data_7);


    }

    private void connect() {
        mUsbManger = (UsbManager) getSystemService(Context.USB_SERVICE);

        someText.setText("Size of the list = " + mUsbManger.getDeviceList().size());

        //UsbManager usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
        for (UsbDevice usbDevice : mUsbManger.getDeviceList().values()) {
            someText.setText(someText.getText() + "\n" + " device Id = " + usbDevice.getDeviceId());
             if (usbDevice.getDeviceId() == 24577)
            device = usbDevice;
        }
        if (device == null) {
            someText.setText(someText.getText() + "\n" + "Connection failed : device not found");
            return;
        }
        someText.setText(someText.getText() + "\n" + " device Id = " + device.getDeviceId());
        someText.setText(someText.getText() + "\n" + " Vendor Id = " + device.getVendorId());

        // UsbSerialDriver driver = UsbSerialProber.getDefaultProber().probeDevice(device);
        UsbSerialDriver driver = new FtdiSerialDriver(device);
        if (driver == null) {
            someText.setText(someText.getText() + "\n" + "Connection failed : driver not found");
        }

      /*  for(UsbSerialPort usbSerialPortTest : driver.getPorts())
        {

        }*/
        usbSerialPort = driver.getPorts().get(0);
        UsbDeviceConnection usbConnection = mUsbManger.openDevice(driver.getDevice());
        if (usbConnection == null)
            someText.setText(someText.getText() + "\n" + "Can't open USB connection:" + device.getDeviceName());
        else {
            someText.setText(someText.getText() + "\n" + "USB connection: is open" + device.getDeviceName());

            try {
                usbSerialPort.open(usbConnection);
                usbSerialPort.setParameters(230400, 8, UsbSerialPort.STOPBITS_1, UsbSerialPort.PARITY_NONE);

            } catch (Exception e) {
                someText.setText(someText.getText() + "\n" + "Connection failed");
            }
        }
    }

    @Override
    public void onClick(View view) {
        someText.setText("Value: "+someData++);
    }
    public byte[] data = new byte[25];
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

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    someText.setText("Lenght :"+len);
                                    countOf.setText("number = " + i++ );
                                    id_1.setText( "Id:"+data[0]);
                                    id_2.setText( "Id:"+data[1]);
                                    id_3.setText( "Id:"+data[2]);
                                    id_4.setText( "Id:"+data[3]);
                                    data_0.setText( "Data:"+data[4]);
                                    data_1.setText( "Data:"+data[5]);
                                    data_2.setText( "Data:"+data[6]);
                                    data_3.setText( "Data:"+data[7]);
                                    data_4.setText( "Data:"+data[8]);
                                    data_5.setText( "Data:"+data[9]);
                                    data_6.setText( "Data:"+data[10]);
                                    data_7.setText( "Data:"+data[11]);

                                }
                            });
                        }
                    } catch(IOException e){
                        someText.setText("errrrrror = " + e.getMessage());
                        e.printStackTrace();
                      }
                }
            }
        }
    }


    public void onResume() {
        super.onResume();
        connect();
        new ThreadTest1().start();
        }

    }