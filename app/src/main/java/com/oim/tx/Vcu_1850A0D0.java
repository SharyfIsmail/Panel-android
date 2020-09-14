package com.oim.tx;



import com.oim.can.CanCdr;
import com.oim.candata.DataFromDevice;
import com.oim.util.Parser;

import java.util.HashMap;
import java.util.Map;

public class Vcu_1850A0D0  implements DataFromDevice
{
    private  String systemStatus;
    private String errorStatus;
    private long time;
    private short speed = 0;

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

    @Override
    public void parseDataFromCan(byte[] data)
    {
        if(data.length == 8)
        {
            systemStatus = SystemStatusCode.getSystemStatus(data[1]);
            byte[] partArray = new byte[4];
            System.arraycopy(data, 2, partArray, 0, partArray.length);
            time = Parser.BigIndianByteParser.unsignedLongToLong(partArray);
            errorStatus = ErrorStatusCode.getSystemStatus(data[6]);
            speed = Parser.BigIndianByteParser.uint_8ToShort(data[7]);
        }
    }
    private static class SystemStatusCode
    {
        private static Map<Integer, String> systemStatusCondition = new HashMap<>();
        static
        {
            systemStatusCondition.put(0, "I");
            systemStatusCondition.put(1, "P");
            systemStatusCondition.put(2, "N");
            systemStatusCondition.put(3, "D");
            systemStatusCondition.put(4, "R");
            systemStatusCondition.put(5, "C");
            systemStatusCondition.put(6, "S");
            systemStatusCondition.put(7, "E");
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
