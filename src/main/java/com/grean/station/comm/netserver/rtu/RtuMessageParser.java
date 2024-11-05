//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.comm.netserver.rtu;

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
public class RtuMessageParser {
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

  public RtuMessageParser() {
    Timer timer = new Timer();
    timer.schedule(new TimerTask() {
      public void run() {
        RtuMessageParser.this.cfgFactorList = RtuMessageParser.this.dbService.getCfgFactorList();
        RtuMessageParser.this.cfgModbusList = RtuMessageParser.this.cfgMapper.getCfgModbusList();
        if (RtuMessageParser.this.cfgFactorList != null) {
          String var1 = RtuMessageParser.this.data;
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
              RtuMessageParser.this.recDataTime = RtuMessageParser.this.recMapper.getLastRecTimeHour();
              if (RtuMessageParser.this.recDataTime == null) {
                return;
              }

              RtuMessageParser.this.recDataList = RtuMessageParser.this.recMapper.getRecDataHourListByTime(RtuMessageParser.this.recDataTime);
              break;
            default:
              RtuMessageParser.this.recDataTime = RtuMessageParser.this.recMapper.getLastRecTimeHour();
              Iterator var3;
              CfgFactor cfgFactor;
              if (RtuMessageParser.this.recDataTime == null) {
                RtuMessageParser.this.recDataList = new ArrayList();
                var3 = RtuMessageParser.this.cfgFactorList.iterator();

                while(var3.hasNext()) {
                  cfgFactor = (CfgFactor)var3.next();
                  if (cfgFactor.getDataMea() != null) {
                    RecDataFactor recDataFactor = new RecDataFactor();
                    recDataFactor.setFactorID(cfgFactor.getId());
                    recDataFactor.setDataValue(cfgFactor.getDataMea());
                    RtuMessageParser.this.recDataList.add(recDataFactor);
                  }
                }
              } else {
                RtuMessageParser.this.recDataList = RtuMessageParser.this.recMapper.getRecDataHourListByTime(RtuMessageParser.this.recDataTime);
                var3 = RtuMessageParser.this.cfgFactorList.iterator();

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
                  var6 = RtuMessageParser.this.recDataList.iterator();

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
                    RtuMessageParser.this.recDataList.add(recDataFactorx);
                  }
                }
              }
          }

          DateTime dateTime = new DateTime(RtuMessageParser.this.recDataTime.getRecTime().getTime());
          RtuMessageParser.this.bRegister[0] = Utility.getBCDVal(dateTime.getYear() % 100);
          RtuMessageParser.this.bRegister[1] = Utility.getBCDVal(dateTime.getMonthOfYear());
          RtuMessageParser.this.bRegister[2] = Utility.getBCDVal(dateTime.getDayOfMonth());
          RtuMessageParser.this.bRegister[3] = Utility.getBCDVal(dateTime.getHourOfDay());
          RtuMessageParser.this.bRegister[4] = Utility.getBCDVal(dateTime.getMinuteOfHour());
          RtuMessageParser.this.bRegister[5] = Utility.getBCDVal(dateTime.getSecondOfMinute());
          Iterator var13 = RtuMessageParser.this.cfgModbusList.iterator();

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
                var6 = RtuMessageParser.this.cfgFactorList.iterator();

                while(var6.hasNext()) {
                  CfgFactor cfgFactorx = (CfgFactor)var6.next();
                  if (cfgModbus.getName().equals(cfgFactorx.getName())) {
                    Iterator var8 = RtuMessageParser.this.recDataList.iterator();

                    while(var8.hasNext()) {
                      RecDataFactor recDataFactorxxx = (RecDataFactor)var8.next();
                      if (recDataFactorxxx.getFactorID() == cfgFactorx.getId()) {
                        dTmp = recDataFactorxxx.getDataValue();
                        float fTmp = Float.parseFloat(dTmp + "");
                        byte[] bTmp = Utility.getByteArray(fTmp);
                        RtuMessageParser.this.bRegister[cfgModbus.getAddress() * 2] = bTmp[0];
                        RtuMessageParser.this.bRegister[cfgModbus.getAddress() * 2 + 1] = bTmp[1];
                        RtuMessageParser.this.bRegister[cfgModbus.getAddress() * 2 + 2] = bTmp[2];
                        RtuMessageParser.this.bRegister[cfgModbus.getAddress() * 2 + 3] = bTmp[3];
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
    int address = Utility.getShort(byteMessages, 2);
    int length = Utility.getShort(byteMessages, 4);
    byte[] bLength = Utility.getByteArray(length * 2);
    switch(byteMessages[1]) {
      case 3:
      case 4:
        byte[] bSend;
        int crc_result;
        byte[] tmp_bytes;
        if (address * 2 + length * 2 > 240) {
          bSend = new byte[]{byteMessages[0], (byte)(128 + byteMessages[1]), 2, 0, 0};
          crc_result = Utility.calcCrc16(bSend, 0, bSend.length - 2);
          tmp_bytes = Utility.getByteArray(crc_result);
          bSend[bSend.length - 2] = tmp_bytes[2];
          bSend[bSend.length - 1] = tmp_bytes[3];
        } else {
          bSend = new byte[length * 2 + 5];
          bSend[0] = byteMessages[0];
          bSend[1] = byteMessages[1];
          bSend[2] = bLength[3];

          for(crc_result = 0; crc_result < length * 2; ++crc_result) {
            bSend[3 + crc_result] = this.bRegister[address * 2 + crc_result];
          }

          crc_result = Utility.calcCrc16(bSend, 0, bSend.length - 2);
          tmp_bytes = Utility.getByteArray(crc_result);
          bSend[bSend.length - 2] = tmp_bytes[2];
          bSend[bSend.length - 1] = tmp_bytes[3];
        }

        return bSend;
      default:
        return byteMessages;
    }
  }

  public Boolean passCrc(byte[] byteMessages) {
    int crc_result = Utility.calcCrc16(byteMessages, 0, byteMessages.length - 2);
    byte[] tmp_bytes = Utility.getByteArray(crc_result);
    return tmp_bytes[2] == byteMessages[byteMessages.length - 2] && tmp_bytes[3] == byteMessages[byteMessages.length - 1] ? true : false;
  }
}
