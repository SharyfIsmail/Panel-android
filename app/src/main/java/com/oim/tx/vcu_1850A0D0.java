package com.oim.tx;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.oim.candata.DataFromDevice;
import com.oim.candata.DataFromDeviceModel;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class vcu_1850A0D0 implements DataFromDevice
{
    private  String systemStatus;
    private String errorStatus;
    private long time;
    private short speed;

    public String getSystemStatus() {
        return systemStatus;
    }

    public String getErrorStatus() {
        return errorStatus;
    }

    public long getTime() {
        return time;
    }

    public short getSpeed() {
        return speed;
    }

    public void setSystemStatus(String systemStatus) {
        this.systemStatus = systemStatus;
    }

    public void setErrorStatus(String errorStatus) {
        this.errorStatus = errorStatus;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setSpeed(short speed) {
        this.speed = speed;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void parseDataFromCan(byte[] data)
    {
        if(data.length == 8)
        {
            systemStatus = SystemStatusCode.getSystemStatus(data[1]);
            speed = (short)(((int)data[7]) & 0xff);
            errorStatus = ErrorStatusCode.getSystemStatus(data[6]);
        }
    }
    private static class SystemStatusCode
    {
        private static Map<Integer, String> systemStatusCondition = new HashMap<>();
        static
        {
            systemStatusCondition.put(0, "Init");
            systemStatusCondition.put(1, "Parking");
            systemStatusCondition.put(2, "Neutral");
            systemStatusCondition.put(3, "Drive");
            systemStatusCondition.put(4, "Reverce");
            systemStatusCondition.put(5, "Charging");
            systemStatusCondition.put(6, "Sleep");
            systemStatusCondition.put(7, "Undefined");
        }
        public static String getSystemStatus(byte code)
        {
            return systemStatusCondition.get((code >> 4 ) & 0x0F);
        }
    }
    private static class ErrorStatusCode
    {
        private static Map<Integer, String> errorStatusCondition = new HashMap<>();
        static
        {
            errorStatusCondition.put(0, "System is working fine");
            errorStatusCondition.put(1, "Error working");
            errorStatusCondition.put(2, "Error stop");
            errorStatusCondition.put(3, "Undefined");
        }
        public static String getSystemStatus(byte code)
        {
            return errorStatusCondition.get(code & 0x0F);
        }
    }


}
