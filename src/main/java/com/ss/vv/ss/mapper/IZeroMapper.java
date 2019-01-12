package com.ss.vv.ss.mapper;

import com.ss.vv.common.IOperations;
import com.ss.vv.ss.domain.Zero;

import java.util.Map;

public interface IZeroMapper extends IOperations<Zero, Zero>{
    public Zero selectLogin(Map<String,Object> map);
}
