package com.leyou.item.web;

import com.leyou.item.pojo.Category;
import com.leyou.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by lidatu on 2019/01/08 09:08
 */

@RestController
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	/**
	 * 根据父节点id查询商品分类集合
	 * @param pid
	 * @return
	 */
	@GetMapping("/list")
	public ResponseEntity<List<Category>> queryCategoryListByPid(@RequestParam("pid") Long pid){
		return ResponseEntity.ok(categoryService.queryCategoryListByPid(pid));//ResponseEntity.ok()：简写
	}

	/**
	 * 根据多个商品分类的id查询商品分类集合--搜索
	 * @param ids
	 * @return
	 */
	@GetMapping("/list/ids")
	public ResponseEntity<List<Category>> queryCategoryByIds(@RequestParam("ids") List<Long> ids){
		return ResponseEntity.ok(categoryService.queryByIds(ids));
	}

	/**
	 * 点击修改品牌 回显数据-品牌和分类
	 * @param ids
	 * @return
	 */
	@GetMapping("/bid/{ids}")
	public ResponseEntity<List<Category>> queryCategoryByBIds(@PathVariable("ids") Long ids){
		return ResponseEntity.ok(categoryService.queryByBrandIds(ids));
	}

	/**
	 * 根据3级分类id，查询1~3级的分类   ---单个三级导航栏
	 * @param id
	 * @return
	 */
	@GetMapping("all/level")
	public ResponseEntity<List<Category>> queryAllByCid3(@RequestParam("id") Long id){
		List<Category> list = categoryService.queryAllByCid3(id);
		if (list == null || list.size() < 1) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(list);
	}

}
