package com.oim.tx;

import com.oim.candata.DataFromDeviceModel;
import com.oim.txModel.Inv_18A2D0EF_Model;
import com.oim.txModel.Vcu_1850A0D0_Model;

import java.util.HashMap;
import java.util.Map;

public class AllFrames
{
    public static AllFrames allFramesSingelton;
    private Map<Integer, DataFromDeviceModel> canId;
    private AllFrames(Map<Integer, DataFromDeviceModel> canId)
    {
        this.canId = canId;
    }
    private static Map<Integer, DataFromDeviceModel> canId()
    {
        Map<Integer, DataFromDeviceModel> canId = new HashMap<>();
        canId.put(407937232, new Vcu_1850A0D0_Model());
        canId.put(413323503, new Inv_18A2D0EF_Model());
        return canId;
    }
    public static AllFrames getAllFramesSingelton()
    {
        if(allFramesSingelton == null)
        {
            allFramesSingelton = new AllFrames(AllFrames.canId());
        }
        return allFramesSingelton;
    }

    public Map<Integer, DataFromDeviceModel> getCanId() {
        return canId;
    }
}
