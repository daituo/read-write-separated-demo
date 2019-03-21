package com.read.write.separated.demo.readwriteseparated.controller;

import com.alibaba.fastjson.JSON;
import com.read.write.separated.demo.readwriteseparated.entity.Resource;
import com.read.write.separated.demo.readwriteseparated.mapper.ResourceMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Author daituo
 * @Date 2019-3-21
 **/
@RestController
public class ResourceController {

    @javax.annotation.Resource
    private ResourceMapper resourceMapper;

    @GetMapping("/resource/{id}")
    public String findById(@PathVariable("id") Long id) {
        Resource resource = resourceMapper.selectByPrimaryKey(id);
        return JSON.toJSONString(resource);
    }

    @GetMapping("/resource/update")
    @Transactional
    public String update(Resource resource) {
        resourceMapper.updateByPrimaryKeySelective(resource);
        return "修改成功";
    }

}
