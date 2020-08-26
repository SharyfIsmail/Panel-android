package com.oim.usb;

import android.hardware.usb.UsbDevice;

import java.util.List;

public interface UsbSerialDriver
{
    public UsbDevice getDevice();
    public List<UsbSerialPort> getPorts();
}
