package com.oim.can;

public interface Can
{
    int getId();
    byte[] getData();
    void parseCan(byte[] canPacket) throws ArrayIndexOutOfBoundsException;


}
