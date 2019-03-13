package com.leyou.item.web;

import com.leyou.common.vo.PageResult;
import com.leyou.item.pojo.Brand;
import com.leyou.item.service.BrandService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by lidatu on 2019/01/08 20:15
 */

@RestController
@RequestMapping("brand")
public class BrandController {

	@Autowired
	private BrandService brandService;

	/**
	 * 分页查询品牌
	 * @param page
	 * @param rows
	 * @param sortBy
	 * @param desc
	 * @param key
	 * @return
	 */
	@GetMapping("page")
	public ResponseEntity<PageResult<Brand>> queryBrandByPage(
			@RequestParam(value = "page",defaultValue = "1") Integer page,
			@RequestParam(value = "rows",defaultValue = "5") Integer rows,
			@RequestParam(value = "sortBy",required = false) String sortBy,
			@RequestParam(value = "desc",defaultValue = "1") Boolean desc,
			@RequestParam(value = "key",required = false) String key
	){
		return ResponseEntity.ok(brandService.queryBrandByPage(page,rows,sortBy,desc,key));
	}

	/**
	 * 新增品牌 Brand接收三参数(名称 首字母 分类类型) cids spring会自动解析cids成一个List 功能强大
	 * @param brand
	 * @param cids
	 * @return
	 */
	@PostMapping
	public ResponseEntity<Void> saveBrand(Brand brand, @RequestParam("cids") List<Long> cids){
		brandService.saveBrand(brand,cids);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	/**
	 * 修改品牌
	 * @param brand
	 * @param cids
	 * @return
	 */
	@PutMapping
	public ResponseEntity<Void> updateBrand(Brand brand, @RequestParam("cids")List<Long> cids){
		brandService.updateBrand(brand,cids);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	/**
	 * 根据分类查询品牌
	 * @param cid
	 * @return
	 */
	@GetMapping("cid/{cid}")
	public ResponseEntity<List<Brand>> queryBrandByCategory(@PathVariable("cid") Long cid){
		return ResponseEntity.ok(brandService.queryBrandByCategoryId(cid));
	}

	/**
	 * 根据id查询品牌--回显
	 * @param id
	 * @return
	 */
	@GetMapping("{id}")
	public ResponseEntity<Brand> queryBrandById(@PathVariable("id") Long id){
		return ResponseEntity.ok(brandService.queryById(id));
	}

	/**
	 * 删除品牌
	 * @param id
	 * @return
	 */
	@DeleteMapping("{id}")
	public ResponseEntity<Void> deleteBrand(@PathVariable("id") Long id){
		brandService.deleteBrand(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	/**
	 * 聚合品牌 通过一堆id查询品牌
	 * @param ids
	 * @return
	 */
	@GetMapping("list")
	public  ResponseEntity<List<Brand>> queryBrandByIds(@RequestParam("ids") List<Long> ids){
			return ResponseEntity.ok(brandService.queryBrandByIds(ids));
	}
}
