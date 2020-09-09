package com.oim.tx;

import com.oim.can.CanCdr;
import com.oim.candata.DataFromDevice;
import com.oim.util.Parser;

public class Inv_18A2D0EF extends CanCdr implements DataFromDevice {
    private  int rotationSpeed;

    public int getRotationSpeed() {
        return Math.abs(rotationSpeed - 20000);
    }

    public void setRotationSpeed(int rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    @Override
    public void parseDataFromCan(byte[] data)
    {
        if(data.length == 8)
        {
            byte[] partArray = new byte[2];
            System.arraycopy(data, 6, partArray,0,partArray.length);
            rotationSpeed = Parser.LittleIndianParser.uint_16ToInt(partArray);
        }
    }
}
