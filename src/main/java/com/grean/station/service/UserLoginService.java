//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service;

import com.grean.station.dao.CfgMapper;
import com.grean.station.domain.DO.cfg.CfgUser;
import com.grean.station.domain.request.LoginForm;
import com.grean.station.domain.request.UpdatePassword;
import com.grean.station.domain.response.UserLoginResponse;
import com.grean.station.exception.ErrorCode;
import com.grean.station.exception.ServerException;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserLoginService {
    @Autowired
    CfgMapper cfgMapper;
    @Autowired
    MonitorService monitorService;
    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;

    public UserLoginService() {
    }

    public UserLoginResponse login(LoginForm loginUser) {
        this.checkLoginForm(loginUser);
        CfgUser cfgUser = this.cfgMapper.getCfgUserByName(loginUser.getUsername());
        this.monitorService.saveRecLogUser(cfgUser.getNick(), "登录");
        UserLoginResponse response = new UserLoginResponse();
        response.setName(cfgUser.getUser());
        response.setNick(cfgUser.getNick());
        response.setAvata("./assets/img/zorro.svg");
        DateTime loginTime = new DateTime();
        String strToken = cfgUser.getGroup_name();
        byte var6 = -1;
        switch(strToken.hashCode()) {
            case 817585787:
                if (strToken.equals("普通用户")) {
                    var6 = 0;
                }
                break;
            case 972353876:
                if (strToken.equals("管理用户")) {
                    var6 = 2;
                }
                break;
            case 1128972851:
                if (strToken.equals("运维用户")) {
                    var6 = 1;
                }
        }

        switch(var6) {
            case 0:
                response.setGroupIndex(1);
                loginTime = (new DateTime()).plusDays(180);
                break;
            case 1:
                loginTime = (new DateTime()).plusHours(3);
                response.setGroupIndex(2);
                break;
            case 2:
                loginTime = (new DateTime()).plusHours(3);
                response.setGroupIndex(3);
        }

        strToken = this.userService.addTokenUser(cfgUser.getUser(), loginTime);
        response.setToken(strToken);
        return response;
    }

    private void checkLoginForm(LoginForm loginUser) {
        if (loginUser.getUsername() != null && loginUser.getPassword() != null && loginUser.getUsername().trim().length() != 0 && loginUser.getPassword().trim().length() != 0) {
            CfgUser cfgUser = this.cfgMapper.getCfgUserByName(loginUser.getUsername());
            if (cfgUser == null) {
                throw new ServerException(ErrorCode.FIELD_ERROR.getCode(), "输入的用户名不存在");
            } else {
                boolean isPWDMatch = this.passwordEncoder.matches(loginUser.getPassword(), cfgUser.getPassword());
                if (!isPWDMatch) {
                    throw new ServerException(ErrorCode.FIELD_ERROR.getCode(), "请输入正确的用户名或密码");
                }
            }
        } else {
            throw new ServerException(ErrorCode.FIELD_ERROR.getCode(), "用户名或密码不能为空");
        }
    }

    public boolean updateUserPwd(UpdatePassword updatePassword) {
        CfgUser cfgUser = this.cfgMapper.getCfgUserByName(updatePassword.getUserName());
        if (this.passwordEncoder.matches(updatePassword.getOldPassword(), cfgUser.getPassword())) {
            cfgUser.setPassword(this.passwordEncoder.encode(updatePassword.getNewPassword()));
            this.cfgMapper.updateCfgUserPassword(cfgUser);
            return true;
        } else {
            return false;
        }
    }

    public boolean updateAccessPwd(UpdatePassword updatePassword) {
        CfgUser cfgUser = this.cfgMapper.getCfgUserByName(updatePassword.getUserName());
        if (updatePassword.getOldPassword().equals(cfgUser.getAccess())) {
            cfgUser.setAccess(updatePassword.getNewPassword());
            this.cfgMapper.updateCfgAccessPassword(cfgUser);
            return true;
        } else {
            return false;
        }
    }
}
