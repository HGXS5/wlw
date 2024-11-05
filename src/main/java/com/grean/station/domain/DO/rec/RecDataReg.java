//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.DO.rec;

import java.sql.Timestamp;

public class RecDataReg {
  int devID;
  int factorID;
  int recSize = 60;
  Timestamp recTime;
  Object p1;
  Object p2;
  Object p3;
  Object p4;
  Object p5;
  Object p6;
  Object p7;
  Object p8;
  Object p9;
  Object p10;
  Object p11;
  Object p12;
  Object p13;
  Object p14;
  Object p15;
  Object p16;
  Object p17;
  Object p18;
  Object p19;
  Object p20;
  Object p21;
  Object p22;
  Object p23;
  Object p24;
  Object p25;
  Object p26;
  Object p27;
  Object p28;
  Object p29;
  Object p30;
  Object p31;
  Object p32;
  Object p33;
  Object p34;
  Object p35;
  Object p36;
  Object p37;
  Object p38;
  Object p39;
  Object p40;
  Object p41;
  Object p42;
  Object p43;
  Object p44;
  Object p45;
  Object p46;
  Object p47;
  Object p48;
  Object p49;
  Object p50;
  Object p51;
  Object p52;
  Object p53;
  Object p54;
  Object p55;
  Object p56;
  Object p57;
  Object p58;
  Object p59;
  Object p60;
  Object[] objectArray;

  public RecDataReg() {
    this.objectArray = new Object[this.recSize];
  }

  private void setValues() {
    this.p1 = this.objectArray[0];
    this.p2 = this.objectArray[1];
    this.p3 = this.objectArray[2];
    this.p4 = this.objectArray[3];
    this.p5 = this.objectArray[4];
    this.p6 = this.objectArray[5];
    this.p7 = this.objectArray[6];
    this.p8 = this.objectArray[7];
    this.p9 = this.objectArray[8];
    this.p10 = this.objectArray[9];
    this.p11 = this.objectArray[10];
    this.p12 = this.objectArray[11];
    this.p13 = this.objectArray[12];
    this.p14 = this.objectArray[13];
    this.p15 = this.objectArray[14];
    this.p16 = this.objectArray[15];
    this.p17 = this.objectArray[16];
    this.p18 = this.objectArray[17];
    this.p19 = this.objectArray[18];
    this.p20 = this.objectArray[19];
    this.p21 = this.objectArray[20];
    this.p22 = this.objectArray[21];
    this.p23 = this.objectArray[22];
    this.p24 = this.objectArray[23];
    this.p25 = this.objectArray[24];
    this.p26 = this.objectArray[25];
    this.p27 = this.objectArray[26];
    this.p28 = this.objectArray[27];
    this.p29 = this.objectArray[28];
    this.p30 = this.objectArray[29];
    this.p31 = this.objectArray[30];
    this.p32 = this.objectArray[31];
    this.p33 = this.objectArray[32];
    this.p34 = this.objectArray[33];
    this.p35 = this.objectArray[34];
    this.p36 = this.objectArray[35];
    this.p37 = this.objectArray[36];
    this.p38 = this.objectArray[37];
    this.p39 = this.objectArray[38];
    this.p40 = this.objectArray[39];
    this.p41 = this.objectArray[40];
    this.p42 = this.objectArray[41];
    this.p43 = this.objectArray[42];
    this.p44 = this.objectArray[43];
    this.p45 = this.objectArray[44];
    this.p46 = this.objectArray[45];
    this.p47 = this.objectArray[46];
    this.p48 = this.objectArray[47];
    this.p49 = this.objectArray[48];
    this.p50 = this.objectArray[49];
    this.p51 = this.objectArray[50];
    this.p52 = this.objectArray[51];
    this.p53 = this.objectArray[52];
    this.p54 = this.objectArray[53];
    this.p55 = this.objectArray[54];
    this.p56 = this.objectArray[55];
    this.p57 = this.objectArray[56];
    this.p58 = this.objectArray[57];
    this.p59 = this.objectArray[58];
    this.p60 = this.objectArray[59];
  }

  public int getDevID() {
    return this.devID;
  }

  public void setDevID(int devID) {
    this.devID = devID;
  }

  public int getFactorID() {
    return this.factorID;
  }

  public void setFactorID(int factorID) {
    this.factorID = factorID;
  }

  public Timestamp getRecTime() {
    return this.recTime;
  }

  public void setRecTime(Timestamp recTime) {
    this.recTime = recTime;
  }

  public void setRegParams(Object[] regParams) {
    int j;
    for(j = 0; j < regParams.length; ++j) {
      this.objectArray[j] = regParams[j];
    }

    for(j = regParams.length; j < this.recSize; ++j) {
      this.objectArray[j] = null;
    }

    this.setValues();
  }

  public int getRecSize() {
    return this.recSize;
  }

  public void setRecSize(int recSize) {
    this.recSize = recSize;
  }
}
