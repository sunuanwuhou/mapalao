package com.anso.mapalao.service;

import com.anso.mapalao.base.BaseService;
import com.anso.mapalao.entity.Record;
import com.anso.mapalao.entity.RecordExample;

import java.util.List;

/**
 * @author qiumeng
 * @Package com.anso.mapalao.service
 * @Description:
 * @date 2018 2018/4/20 9:35
 */
public interface RecordService  extends BaseService<Record,RecordExample> {

    void batchInsert(List<Record> recordings);
}
