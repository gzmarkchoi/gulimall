package com.mci.gulimall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 产品评价回复表
 * 
 * @author gzmarkchoi
 * @email @gmail.com
 * @date 2021-01-05 15:28:33
 */
@Data
@TableName("pms_comment_replay")
public class CommentReplayEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 
	 */
	private Long commentId;
	/**
	 * 
	 */
	private String memberNickName;
	/**
	 * 
	 */
	private String memberIcon;
	/**
	 * 
	 */
	private String content;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 评论人员类型；0->会员；1->管理员
	 */
	private Integer type;

}
