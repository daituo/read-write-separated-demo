package com.read.write.separated.demo.readwriteseparated.mapper;

import com.read.write.separated.demo.readwriteseparated.entity.Resource;
import com.read.write.separated.demo.readwriteseparated.entity.ResourceExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* 请添加描述
* @author FGVTH
* @time 2019-03-21
*/
public interface ResourceMapper {
    long countByExample(ResourceExample example);

    int deleteByExample(ResourceExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Resource record);

    int insertSelective(Resource record);

    List<Resource> selectByExample(ResourceExample example);

    Resource selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Resource record, @Param("example") ResourceExample example);

    int updateByExample(@Param("record") Resource record, @Param("example") ResourceExample example);

    int updateByPrimaryKeySelective(Resource record);

    int updateByPrimaryKey(Resource record);
}