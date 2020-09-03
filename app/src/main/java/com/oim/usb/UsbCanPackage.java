package com.oim.usb;

import com.oim.can.Can;
import com.oim.can.CanCdr;
import com.oim.util.Parser;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UsbCanPackage  implements  IUsbCan
{
    private List<Can> cans = new ArrayList<>();
    private final short header = 0x9D;
    private short crc8;
    private byte [] usbData = new byte[13];
    private int indexSource = 0;
    private int indexDestination = 0;
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
        //    if(indexDestination == 0)
         //   {
                if (Parser.BigIndianByteParser.uint_8ToShort(usbPacket[i]) != header)
                    continue;
                indexSource = i;
                if(usbPacket.length - indexSource + 1 >= 13)
                {
                    byte[] partArray = new byte[12];
                    System.arraycopy(usbPacket, indexSource + 1, partArray, 0, partArray.length);
                    if (crc8(partArray) == Parser.BigIndianByteParser.uint_8ToShort(usbPacket[indexSource + 13])) {
                        currentCan.parseCan(partArray);
                        cans.add(currentCan);
                    }
                    i += 14;
                   // indexSource = 0;
                }
                else
                {
                    //if(indexDestination < 13) {
                        int min = Math.min(usbPacket.length -indexSource - 1, 13 - indexDestination);
                        System.arraycopy(usbPacket, indexSource + 1, usbData, indexDestination, min);
                        indexDestination += min;
                        i += min;
                        if(indexDestination == 13)
                        {
                            byte[] partArray = new byte[12];
                            ByteBuffer.wrap(usbData).get(partArray,0,usbData.length - 1);
                            if (crc8(partArray) == Parser.BigIndianByteParser.uint_8ToShort(usbPacket[indexSource + min])) {
                                currentCan.parseCan(partArray);
                                cans.add(currentCan);
                            }
                            indexDestination = 0;
                            i +=min;
                        }
                   // }
                }
               /* if ((indexSource + 1 + 13) == usbPacket.length) {
                    byte[] partArray = new byte[12];
                    System.arraycopy(usbPacket, indexSource + 1, partArray, 0, partArray.length);
                    if (crc8(partArray) == Parser.BigIndianByteParser.uint_8ToShort(usbPacket[indexSource + 13])) {
                        currentCan.parseCan(partArray);
                        cans.add(currentCan);
                    }
                    indexSource = 0;
                    break;
                }
                else if(usbPacket.length - indexSource + 1 >= 13)
                {
                    byte[] partArray = new byte[12];
                    System.arraycopy(usbPacket, indexSource + 1, partArray, 0, partArray.length);
                    if (crc8(partArray) == Parser.BigIndianByteParser.uint_8ToShort(usbPacket[indexSource + 13])) {
                        currentCan.parseCan(partArray);
                        cans.add(currentCan);
                    }
                    i += 14;
                    indexSource = 0;
                }
                else
                    {
                        int len = (usbPacket.length-indexSource - 1);
                        System.arraycopy(usbPacket, indexSource + 1, usbData, 0,len );
                        indexSource = usbPacket.length -(indexSource + 1);
                    }

                }
                else {
                if((indexSource + usbPacket.length) >= 13) {
                    System.arraycopy(usbPacket, 0, usbData, index, usbData.length - index);
                    byte[] partArray = new byte[12];
                    System.arraycopy(usbData, 0, partArray, 0, partArray.length);
                    if (crc8(partArray) == Parser.BigIndianByteParser.uint_8ToShort(usbData[12])) {
                        currentCan.parseCan(partArray);
                        cans.add(currentCan);
                    }
                    indexSource = 0;
                }
                else
                {
                    System.arraycopy(usbPacket, 0, usbData, indexSource, usbData.length - indexSource);
                    indexSource += usbPacket.length;
                    if(indexSource == 13)
                    {
                        byte[] partArray = new byte[12];
                        System.arraycopy(usbData, 0, partArray, 0, partArray.length);
                        if (crc8(partArray) == Parser.BigIndianByteParser.uint_8ToShort(usbData[13])) {
                            currentCan.parseCan(partArray);
                            cans.add(currentCan);
                        }
                        indexSource = 0;
                    }
                }*/
            //}
        }
    }
}
