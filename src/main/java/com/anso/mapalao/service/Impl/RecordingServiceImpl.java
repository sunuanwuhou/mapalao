package com.anso.mapalao.service.Impl;

import com.anso.mapalao.annotation.BaseService;
import com.anso.mapalao.base.BaseServiceImpl;
import com.anso.mapalao.entity.Recording;
import com.anso.mapalao.entity.RecordingExample;
import com.anso.mapalao.dao.RecordingMapper;
import com.anso.mapalao.service.RecordingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author qiumeng
 * @Package com.anso.mapalao.service.Impl
 * @Description:
 * @date 2018 2018/4/20 9:36
 */
@Service
@Transactional
@BaseService
public class RecordingServiceImpl extends BaseServiceImpl<RecordingMapper,Recording,RecordingExample> implements RecordingService {

    @Autowired
    RecordingMapper recordingMapper;

    @Override
    public void batchInsert(List<Recording> recordings) {
        recordingMapper.batchInsert(recordings);
    }
}
