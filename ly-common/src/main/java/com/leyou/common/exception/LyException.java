package com.leyou.common.exception;

import com.leyou.common.enums.ExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by lidatu on 2019/01/03 20:05
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LyException extends RuntimeException {
	private ExceptionEnum exceptionEnum; //封装枚举对象 枚举对象中有code和msg

}
