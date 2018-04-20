package com.anso.mapalao.service;

import com.anso.mapalao.base.BaseService;
import com.anso.mapalao.entity.Recording;
import com.anso.mapalao.entity.RecordingExample;

import java.util.List;

/**
 * @author qiumeng
 * @Package com.anso.mapalao.service
 * @Description:
 * @date 2018 2018/4/20 9:35
 */
public interface RecordingService extends BaseService<Recording,RecordingExample> {

    void batchInsert(List<Recording> recordings);
}
