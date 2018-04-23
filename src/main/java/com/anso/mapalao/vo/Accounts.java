package com.anso.mapalao.vo;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author qiumeng
 * @Package com.anso.mapalao.vo
 * @Description:
 * @date 2018 2018/4/23 17:59
 */
@XStreamAlias("accounts")
public class Accounts {

    @XStreamAlias("APIVersion")
    private String APIVersion;
    @XStreamAlias("account")
    private Account account;

    public String getAPIVersion() {
        return APIVersion;
    }

    public void setAPIVersion(String APIVersion) {
        this.APIVersion = APIVersion;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
