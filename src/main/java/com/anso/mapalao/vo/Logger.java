package com.anso.mapalao.vo;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author qiumeng
 * @Package com.anso.mapalao.vo
 * @Description:
 * @date 2018 2018/4/23 20:08
 */
@XStreamAlias("logger")
public class Logger {
    @XStreamAlias("id")
    private String id;
    @XStreamAlias("mobileNumber")
    private String mobileNumber;
    @XStreamAlias("serialNumber")
    private String serialNumber;
    @XStreamAlias("siteName")
    private String siteName;
    @XStreamAlias("maintenanceflag")
    private String maintenanceflag;

    @XStreamAlias("messages")
    private Messages messages;

    public Messages getMessages() {
        return messages;
    }

    public void setMessages(Messages messages) {
        this.messages = messages;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getMaintenanceflag() {
        return maintenanceflag;
    }

    public void setMaintenanceflag(String maintenanceflag) {
        this.maintenanceflag = maintenanceflag;
    }
}
