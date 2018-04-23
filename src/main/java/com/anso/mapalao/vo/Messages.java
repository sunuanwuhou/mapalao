package com.anso.mapalao.vo;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

/**
 * @author qiumeng
 * @Package com.anso.mapalao.vo
 * @Description:
 * @date 2018 2018/4/23 20:09
 */
@XStreamAlias("messages")
public class Messages {

    @XStreamImplicit(itemFieldName="message")
    private List<Message> message;

    public List<Message> getMessage() {
        return message;
    }

    public void setMessage(List<Message> message) {
        this.message = message;
    }
}
