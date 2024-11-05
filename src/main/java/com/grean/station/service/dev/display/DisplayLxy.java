//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.dev.display;

import com.grean.station.comm.CommInter;
import com.grean.station.domain.DO.cfg.CfgDev;
import com.grean.station.domain.DO.cfg.CfgFactor;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Structure;
import com.sun.jna.WString;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import org.springframework.util.ResourceUtils;

public class DisplayLxy extends DisplayService {
    static String dllPath = "";

    public DisplayLxy() {
    }

    public void init(CommInter interComm, CfgDev cfgDev, CfgFactor cfgFactor, List regList, int channel, int cmdTime) {
        super.init(interComm, cfgDev, cfgFactor, regList, channel, cmdTime);
        (new Thread(new Runnable() {
            public void run() {
                try {
                    File InputFile = ResourceUtils.getFile("classpath:dll/LxyBoxSDK.dll");
                    DisplayLxy.dllPath = InputFile.getAbsolutePath();
                } catch (Exception var16) {
                }

                DisplayLxy.CLibrary.INSTANCE.Lxy_init_workspace();
                int screenId = DisplayLxy.CLibrary.INSTANCE.Lxy_create_screen(0, 0, 200, 100);
                int programId = DisplayLxy.CLibrary.INSTANCE.Lxy_create_program(screenId, false, new WString("节目页一"), true, 600, (WString)null, 0, 16777215, (WString)null, 0);
                int wndId1 = DisplayLxy.CLibrary.INSTANCE.Lxy_create_wnd(programId, 0, 0, 200, 20, new WString("文件窗"), 0, 16777215, 0);
                int nRet1 = DisplayLxy.CLibrary.INSTANCE.Lxy_add_single_txt(wndId1, new WString("    中山市生态环境监测"), new WString("宋体"), 10, 16777215, false, false, false, 15, 60, 1, (WString)null, 0, 0, 0, false, false, true, 10, 0);
                int wndId2 = DisplayLxy.CLibrary.INSTANCE.Lxy_create_wnd(programId, 0, 20, 200, 20, new WString("文件窗"), 0, 16777215, 0);
                DisplayLxy.CLibrary.INSTANCE.Lxy_add_single_txt(wndId2, new WString(" 站点：跃进围涌    19.11.09"), new WString("宋体"), 10, 16777215, false, false, false, 15, 60, 1, (WString)null, 0, 0, 0, false, false, true, 10, 0);
                int wndId3 = DisplayLxy.CLibrary.INSTANCE.Lxy_create_wnd(programId, 0, 40, 200, 20, new WString("文件窗"), 0, 16777215, 0);
                DisplayLxy.CLibrary.INSTANCE.Lxy_add_single_txt(wndId3, new WString(" 总磷　  1.23   mg/L"), new WString("宋体"), 10, 16777215, false, false, false, 15, 60, 1, (WString)null, 0, 0, 0, false, false, true, 10, 0);
                int wndId4 = DisplayLxy.CLibrary.INSTANCE.Lxy_create_wnd(programId, 0, 60, 200, 20, new WString("文件窗"), 0, 16777215, 0);
                DisplayLxy.CLibrary.INSTANCE.Lxy_add_single_txt(wndId4, new WString(" 总氮　  0.23   mg/L"), new WString("宋体"), 10, 16777215, false, false, false, 15, 60, 1, (WString)null, 0, 0, 0, false, false, true, 10, 0);
                int wndId5 = DisplayLxy.CLibrary.INSTANCE.Lxy_create_wnd(programId, 0, 80, 200, 20, new WString("文件窗"), 0, 16777215, 0);
                DisplayLxy.CLibrary.INSTANCE.Lxy_add_single_txt(wndId5, new WString(" 氨氮　  0.23   mg/L"), new WString("宋体"), 10, 16777215, false, false, false, 15, 60, 1, (WString)null, 0, 0, 0, false, false, true, 10, 0);
                new DisplayLxy.CLibrary.List_box.ByReference();

                try {
                    Thread.sleep(30000L);
                } catch (Exception var15) {
                }

                System.out.println("" + nRet1);
            }
        })).start();
    }

    public interface CLibrary extends Library {
        DisplayLxy.CLibrary INSTANCE = (DisplayLxy.CLibrary)Native.loadLibrary(DisplayLxy.dllPath, DisplayLxy.CLibrary.class);

        void Lxy_init_workspace();

        int Lxy_get_box_list(DisplayLxy.CLibrary.List_box.ByReference var1);

        int Lxy_create_screen(int var1, int var2, int var3, int var4);

        int Lxy_create_program(int var1, boolean var2, WString var3, boolean var4, int var5, WString var6, int var7, int var8, WString var9, int var10);

        int Lxy_create_wnd(int var1, int var2, int var3, int var4, int var5, WString var6, int var7, int var8, int var9);

        int Lxy_add_single_txt(int var1, WString var2, WString var3, int var4, int var5, boolean var6, boolean var7, boolean var8, int var9, int var10, int var11, WString var12, int var13, int var14, int var15, boolean var16, boolean var17, boolean var18, int var19, int var20);

        public static class List_box extends Structure {
            public DisplayLxy.CLibrary.Lxy_box_perperty.ByReference p_Lxy_st_box_property;
            public DisplayLxy.CLibrary.List_box.ByReference p_node_next;

            public List_box() {
            }

            protected List getFieldOrder() {
                return Arrays.asList("p_Lxy_st_box_property", "p_node_next");
            }

            public static class ByValue extends DisplayLxy.CLibrary.List_box implements com.sun.jna.Structure.ByValue {
                public ByValue() {
                }
            }

            public static class ByReference extends DisplayLxy.CLibrary.List_box implements com.sun.jna.Structure.ByReference {
                public ByReference() {
                }
            }
        }

        public static class Lxy_box_perperty extends Structure {
            public int n_type;
            public int n_count;
            public String s_time;
            public String s_run_time;
            public String s_capacity;
            public String s_ip;
            public String s_wifi_info;
            public String s_wifi_list;
            public String s_wifi_AP;
            public String s_net_info;
            public String s_app_version_info;
            public String s_device_name;
            public String s_hdmi_mode;
            public String s_mac;
            public String s_system_size;
            public String s_volume_info;
            public boolean b_online;
            public boolean b_is_new;

            public Lxy_box_perperty() {
            }

            protected List getFieldOrder() {
                return Arrays.asList("n_type", "n_count", "s_time", "s_run_time", "s_capacity", "s_ip", "s_wifi_info", "s_wifi_list", "s_wifi_AP", "s_net_info", "s_app_version_info", "s_device_name", "s_hdmi_mode", "s_mac", "s_system_size", "s_volume_info", "b_online", "b_is_new");
            }

            public static class ByValue extends DisplayLxy.CLibrary.Lxy_box_perperty implements com.sun.jna.Structure.ByValue {
                public ByValue() {
                }
            }

            public static class ByReference extends DisplayLxy.CLibrary.Lxy_box_perperty implements com.sun.jna.Structure.ByReference {
                public ByReference() {
                }
            }
        }
    }
}
