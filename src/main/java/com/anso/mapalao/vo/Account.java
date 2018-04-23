package com.anso.mapalao.vo;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

/**
 * @author qiumeng
 * @Package com.anso.mapalao.vo
 * @Description:
 * @date 2018 2018/4/23 18:00
 */
@XStreamAlias("account")
public class  Account {

    @XStreamAlias("id")
    private  String id;
    @XStreamAlias("name")
    private  String name;
    @XStreamAlias("numberOfLoggers")
    private  String numberOfLoggers;
    @XStreamAlias("dateCreated")
    private  String dateCreated;
    @XStreamImplicit(itemFieldName="loggers")
    private List<Loggers> loggers;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumberOfLoggers() {
        return numberOfLoggers;
    }

    public void setNumberOfLoggers(String numberOfLoggers) {
        this.numberOfLoggers = numberOfLoggers;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public List<Loggers> getLoggers() {
        return loggers;
    }

    public void setLoggers(List<Loggers> loggers) {
        this.loggers = loggers;
    }
}
