//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.exception;

import com.grean.station.domain.meta.ApiErrorResponse;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice({"com.grean.station.controller"})
public class ExceptionHandler {
    static final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

    public ExceptionHandler() {
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({ServerException.class})
    @ResponseBody
    public ApiErrorResponse handleInputException(ServerException ex, HttpServletRequest request) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(ex, request);
        return apiErrorResponse;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({Exception.class})
    @ResponseBody
    public ApiErrorResponse handleException(Exception ex, HttpServletRequest request) {
        logger.error("Ops!", ex);
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(ex, request);
        return apiErrorResponse;
    }
}
