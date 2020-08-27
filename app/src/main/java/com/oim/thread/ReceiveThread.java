package com.oim.thread;

import com.google.android.gms.common.util.PlatformVersion;

import java.util.Map;

public class ReceiveThread extends Thread
{
    private Map<Integer, Integer> canPackage;
    @Override
    public void run() {

    }
    private void objectMapping(byte[] data)
    {

    }
    public void setUnitIdMapper(Map<Integer, Integer> canPackage)
    {
        this.canPackage = canPackage;
    }

}
