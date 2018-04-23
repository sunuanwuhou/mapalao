package com.anso.mapalao.vo;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author qiumeng
 * @Package com.anso.mapalao.vo
 * @Description:
 * @date 2018 2018/4/23 20:04
 */
@XStreamAlias("loggers")
public class Loggers {


    @XStreamAlias("logger")
    private Logger logger;

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }
}
