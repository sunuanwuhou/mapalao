package com.anso.mapalao.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.Date;

public class Recording {

    @XStreamAlias("id")
    private Integer id;

    @XStreamAlias("siteid")
    private Integer siteid;

    @XStreamAlias("number")
    private String number;

    @XStreamAlias("rst")
    private String rst;

    @XStreamAlias("ret")
    private String ret;

    @XStreamAlias("siteidtext")
    private String siteidtext;

    /** */
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSiteid() {
        return siteid;
    }

    public void setSiteid(Integer siteid) {
        this.siteid = siteid;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    public String getRst() {
        return rst;
    }

    public void setRst(String rst) {
        this.rst = rst == null ? null : rst.trim();
    }

    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret == null ? null : ret.trim();
    }

    public String getSiteidtext() {
        return siteidtext;
    }

    public void setSiteidtext(String siteidtext) {
        this.siteidtext = siteidtext == null ? null : siteidtext.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}