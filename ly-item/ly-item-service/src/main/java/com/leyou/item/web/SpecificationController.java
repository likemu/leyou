package com.leyou.item.web;

import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import com.leyou.item.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/spec")
public class SpecificationController {

    @Autowired
    private SpecificationService specificationService;

    /**
     * 根据分类id查询规格组
     * @param cid
     * @return
     */
    @GetMapping("groups/{cid}")
    public ResponseEntity<List<SpecGroup>> querySpecGroupsByCid(@PathVariable("cid") Long cid){
        return ResponseEntity.ok(specificationService.querySpecGroupByCid(cid));
    }

    /**
     * 新增商品分组/分类
     * @param group
     * @return   //,@RequestParam("cid") Long cid,@RequestParam("name") String name
     */
    @PostMapping("/group")
    public ResponseEntity<Void> saveGroup(@RequestBody SpecGroup group){
        specificationService.saveGroup(group);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 删除商品分组/分类
     * @param id
     * @return
     */
    @DeleteMapping("group/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable("id") Long id){
        specificationService.deleteGroup(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 修改商品分组/分类
     * @param group
     * @return
     */
    @PutMapping("/group")
    public ResponseEntity<Void> updateGroup(SpecGroup group){
        specificationService.updateGroup(group);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 查询参数集合 点击查看某参数详情时
     * @param gid
     * @param cid
     * @param searching
     * @return
     */
    @GetMapping("/params")
    public ResponseEntity<List<SpecParam>> querySpecParamList(
            @RequestParam(value="gid", required = false) Long gid,
            @RequestParam(value="cid", required = false) Long cid,
            @RequestParam(value="searching", required = false) Boolean searching
            ){
        return ResponseEntity.ok(specificationService.querySpecParamList(gid,cid,searching));
    }

    /**
     * 根据分类查询规格组及组内参数 - item.html商品详情页
     * @param cid
     * @return
     */
    @GetMapping("group")
    public ResponseEntity<List<SpecGroup>> queryListByCid(@RequestParam("cid") Long cid){
        return ResponseEntity.ok(specificationService.queryListByCid(cid));
    }

}