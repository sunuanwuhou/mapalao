package com.anso.mapalao.service.Impl;

import com.anso.mapalao.annotation.BaseService;
import com.anso.mapalao.base.BaseServiceImpl;
import com.anso.mapalao.entity.Record;
import com.anso.mapalao.entity.RecordExample;
import com.anso.mapalao.dao.RecordMapper;
import com.anso.mapalao.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author qiumeng
 * @Package com.anso.mapalao.service
 * @Description:
 * @date 2018 2018/4/20 9:36
 */
@Service
@Transactional
@BaseService
public class RecordServiceImpl extends BaseServiceImpl<RecordMapper,Record,RecordExample> implements RecordService {

    @Autowired
    private RecordMapper recordMapper;

    @Override
    public void batchInsert(List<Record> recordings) { this.recordMapper.batchInsert(recordings);
    }
}
