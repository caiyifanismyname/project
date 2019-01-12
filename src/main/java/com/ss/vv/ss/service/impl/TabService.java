package com.ss.vv.ss.service.impl;

import com.ss.vv.common.AbstractService;
import com.ss.vv.common.IOperations;
import com.ss.vv.ss.domain.Tab;
import com.ss.vv.ss.domain.User;
import com.ss.vv.ss.mapper.ITabMapper;
import com.ss.vv.ss.service.ITabService;
import com.ss.vv.ss.service.IUserService;
import com.ss.vv.ss.mapper.IUserMapper;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;




@Service("tabService")
public class TabService extends AbstractService<Tab, Tab> implements ITabService {


    public TabService() {
        this.setTableName("tab");
    }
    @Resource
    private ITabMapper tabMapper;

    @Override
    protected IOperations<Tab, Tab> getMapper() {
        return tabMapper;
    }
    @Override
    public void setTableName(String tableName){
        this.tableName = tableName;
    }






}

