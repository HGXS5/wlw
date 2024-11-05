//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service;

import com.grean.station.domain.response.TokenUser;
import com.grean.station.utils.TokenHelper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    List<TokenUser> userList = new ArrayList();

    public UserService() {
    }

    public String addTokenUser(String strName, DateTime loginTime) {
        Iterator var3 = this.userList.iterator();

        TokenUser tokenUser;
        do {
            if (!var3.hasNext()) {
                String strToken = TokenHelper.getGUID();
                tokenUser = new TokenUser(strToken, strName, loginTime);
                this.userList.add(tokenUser);
                return strToken;
            }

            tokenUser = (TokenUser)var3.next();
        } while(!tokenUser.getName().equals(strName));

        tokenUser.setTimeout(loginTime);
        return tokenUser.getToken();
    }

    public void delTokenUser(String strToken) {
        Iterator var2 = this.userList.iterator();

        while(var2.hasNext()) {
            TokenUser tokenUser = (TokenUser)var2.next();
            if (tokenUser.getToken().equals(strToken)) {
                this.userList.remove(tokenUser);
                break;
            }
        }

    }

    public TokenUser getTokenUser(String strToken) {
        Iterator var2 = this.userList.iterator();

        TokenUser tokenUser;
        do {
            if (!var2.hasNext()) {
                return null;
            }

            tokenUser = (TokenUser)var2.next();
        } while(!tokenUser.getToken().equals(strToken));

        return tokenUser;
    }
}
