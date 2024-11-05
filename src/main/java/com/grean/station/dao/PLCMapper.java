//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.dao;

import com.grean.station.domain.DO.plc.DefWord;
import com.grean.station.domain.DO.plc.KeyWord;
import com.grean.station.domain.DO.plc.StatusWord;
import com.grean.station.domain.DO.plc.VarWord;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PLCMapper {
  List<DefWord> getPLCDefWordList();

  List<KeyWord> getPLCKeyWordList();

  List<VarWord> getPLCVarWordList();

  List<StatusWord> getPLCStatusWordList();

  void updatePLCVarWord(VarWord var1);

  void truncatePLCDefWord();

  void addPLCDefWord(DefWord var1);
}
