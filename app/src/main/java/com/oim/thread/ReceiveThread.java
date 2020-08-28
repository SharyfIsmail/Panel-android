package com.oim.thread;

import com.google.android.gms.common.util.PlatformVersion;
import com.oim.candata.DataFromDeviceModel;

import java.util.Map;

public class ReceiveThread extends Thread
{
    private Map<Integer, DataFromDeviceModel> canPackage;
    @Override
    public void run() {

    }
    private void objectMapping(byte[] data)
    {

    }
    public void setUnitIdMapper(Map<Integer, DataFromDeviceModel> canPackage)
    {
        this.canPackage = canPackage;
    }

}
