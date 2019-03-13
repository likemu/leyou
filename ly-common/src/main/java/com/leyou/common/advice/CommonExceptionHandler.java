package com.leyou.common.advice;

import com.leyou.common.exception.LyException;
import com.leyou.common.vo.ExceptionResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 异常通知类
 * Created by lidatu on 2019/01/03 19:47
 */
//@ControllerAdvice 默认：自动拦截所有的controller  注意扫描这个包的问题

@ControllerAdvice
public class CommonExceptionHandler {

	//返回的是错误信息 String
	@ExceptionHandler(LyException.class)
	public ResponseEntity<ExceptionResult> handleException(LyException e){
		//我们暂定返回状态码为400，然后从异常中获取友好提示信息
		//return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		return ResponseEntity.status(e.getExceptionEnum().getCode())
				.body(new ExceptionResult(e.getExceptionEnum()));//获取状态值 并将信息封装到异常结果（ExceptionResult）对象中返回
	}
}
