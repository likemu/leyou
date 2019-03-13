package com.leyou.item.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name="tb_category")
public class Category {

	@Id
	@KeySql(useGeneratedKeys = true)
	private Long id;
	private String name;
	private Long parentId;
	private Boolean isParent;
	private Integer sort;

}