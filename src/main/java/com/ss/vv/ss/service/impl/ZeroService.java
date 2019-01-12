package com.ss.vv.ss.service.impl;

import com.ss.vv.common.AbstractService;
import com.ss.vv.common.IOperations;
import com.ss.vv.ss.domain.Zero;
import com.ss.vv.ss.service.IZeroService;
import com.ss.vv.ss.mapper.IZeroMapper;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;




@Service("zeroService")
public class ZeroService extends AbstractService<Zero, Zero> implements IZeroService {
	public ZeroService() {
		this.setTableName("zero");
	}

	@Resource
	private IZeroMapper zeroMapper;
	@Override
	protected IOperations<Zero, Zero> getMapper() {
		return zeroMapper;
	}
	@Override
	public void setTableName(String tableName){
		this.tableName = tableName;
	}
}
