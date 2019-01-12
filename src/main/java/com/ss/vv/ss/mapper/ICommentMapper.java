package com.ss.vv.ss.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.ss.vv.common.IOperations;
import com.ss.vv.ss.domain.Comment;
@Repository
public interface ICommentMapper extends IOperations<Comment, Comment>{
	
	@Select("select COUNT(username) from comment")
	int findAllCommentCount();
	
	@Select("select * from comment")
	List<Comment> findAllComment();
	
	
	@Select("select * from comment limit ${(pageNo-1)*10},10")
	List<Comment> findCommentList(@Param("pageNo")Integer pageNo);
	


}
