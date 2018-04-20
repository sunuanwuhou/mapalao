package com.anso.mapalao.dao;

import com.anso.mapalao.entity.Recording;
import com.anso.mapalao.entity.RecordingExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RecordingMapper {
    int countByExample(RecordingExample example);

    int deleteByExample(RecordingExample example);

    int insert(Recording record);

    int insertSelective(Recording record);

    List<Recording> selectByExample(RecordingExample example);

    int updateByExampleSelective(@Param("record") Recording record, @Param("example") RecordingExample example);

    int updateByExample(@Param("record") Recording record, @Param("example") RecordingExample example);

    void batchInsert(List<Recording> roleList);
}