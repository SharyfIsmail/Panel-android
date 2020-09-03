package com.oim.can;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class CanCdr implements Can
{
    private int id;
    private byte[] data = new  byte[8];
    public static final int PACKAGE_SIZE = 12;
    @Override
    public int getId() {
        return id;
    }

    @Override
    public byte[] getData()
    {
        return data;
    }

    @Override
    public void parseCan(byte[] canPacket) throws ArrayIndexOutOfBoundsException
    {
        if(getBytePosition(canPacket.length) == getBytePosition(PACKAGE_SIZE)) {
            byte[] partArray = new byte[4];
            id = ByteBuffer.wrap(canPacket).order(ByteOrder.BIG_ENDIAN).getInt(0);
            int bytePosition = +4;
           System.arraycopy(canPacket, getBytePosition(bytePosition), data, 0, data.length );
        }
    }

    private int getBytePosition(int bytePosition) {
        return bytePosition;
    }
}
