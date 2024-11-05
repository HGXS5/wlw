//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.dev.display;

import com.grean.station.domain.DO.cfg.CfgFactor;
import com.grean.station.domain.DO.rec.RecDataFactor;
import com.grean.station.domain.DO.rec.RecDataTime;
import com.grean.station.utils.CrcUtil;
import com.grean.station.utils.Utility;
import java.util.Iterator;
import java.util.List;

public class DisplayNova extends DisplayService {
    public DisplayNova() {
    }

    public void doQuery() {
        if (this.factorList != null) {
            RecDataTime recTime = this.monitorService.getRecMapper().getLastRecTimeHour();
            List recDataList;
            if (recTime != null) {
                recDataList = this.monitorService.getRecMapper().getRecDataHourListByTime(recTime);
            } else {
                recDataList = null;
            }

            if (recDataList != null) {
                Iterator var3 = this.factorList.iterator();

                label156:
                while(true) {
                    while(true) {
                        if (!var3.hasNext()) {
                            break label156;
                        }

                        CfgFactor cfgFactor = (CfgFactor)var3.next();
                        Iterator var5 = recDataList.iterator();

                        while(var5.hasNext()) {
                            RecDataFactor recDataFactor = (RecDataFactor)var5.next();
                            if (recDataFactor.getFactorID() == cfgFactor.getId()) {
                                cfgFactor.setDataMea(recDataFactor.getDataValue());
                                break;
                            }
                        }
                    }
                }
            }

            byte[] bHead = new byte[]{-86, -1, -1, -120, 1, 0};
            byte[] bItem = new byte[]{91, 105, 116, 101, 109, 45, 49, 93, 13, 10};
            byte[] bParam = new byte[]{112, 97, 114, 97, 109, 61, 50, 48, 48, 44, 49, 44, 49, 44, 49, 44, 48, 44, 53, 44, 49, 44, 48, 44, 49};
            byte[] bTmp = new byte[1024];
            int bPos = 0;

            int i;
            for(i = 0; i < bHead.length; ++i) {
                bTmp[bPos] = bHead[i];
                if (bPos < 1020) {
                    ++bPos;
                }
            }

            for(i = 0; i < bItem.length; ++i) {
                bTmp[bPos] = bItem[i];
                if (bPos < 1020) {
                    ++bPos;
                }
            }

            for(i = 0; i < bParam.length; ++i) {
                bTmp[bPos] = bParam[i];
                if (bPos < 1020) {
                    ++bPos;
                }
            }

            for(i = 0; i < 4; ++i) {
                int index = this.showPage * 4 + i;
                String strText;
                if (index < this.factorList.size()) {
                    String strName = ((CfgFactor)this.factorList.get(index)).getName();
                    String strData;
                    if (((CfgFactor)this.factorList.get(index)).getDataMea() != null) {
                        strData = String.format("%.2f", ((CfgFactor)this.factorList.get(index)).getDataMea());
                    } else {
                        strData = "0.00";
                    }

                    if (strName.equals("高锰酸盐指数")) {
                        strName = "高指数";
                    }

                    if (strName.equals("pH")) {
                        strText = "\r\ntxt" + (i + 1) + "=0," + (16 * i + 32) + ",3,1212,1,000000,0, " + String.format("%-8s", strName) + strData + ",96,16,0";
                    } else {
                        String strFormat;
                        if (strName.length() <= 8) {
                            strFormat = "%-" + (8 - strName.length()) + "s";
                        } else {
                            strFormat = "%-s";
                        }

                        strText = "\r\ntxt" + (i + 1) + "=0," + (16 * i + 32) + ",3,1212,1,000000,0, " + String.format(strFormat, strName) + strData + ",96,16,0";
                    }
                } else {
                    strText = "\r\ntxt" + (i + 1) + "=0," + (16 * i + 32) + ",3,1212,1,000000,0, ,96,16,0";
                }

                String strParam = "\r\ntxtparam" + (i + 1) + "=0,0";
                byte[] bText = strText.getBytes();
                byte[] bTextParam = strParam.getBytes();

                int k;
                for(k = 0; k < bText.length; ++k) {
                    bTmp[bPos] = bText[k];
                    if (bPos < 1020) {
                        ++bPos;
                    }
                }

                for(k = 0; k < bTextParam.length; ++k) {
                    bTmp[bPos] = bTextParam[k];
                    if (bPos < 1020) {
                        ++bPos;
                    }
                }
            }

            String strTime = "\r\ntime1=0,0,96,32,600,1,000000,1212,3,0,1,0,1,1,1,1,1,1,0,0,1";
            byte[] bTime = strTime.getBytes();

            for(i = 0; i < strTime.length(); ++i) {
                bTmp[bPos] = bTime[i];
                if (bPos < 1020) {
                    ++bPos;
                }
            }

            byte[] bQuery = new byte[bPos + 3];

            int iResult;
            for(iResult = 0; iResult < bPos; ++iResult) {
                bQuery[iResult] = bTmp[iResult];
            }

            bQuery[bPos] = -52;
            iResult = CrcUtil.getCrc16_MCRF4XX(bQuery, bQuery.length - 2);
            byte[] tmp_bytes = Utility.getByteArray(iResult);
            bQuery[bQuery.length - 2] = tmp_bytes[3];
            bQuery[bQuery.length - 1] = tmp_bytes[2];
            this.mInterComm.clearRecv(2 * this.mChannel);
            this.mInterComm.Send(bQuery);
            if (this.mInterComm != null) {
                this.setStatus(this.mInterComm.isConnect());
            }

            if (this.showPage * 4 + 4 < this.factorList.size()) {
                ++this.showPage;
            } else {
                this.showPage = 0;
            }

        }
    }
}
