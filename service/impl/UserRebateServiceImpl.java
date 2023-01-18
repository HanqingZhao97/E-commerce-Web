package com.yunniu.lease.service.impl;

import com.yunniu.lease.dao.UserDao;
import com.yunniu.lease.model.*;
import com.yunniu.lease.service.UserRebateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
public class UserRebateServiceImpl implements UserRebateService {

    @Resource
    private UserDao userDao;


    //用户提现记录查询
    @Override
    public TableResult getUserRebateList(HttpServletRequest request) {
        Map<String, Object> params = Pages.getParams(request);
        List<UserAskLog> userAskLogList = userDao.getUserAskLogList(params);
        Integer userAskLogCount = userDao.getUserAskLogCount(params);
        return new TableResult(userAskLogCount, userAskLogList);
    }

    @Override
    public Result updateUserAskLogType(UserAskLog userAskLog) {
        UserAskLog userLog = userDao.getUserAskLog(userAskLog.getUserAskLogId());
        userAskLog.setUserDeposits(userLog.getUserDeposits() - userLog.getUserAskDeposits());
        Integer userAskType = userAskLog.getUserAskType();
        if (userAskType == 1) {
            System.out.println("提现");
        }
        Integer res = userDao.updateUserAskLogType(userAskLog);
        if (res >= 1) {
            return new Result();
        }
        return new Result(103);
    }

    @Override
    public Result addUserAskLog(UserAskLog userAskLog) {
        String userId = userAskLog.getUserId();
        User user = userDao.getUserById(userId);
        Double deposits = user.getSumNum() - userAskLog.getUserAskDeposits();
        if (deposits < 0) {
            return new Result(103, "余额不足");
        }
        userAskLog.setUserDeposits(user.getSumNum());
        User u = new User();
        u.setUserId(userId);
        u.setSumNum(deposits);
        Integer res = userDao.updateUserSumNum(u);
        Integer res1 = userDao.addUserAskLog(userAskLog);
        if (res > 0 && res1 > 0) {
            return new Result();
        }
        return new Result(103);
    }


    @Override
    public Result getUserRebateLog(UserRebate userRebate) {
        return new Result(userDao.getUserRebateList(userRebate));
    }


}
