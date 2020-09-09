package com.oim.txModel;

import com.oim.candata.DataFromDeviceModel;

import java.util.HashMap;
import java.util.Map;

public class AllFramesModel
{
    public static AllFramesModel allFramesSingelton;
    private Map<Integer, DataFromDeviceModel> canId;
    private AllFramesModel(Map<Integer, DataFromDeviceModel> canId)
    {
        this.canId = canId;
    }
    private static Map<Integer, DataFromDeviceModel> canId()
    {
        Map<Integer, DataFromDeviceModel> canId = new HashMap<>();
        canId.put(407937232, new Vcu_1850A0D0_Model());
        canId.put(413323503, new Inv_18A2D0EF_Model());
        canId.put(414503155, new Bms_18B4D0F3_Model());

        return canId;
    }
    public static AllFramesModel getAllFramesSingelton()
    {
        if(allFramesSingelton == null)
        {
            allFramesSingelton = new AllFramesModel(AllFramesModel.canId());
        }
        return allFramesSingelton;
    }

    public Map<Integer, DataFromDeviceModel> getCanId() {
        return canId;
    }
}
