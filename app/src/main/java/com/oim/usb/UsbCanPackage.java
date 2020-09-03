package com.oim.usb;

import com.oim.can.Can;
import com.oim.can.CanCdr;
import com.oim.util.Parser;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class UsbCanPackage  implements  IUsbCan
{
    private List<Can> cans = new ArrayList<>();
    private final short header = 0x9D;
    private short crc8;
    private byte [] usbData = new byte[13];
    private int index = 0;
    public UsbCanPackage()
    {
        super();
    }

    @Override
    public short crc8(byte[] data) {
      short crc = 0xFF;
      for(int i = 0; i < data.length ; i++)
        {
            crc = IUsbCan.crc8Table[crc ^ Parser.BigIndianByteParser.uint_8ToShort(data[i])];
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
        cans.clear();
        for (int i = 0; i < usbPacket.length; i++) {
            if(index == 0)
            {
                if (Parser.BigIndianByteParser.uint_8ToShort(usbPacket[i]) != header)
                    continue;
                index = i;
                if ((index + 1 + 13) == usbPacket.length) {
                    byte[] partArray = new byte[12];
                    System.arraycopy(usbPacket, index + 1, partArray, 0, partArray.length);
                    if (crc8(partArray) == Parser.BigIndianByteParser.uint_8ToShort(usbPacket[index + 13])) {
                        currentCan.parseCan(partArray);
                        cans.add(currentCan);
                    }
                    index = 0;
                    break;
                }
                else
                {
                    if(usbPacket.length - index >= 14)
                    {
                        byte[] partArray = new byte[12];
                        System.arraycopy(usbPacket, index + 1, partArray, 0, partArray.length);
                        if (crc8(partArray) == Parser.BigIndianByteParser.uint_8ToShort(usbPacket[index + 13])) {
                            currentCan.parseCan(partArray);
                            cans.add(currentCan);
                        }
                        i += 14;
                    }
                    else
                    {
                        System.arraycopy(usbPacket, index + 1, usbData, 0, usbPacket.length);
                        index = usbPacket.length - index + 1;
                    }
                }
            }

           else {
                if((index + usbPacket.length) >= 13) {
                    System.arraycopy(usbPacket, 0, usbData, index, usbData.length - index);
                    byte[] partArray = new byte[12];
                    System.arraycopy(usbData, 0, partArray, 0, partArray.length);
                    if (crc8(partArray) == Parser.BigIndianByteParser.uint_8ToShort(usbData[13])) {
                        currentCan.parseCan(partArray);
                        cans.add(currentCan);
                    }
                    index = 0;
                }
                else
                {
                    System.arraycopy(usbPacket, 0, usbData, index, usbPacket.length);
                    index += usbPacket.length;
                    if(index == 13)
                    {
                        byte[] partArray = new byte[12];
                        System.arraycopy(usbData, 0, partArray, 0, partArray.length);
                        if (crc8(partArray) == Parser.BigIndianByteParser.uint_8ToShort(usbData[13])) {
                            currentCan.parseCan(partArray);
                            cans.add(currentCan);
                        }
                        index = 0;
                    }
                }
            }
        }
    }
}
