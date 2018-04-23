package com.anso.mapalao.vo;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author qiumeng
 * @Package com.anso.mapalao.vo
 * @Description:
 * @date 2018 2018/4/23 20:09
 */
@XStreamAlias("message")
public class Message {

    @XStreamAlias("id")
    private String id;

    @XStreamAlias("number")
    private String number;

    @XStreamAlias("dateReceived")
    private String dateReceived;

    @XStreamAlias("dateCredited")
    private String dateCredited;

    @XStreamAlias("network")
    private String network;

    @XStreamAlias("message")
    private String message;

    @XStreamAlias("format")
    private String format;

    @XStreamAlias("source")
    private String source;

    @XStreamAlias("batteryCondition")
    private String batteryCondition;

    @XStreamAlias("signalStrength")
    private String signalStrength;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(String dateReceived) {
        this.dateReceived = dateReceived;
    }

    public String getDateCredited() {
        return dateCredited;
    }

    public void setDateCredited(String dateCredited) {
        this.dateCredited = dateCredited;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getBatteryCondition() {
        return batteryCondition;
    }

    public void setBatteryCondition(String batteryCondition) {
        this.batteryCondition = batteryCondition;
    }

    public String getSignalStrength() {
        return signalStrength;
    }

    public void setSignalStrength(String signalStrength) {
        this.signalStrength = signalStrength;
    }
}
