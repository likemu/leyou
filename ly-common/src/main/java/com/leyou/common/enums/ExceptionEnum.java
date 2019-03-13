package com.leyou.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 枚举是指 具有固定实例个数 的类
 * Created by lidatu on 2019/01/03 20:07
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum  ExceptionEnum {

	BRAND_NOT_FOUND(400,"品牌不存在"),
	CATEGORY_NOT_FOUND(404,"商品分类没查到"),
	CATEGORY_SAVE_ERROR(404,"新增商品分类失败"),
	CATEGORY_DELETE_ERROR(404,"删除商品分类失败"),
	CATEGORY_UPDATE_ERROR(404,"修改商品分类失败"),
	SPEC_GROUP_NOT_FOUND(404,"商品规格组不存在"),
	SPEC_PARAM_NOT_FOUND(404,"商品规格参数不存在"),
	GOODS_NOT_FOUND(404,"商品不存在"),
	GOODS_DETAIL_NOT_FOUND(404,"商品详情不存在"),
	GOODS_SKU_NOT_FOUND(404,"商品SKU不存在"),
	GOODS_STOCK_NOT_FOUND(404,"商品库存不存在"),
	BRAND_SAVE_ERROR(500,"新增品牌失败"),
	BRAND_DELETE_ERROR(500,"新增品牌失败"),
	BRAND_UPDATE_ERROR(500,"修改品牌失败"),
	BRAND_ID_CANNOT_BE_NULL(400,"品牌id不能为空"),
	UPLOAD_FILE_ERROR(500,"文件上传失败"),
	INVALID_FILE_ERROR(400,"无效的文件类型"),
	GOODS_SAVE_ERROR(500,"新增商品失败"),
	GOODS_UPDATE_ERROR(500,"更新商品失败"),
	GOODS_DELETE_ERROR(500,"更新商品失败"),
	GOODS_ID_CANNOT_BE_NULL(400,"商品id不能为空"),
	INVALID_USER_DATA_TYPE(400,"用户的数据类型无效"),
	INVALID_VERIFY_CODE(400,"无效的验证码"),
	INVALID_USERNAME_PASSWORD(400,"用户名或密码错误"),
	CREATE_TOKEN_ERROR(400,"用户凭证生成失败"),
	UNAUTHORIZED(403,"未授权"),
	CART_NOT_FOUND(404,"购物车为空"),
	ORDER_NOT_FOUND(404,"订单不存在"),
	ORDER_DETAIL_NOT_FOUND(404,"订单详情不存在"),
	ORDER_STATUS_NOT_FOUND(404,"订单状态不存在"),
	CREATE_ORDER_ERROR(500,"创建订单失败"),
	STOCK_NOT_ENOUGH(500,"库存不足"),
	WX_PAY_ORDER_FAIL(500,"微信下单失败"),
	ORDER_STATUS_ERROR(400,"订单状态不正确"),
	INVALID_SIGN_ERROR(400,"无效的签名异常"),
	INVALID_ORDER_PARAM(500,"订单参数异常"),
	UPDATE_ORDER_STATUS_ERROR(500,"更新订单状态失败"),
	;
	private int code;
	private String msg;
}
















