package com.oim.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Parser
{
    public static class BigIndianByteParser
    {
        private BigIndianByteParser()
        {
            super();
        }
        public static short uint_8ToShort( byte data)
        {
            return (short) (((int) data) & 0xff);

        }
        public static int uint_16ToInt(byte[] data)
        {
            Integer a = 4;
            if(data.length == 2)
            {
                byte[] parsedData = {0, 0, data[0], data[1]};
                return ByteBuffer.wrap(parsedData).order(ByteOrder.BIG_ENDIAN).getInt();
            }
            else
                throw new ArrayIndexOutOfBoundsException("length should be 2 byte not "+data.length);
        }
        public static long unsignedLongToLong(byte []data)
        {
            if(data.length == 4)
            {
                byte[] parsedData = {0, 0, 0, 0, data[0], data[1], data[2], data[3]};
                return ByteBuffer.wrap(parsedData).order(ByteOrder.BIG_ENDIAN).getLong();
            }
            else
            {
                throw new ArrayIndexOutOfBoundsException("length should be 4 byte not "+data.length);
            }
        }

    }
    public static class LittleIndianParser
    {
        private  LittleIndianParser(){super();}
        public static short uint_8ToShort( byte data)
        {
            return (short) (((int) data) & 0xff);
        }
        public static int uint_16ToInt(byte[] data)
        {
            if(data.length == 2)
            {
                byte[] parsedData = {data[0], data[1], 0, 0};
                return ByteBuffer.wrap(parsedData).order(ByteOrder.LITTLE_ENDIAN).getInt();
            }
            else
                throw new ArrayIndexOutOfBoundsException("length should be 2 byte not "+data.length);
        }
        public static long unsignedLongToLong(byte []data)
        {
            if(data.length == 4)
            {
                byte[] parsedData = { data[0], data[1], data[2], data[3], 0, 0, 0, 0};
                return ByteBuffer.wrap(parsedData).order(ByteOrder.LITTLE_ENDIAN).getLong();
            }
            else
            {
                throw new ArrayIndexOutOfBoundsException("length should be 4 byte not "+data.length);
            }
        }
    }
}
