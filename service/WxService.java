package com.yunniu.lease.service;

import com.yunniu.lease.model.Result;
import org.jdom.JDOMException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface WxService {

    Result login(String code);

    Result wxPay(String userId, String orderId, HttpServletRequest request) throws Exception;

    void notify(HttpServletRequest request, HttpServletResponse response) throws IOException, JDOMException;

}
