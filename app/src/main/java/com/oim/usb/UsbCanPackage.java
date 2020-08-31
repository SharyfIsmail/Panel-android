package com.oim.usb;

import com.oim.can.Can;
import com.oim.can.CanCdr;
import java.util.ArrayList;
import java.util.List;

public class UsbCanPackage  implements  IUsbCan
{
    private List<Can> cans;
    private final short header = 0x9D;
    private short crc8;
    private byte [] usbData = new byte[13];
    private int index = 0;
    public UsbCanPackage()
    {
        super();
        cans = new ArrayList<>();
    }

    @Override
    public short crc8(byte[] data) {
      short crc = 0xFF;
      int dataLength = data.length;
      for(int i = 0; i < data.length ; i++)
        {
            crc = IUsbCan.crc8Table[crc ^ data[i]];
        }
      return crc;
    }

    @Override
    public void removeCan(Can canToRemove)
    {
        cans.remove(canToRemove);
    }

    @Override
    public boolean addCan(Can canToAdd) {
        if(canToAdd != null) {
            return  cans.add(canToAdd);
        }
        else
            return false;
    }

    @Override
    public Can[] getAllCan() {
        return cans.toArray(new Can[cans.size()]);
    }

    @Override
    public void parseUsbPacket(byte[] usbPacket)
    {
        Can currentCan = new CanCdr();
        if(index == 0)
        {
            for (int i = 0; i < usbPacket.length; i++) {
                if (usbPacket[i] != header)
                    continue;
                index = i;
                if ((index + 13) == usbPacket.length) {
                    byte[] partArray = new byte[12];
                    System.arraycopy(usbPacket, 0, partArray, 0, partArray.length);//что будет, если в массивве источнике нет столько байт?
                    if (crc8(partArray) == usbPacket[index + 13]) {
                        currentCan.parseCan(partArray);
                        cans.add(currentCan);
                    }
                    index = 0;
                }
                else
                {
                   // byte[] partArray = new byte[usbPacket.length - index];
                    if(usbPacket.length - index >= 13)
                    {
                        byte[] partArray = new byte[12];
                        System.arraycopy(usbPacket, index + 1, partArray, 0, partArray.length);//что будет, если в массивве источнике нет столько байт?
                        if (crc8(partArray) == usbPacket[index + 13]) {
                            currentCan.parseCan(partArray);
                            cans.add(currentCan);
                        }
                    }
                    else
                    {
                        System.arraycopy(usbPacket, index + 1, usbData, 0, usbPacket.length);
                        index = usbPacket.length - index;
                    }
                }
            }
        }
        else
        {
            if((index + usbPacket.length) >= 13) {
                System.arraycopy(usbPacket, 0, usbData, index, usbData.length - index);
                byte[] partArray = new byte[12];
                System.arraycopy(usbData, 0, partArray, 0, partArray.length);
                if (crc8(partArray) == usbData[13]) {
                    currentCan.parseCan(partArray);
                    cans.add(currentCan);
                }
                index = 0;
            }
        }
    }
}
