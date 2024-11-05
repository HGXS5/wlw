//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.meta;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.io.Serializable;
import org.joda.time.DateTime;

@JsonInclude(Include.NON_NULL)
public class ApiResponse implements Serializable {
  private static final long serialVersionUID = -8990271803450171144L;
  private Boolean success = true;
  private Object data;
  private Object additional_data;
  private Object related_objects;
  private String message;
  private String request_time = (new DateTime()).toString();

  public ApiResponse() {
  }

  public ApiResponse(Object data) {
    this.data = data;
  }

  public Boolean getSuccess() {
    return this.success;
  }

  public void setSuccess(Boolean success) {
    this.success = success;
  }

  public Object getData() {
    return this.data;
  }

  public void setData(Object data) {
    this.data = data;
  }

  public Object getAdditional_data() {
    return this.additional_data;
  }

  public void setAdditional_data(Object additional_data) {
    this.additional_data = additional_data;
  }

  public Object getRelated_objects() {
    return this.related_objects;
  }

  public void setRelated_objects(Object related_objects) {
    this.related_objects = related_objects;
  }

  public String getMessage() {
    return this.message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public static long getSerialVersionUID() {
    return -8990271803450171144L;
  }

  public String getRequest_time() {
    return this.request_time;
  }

  public void setRequest_time(String request_time) {
    this.request_time = request_time;
  }
}
