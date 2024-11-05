//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.comm.netserver.tcp;

import com.grean.station.dao.CfgMapper;
import com.grean.station.dao.RecMapper;
import com.grean.station.domain.DO.cfg.CfgFactor;
import com.grean.station.domain.DO.cfg.CfgModbus;
import com.grean.station.domain.DO.rec.RecDataFactor;
import com.grean.station.domain.DO.rec.RecDataTime;
import com.grean.station.service.DbService;
import com.grean.station.utils.Utility;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TcpMessageParser {
  @Autowired
  CfgMapper cfgMapper;
  @Autowired
  RecMapper recMapper;
  @Autowired
  DbService dbService;
  @Value("${net_server_data:}")
  private String data;
  private static final Logger logger = LoggerFactory.getLogger("server");
  private byte[] bRegister = new byte[256];
  private List<CfgFactor> cfgFactorList;
  private List<CfgModbus> cfgModbusList;
  RecDataTime recDataTime;
  List<RecDataFactor> recDataList;

  public TcpMessageParser() {
    Timer timer = new Timer();
    timer.schedule(new TimerTask() {
      public void run() {
        TcpMessageParser.this.cfgFactorList = TcpMessageParser.this.dbService.getCfgFactorList();
        TcpMessageParser.this.cfgModbusList = TcpMessageParser.this.cfgMapper.getCfgModbusList();
        if (TcpMessageParser.this.cfgFactorList != null) {
          String var1 = TcpMessageParser.this.data;
          byte var2 = -1;
          switch(var1.hashCode()) {
            case 2255364:
              if (var1.equals("Hour")) {
                var2 = 0;
              }
          }

          Iterator var6;
          switch(var2) {
            case 0:
              TcpMessageParser.this.recDataTime = TcpMessageParser.this.recMapper.getLastRecTimeHour();
              if (TcpMessageParser.this.recDataTime == null) {
                return;
              }

              TcpMessageParser.this.recDataList = TcpMessageParser.this.recMapper.getRecDataHourListByTime(TcpMessageParser.this.recDataTime);
              break;
            default:
              TcpMessageParser.this.recDataTime = TcpMessageParser.this.recMapper.getLastRecTimeHour();
              Iterator var3;
              CfgFactor cfgFactor;
              if (TcpMessageParser.this.recDataTime == null) {
                TcpMessageParser.this.recDataList = new ArrayList();
                var3 = TcpMessageParser.this.cfgFactorList.iterator();

                while(var3.hasNext()) {
                  cfgFactor = (CfgFactor)var3.next();
                  if (cfgFactor.getDataMea() != null) {
                    RecDataFactor recDataFactor = new RecDataFactor();
                    recDataFactor.setFactorID(cfgFactor.getId());
                    recDataFactor.setDataValue(cfgFactor.getDataMea());
                    TcpMessageParser.this.recDataList.add(recDataFactor);
                  }
                }
              } else {
                TcpMessageParser.this.recDataList = TcpMessageParser.this.recMapper.getRecDataHourListByTime(TcpMessageParser.this.recDataTime);
                var3 = TcpMessageParser.this.cfgFactorList.iterator();

                label97:
                while(true) {
                  do {
                    do {
                      if (!var3.hasNext()) {
                        break label97;
                      }

                      cfgFactor = (CfgFactor)var3.next();
                    } while(cfgFactor.getDataMea() == null);
                  } while(cfgFactor.getType().toUpperCase().equals("PARM"));

                  boolean bCheck = false;
                  var6 = TcpMessageParser.this.recDataList.iterator();

                  while(var6.hasNext()) {
                    RecDataFactor recDataFactorxx = (RecDataFactor)var6.next();
                    if (recDataFactorxx.getFactorID() == cfgFactor.getId()) {
                      recDataFactorxx.setDataValue(cfgFactor.getDataMea());
                      bCheck = true;
                      break;
                    }
                  }

                  if (!bCheck) {
                    RecDataFactor recDataFactorx = new RecDataFactor();
                    recDataFactorx.setFactorID(cfgFactor.getId());
                    recDataFactorx.setDataValue(cfgFactor.getDataMea());
                    TcpMessageParser.this.recDataList.add(recDataFactorx);
                  }
                }
              }
          }

          DateTime dateTime = new DateTime(TcpMessageParser.this.recDataTime.getRecTime().getTime());
          TcpMessageParser.this.bRegister[0] = Utility.getBCDVal(dateTime.getYear() % 100);
          TcpMessageParser.this.bRegister[1] = Utility.getBCDVal(dateTime.getMonthOfYear());
          TcpMessageParser.this.bRegister[2] = Utility.getBCDVal(dateTime.getDayOfMonth());
          TcpMessageParser.this.bRegister[3] = Utility.getBCDVal(dateTime.getHourOfDay());
          TcpMessageParser.this.bRegister[4] = Utility.getBCDVal(dateTime.getMinuteOfHour());
          TcpMessageParser.this.bRegister[5] = Utility.getBCDVal(dateTime.getSecondOfMinute());
          Iterator var13 = TcpMessageParser.this.cfgModbusList.iterator();

          while(true) {
            while(true) {
              label67:
              while(true) {
                CfgModbus cfgModbus;
                do {
                  if (!var13.hasNext()) {
                    return;
                  }

                  cfgModbus = (CfgModbus)var13.next();
                } while(cfgModbus.getAddress() < 3);

                double dTmp = 0.0D;
                var6 = TcpMessageParser.this.cfgFactorList.iterator();

                while(var6.hasNext()) {
                  CfgFactor cfgFactorx = (CfgFactor)var6.next();
                  if (cfgModbus.getName().equals(cfgFactorx.getName())) {
                    Iterator var8 = TcpMessageParser.this.recDataList.iterator();

                    while(var8.hasNext()) {
                      RecDataFactor recDataFactorxxx = (RecDataFactor)var8.next();
                      if (recDataFactorxxx.getFactorID() == cfgFactorx.getId()) {
                        dTmp = recDataFactorxxx.getDataValue();
                        float fTmp = Float.parseFloat(dTmp + "");
                        byte[] bTmp = Utility.getByteArray(fTmp);
                        TcpMessageParser.this.bRegister[cfgModbus.getAddress() * 2] = bTmp[0];
                        TcpMessageParser.this.bRegister[cfgModbus.getAddress() * 2 + 1] = bTmp[1];
                        TcpMessageParser.this.bRegister[cfgModbus.getAddress() * 2 + 2] = bTmp[2];
                        TcpMessageParser.this.bRegister[cfgModbus.getAddress() * 2 + 3] = bTmp[3];
                        continue label67;
                      }
                    }
                    break;
                  }
                }
              }
            }
          }
        }
      }
    }, 5000L, 60000L);
  }

  public byte[] processMessage(byte[] byteMessages) {
    int address = Utility.getShort(byteMessages, 8);
    int length = Utility.getShort(byteMessages, 10);
    byte[] bLength = Utility.getByteArray(length * 2);
    byte[] bPkgLen = Utility.getByteArray(length * 2 + 3);
    switch(byteMessages[7]) {
      case 3:
      case 4:
        byte[] bSend;
        int i;
        if (address * 2 + length * 2 > 240) {
          bSend = new byte[9];

          for(i = 0; i < 4; ++i) {
            bSend[i] = byteMessages[i];
          }

          bSend[4] = 0;
          bSend[5] = 3;
          bSend[6] = byteMessages[6];
          bSend[7] = (byte)(128 + byteMessages[7]);
          bSend[8] = 2;
        } else {
          bSend = new byte[length * 2 + 9];

          for(i = 0; i < 4; ++i) {
            bSend[i] = byteMessages[i];
          }

          bSend[4] = bPkgLen[2];
          bSend[5] = bPkgLen[3];
          bSend[6] = byteMessages[6];
          bSend[7] = byteMessages[7];
          bSend[8] = bLength[3];

          for(i = 0; i < length * 2; ++i) {
            bSend[9 + i] = this.bRegister[address * 2 + i];
          }
        }

        return bSend;
      default:
        return byteMessages;
    }
  }
}
