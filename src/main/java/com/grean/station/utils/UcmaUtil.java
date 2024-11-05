//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.utils;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import java.io.File;
import org.springframework.util.ResourceUtils;

public class UcmaUtil {
    static String dllPath = "";

    public UcmaUtil() {
        try {
            File InputFile = ResourceUtils.getFile("classpath:dll/ucma.dll");
            dllPath = InputFile.getAbsolutePath();
            UcmaUtil.CLibrary.INSTANCE.UCMA_Init();
        } catch (Exception var2) {
        }

    }

    public interface CLibrary extends Library {
        UcmaUtil.CLibrary INSTANCE = (UcmaUtil.CLibrary)Native.loadLibrary(UcmaUtil.dllPath, UcmaUtil.CLibrary.class);

        void UCMA_Init();

        int UCMA_CheckConn(int var1, IntByReference var2);

        int UCMA_Conn(IntByReference var1, int var2);

        void UCMA_Close(int var1);

        int UCMA_Send(int var1, Pointer var2, int var3);

        int UCMA_Recv(int var1, Pointer var2, int var3);
    }
}
