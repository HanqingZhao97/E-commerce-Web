package com.yunniu.lease.service;

import com.yunniu.lease.model.Result;
import com.yunniu.lease.model.TableResult;
import com.yunniu.lease.model.UserAskLog;
import com.yunniu.lease.model.UserRebate;

import javax.servlet.http.HttpServletRequest;

public interface UserRebateService {


    Result getUserRebateLog(UserRebate userRebate);

    TableResult getUserRebateList(HttpServletRequest request);

    Result updateUserAskLogType(UserAskLog userAskLog);


    Result addUserAskLog(UserAskLog userAskLog);


}
