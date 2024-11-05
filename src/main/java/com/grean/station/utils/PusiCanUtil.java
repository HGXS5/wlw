//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.utils;

import com.sun.jna.Library;
import com.sun.jna.Native;

public class PusiCanUtil {
    static String dllPath = "PUSICANControl";
    int nodeID = 3;

    public PusiCanUtil() {
        try {
            int result = PusiCanUtil.CLibrary.INSTANCE.PCAN_Init(5, 3, 1);
            System.out.println(result);
            result = PusiCanUtil.CLibrary.INSTANCE.PCAN_OpenPort(4);
            System.out.println(result);
            Thread.sleep(3000L);
            result = PusiCanUtil.CLibrary.INSTANCE.PCAN_SetPeakCurrent(this.nodeID, 1600);
            System.out.println(result);
            result = PusiCanUtil.CLibrary.INSTANCE.PCAN_SetMicroStep(this.nodeID, 32);
            System.out.println(result);
            result = PusiCanUtil.CLibrary.INSTANCE.PCAN_SetWorkMode(this.nodeID, 0);
            System.out.println(result);
            result = PusiCanUtil.CLibrary.INSTANCE.PCAN_SetMaxSpeed(this.nodeID, 8000);
            System.out.println(result);
            result = PusiCanUtil.CLibrary.INSTANCE.PCAN_StartRelStep(this.nodeID, 64000);
            System.out.println(result);
            Thread.sleep(10000L);
            result = PusiCanUtil.CLibrary.INSTANCE.PCAN_ClosePort();
            System.out.println(result);
        } catch (Exception var2) {
            System.out.println(var2.toString());
        }

    }

    public interface CLibrary extends Library {
        PusiCanUtil.CLibrary INSTANCE = (PusiCanUtil.CLibrary)Native.loadLibrary(PusiCanUtil.dllPath, PusiCanUtil.CLibrary.class);

        int PCAN_Init(int var1, int var2, int var3);

        int PCAN_OpenPort(int var1);

        int PCAN_ClosePort();

        int PCAN_SetPeakCurrent(int var1, int var2);

        int PCAN_SetMicroStep(int var1, int var2);

        int PCAN_SetWorkMode(int var1, int var2);

        int PCAN_SetMaxSpeed(int var1, int var2);

        int PCAN_StartRelStep(int var1, int var2);
    }
}
