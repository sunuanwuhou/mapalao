package com.anso.mapalao.vo;

import com.anso.mapalao.entity.Recording;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

/**
 * @author qiumeng
 * @Package com.anso.mapalao.entity
 * @Description:
 * @date 2018 2018/4/19 22:14
 */
@XStreamAlias("recordings")
public class Recordings {

    @XStreamAlias("APIVersion")
    private String apiversion;
    @XStreamImplicit(itemFieldName="name")
    private List<Recording> recording;

    public String getApiversion() {
        return apiversion;
    }

    public void setApiversion(String apiversion) {
        this.apiversion = apiversion;
    }
    public List<Recording> getRecording() {
        return recording;
    }

    public void setRecording(List<Recording> recording) {
        this.recording = recording;
    }
}
