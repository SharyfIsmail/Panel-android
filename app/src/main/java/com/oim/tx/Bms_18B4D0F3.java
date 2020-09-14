package com.oim.tx;

import com.oim.can.CanCdr;
import com.oim.candata.DataFromDevice;
import com.oim.util.Parser;

public class Bms_18B4D0F3  implements DataFromDevice {
    private float batteryVoltage = 403;
    private float maxCellVoltage;
    private float minCellVoltage;

//    public float getBatteryVoltage() {
//        return batteryVoltage /10 ;
//    }

    public float getBatteryVoltage()
    {
        if (batteryVoltage == 288)
        {
            batteryVoltage = 403;
        }
        return batteryVoltage--;
    }
    public float getMaxCellVoltage() {
        return maxCellVoltage / 10;
    }

    public float getMinCellVoltage() {
        return minCellVoltage / 10;
    }

    public void setBatteryVoltage(float batteryVoltage) {
        this.batteryVoltage = batteryVoltage;
    }

    public void setMaxCellVoltage(float maxCellVoltage) {
        this.maxCellVoltage = maxCellVoltage;
    }

    public void setMinCellVoltage(float minCellVoltage) {
        this.minCellVoltage = minCellVoltage;
    }

    @Override
    public void parseDataFromCan(byte[] data)
    {
        if(data.length == 8)
        {
            byte[] partArray = new byte[2];
            System.arraycopy(data, 1, partArray, 0, partArray.length);

            batteryVoltage = Parser.LittleIndianParser.uint_16ToInt(partArray) >> 4;
            System.arraycopy(data, 3, partArray,0,partArray.length);
            maxCellVoltage = Parser.LittleIndianParser.uint_16ToInt(partArray) & 0x3FF;
            System.arraycopy(data,4,partArray,0, partArray.length);
            minCellVoltage = (Parser.LittleIndianParser.uint_16ToInt(partArray) >> 2)& 0x3FF ;
        }
    }
}
