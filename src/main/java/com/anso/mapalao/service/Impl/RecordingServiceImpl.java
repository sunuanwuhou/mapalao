package com.anso.mapalao.service.Impl;

import com.anso.mapalao.annotation.BaseService;
import com.anso.mapalao.base.BaseServiceImpl;
import com.anso.mapalao.dao.RecordingMapper;
import com.anso.mapalao.entity.Recording;
import com.anso.mapalao.entity.RecordingExample;
import com.anso.mapalao.service.RecordingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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



    @Override
    public List<String> getNumberList() {
        List<String> getNumberList=new ArrayList<>();
        List<Recording> lastRecordingList = getLastRecordingList();
        if(!CollectionUtils.isEmpty(lastRecordingList)){
            for (Recording recording : lastRecordingList) {
                getNumberList.add(recording.getNumber());
            }
        }
        return getNumberList;
    }

    @Override
    public List<Integer> getIdList() {
        List<Integer> getIdList=new ArrayList<>();
        List<Recording> lastRecordingList = getLastRecordingList();
        if(!CollectionUtils.isEmpty(lastRecordingList)){
            for (Recording recording : lastRecordingList) {
                getIdList.add(recording.getId());
            }
        }
        return getIdList;
    }

    public List<Recording> getLastRecordingList(){
        List<Recording> retList=new ArrayList<>();
        List<Recording> recordingsList = recordingMapper.selectByExample(new RecordingExample());
        if(!CollectionUtils.isEmpty(recordingsList)){
            Collection<List<Recording>> values = recordingsList.stream().collect(Collectors.groupingBy(Recording::getCreateTime)).values();
            List<List<Recording>> valuList=new ArrayList<>(values);
            retList = valuList.get(values.size() - 1);
        }
        return retList;
    }
}
