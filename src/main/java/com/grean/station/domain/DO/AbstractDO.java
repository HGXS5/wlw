//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.DO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.sql.Timestamp;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties({"add_time", "update_time", "delete"})
public abstract class AbstractDO {
  private Integer id;
  private Timestamp add_time;
  private Timestamp update_time;
  private Boolean delete;

  public AbstractDO() {
  }

  public String toString() {
    return "AbstractDO{id=" + this.id + ", add_time=" + this.add_time + ", update_time=" + this.update_time + ", delete=" + this.delete + '}';
  }

  public Boolean getDelete() {
    return this.delete;
  }

  public void setDelete(Boolean delete) {
    this.delete = delete;
  }

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Timestamp getAdd_time() {
    return this.add_time;
  }

  public void setAdd_time(Timestamp add_time) {
    this.add_time = add_time;
  }

  public Timestamp getUpdate_time() {
    return this.update_time;
  }

  public void setUpdate_time(Timestamp update_time) {
    this.update_time = update_time;
  }
}
