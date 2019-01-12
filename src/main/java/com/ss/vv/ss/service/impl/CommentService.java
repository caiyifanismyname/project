package com.ss.vv.ss.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ss.vv.common.AbstractService;
import com.ss.vv.common.IOperations;
import com.ss.vv.ss.domain.Comment;
import com.ss.vv.ss.mapper.ICommentMapper;
import com.ss.vv.ss.service.ICommentService;

@Service("commentService")
public class CommentService extends AbstractService<Comment, Comment> implements ICommentService{
	
	public CommentService() {
		this.setTableName("comment");
	}

	@Resource
	private ICommentMapper iCommentMapper;

	@Override
	protected IOperations<Comment, Comment> getMapper() {
		return iCommentMapper;
	}
	@Override
	public void setTableName(String tableName){
		this.tableName = tableName;
	}

}
