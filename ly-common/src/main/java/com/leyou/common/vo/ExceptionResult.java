package com.leyou.common.vo;

import com.leyou.common.enums.ExceptionEnum;
import lombok.Data;

/**
 * 将返回的结果封装成对象
 * Created by lidatu on 2019/01/03 20:17
 */

@Data
public class ExceptionResult {

	private int status;
		private String message;
		private long timestamp;

	public ExceptionResult(ExceptionEnum em) {//构造函数 传入枚举
			this.status = em.getCode();
			this.message = em.getMsg();
			this.timestamp = System.currentTimeMillis();//时间戳
	}
}
