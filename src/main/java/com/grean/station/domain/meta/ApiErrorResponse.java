//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.meta;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.grean.station.exception.ErrorCode;
import com.grean.station.exception.ServerException;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;

@JsonInclude(Include.NON_NULL)
public class ApiErrorResponse extends ApiResponse implements Serializable {
  private static final long serialVersionUID = -5011314791279335808L;
  private Boolean success = false;
  private String error;
  private String exception;
  private String message;
  private String path;

  public ApiErrorResponse() {
  }

  public ApiErrorResponse(String error, String message) {
    this.error = error;
    this.message = message;
  }

  public ApiErrorResponse(ServerException ex, HttpServletRequest request) {
    this.error = ex.getError();
    this.message = ex.getMessage();
    this.path = request.getRequestURI();
  }

  public ApiErrorResponse(Exception ex, HttpServletRequest request) {
    this.error = ErrorCode.API_ERROR.getCode();
    this.exception = ex.getClass().getSimpleName();
    this.message = ex.getMessage();
    this.path = request.getRequestURI();
  }

  public String getException() {
    return this.exception;
  }

  public void setException(String exception) {
    this.exception = exception;
  }

  public Boolean getSuccess() {
    return this.success;
  }

  public void setSuccess(Boolean success) {
    this.success = success;
  }

  public String getError() {
    return this.error;
  }

  public void setError(String error) {
    this.error = error;
  }

  public String getMessage() {
    return this.message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getPath() {
    return this.path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public static long getSerialVersionUID() {
    return -5011314791279335808L;
  }
}
