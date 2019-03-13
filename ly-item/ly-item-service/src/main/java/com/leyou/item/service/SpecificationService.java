package com.leyou.item.service;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.item.mapper.SpecGroupMapper;
import com.leyou.item.mapper.SpecParamMapper;
import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SpecificationService {

    @Autowired
    private SpecGroupMapper specGroupMapper;
    @Autowired
    private SpecParamMapper specParamMapper;

    public List<SpecGroup> querySpecGroupByCid(Long cid) {
        //查询条件
        SpecGroup t = new SpecGroup();
        t.setCid(cid);
        //查询
        List<SpecGroup> list = specGroupMapper.select(t);
        if(CollectionUtils.isEmpty(list)){
            //没查到
            throw new LyException(ExceptionEnum.SPEC_GROUP_NOT_FOUND);
        }
        return list;
    }

    public List<SpecParam> querySpecParamList(Long gid,Long cid,Boolean searching){
        //查询条件
        SpecParam s = new SpecParam();
        s.setGroupId(gid);
        s.setCid(cid);
        s.setSearching(searching);
        //查询
        List<SpecParam> list = this.specParamMapper.select(s);//根据非空字段查询
        if(CollectionUtils.isEmpty(list)){
            //没查到
            throw new LyException(ExceptionEnum.SPEC_PARAM_NOT_FOUND);
        }
        return list;
    }

    public void saveGroup(SpecGroup specGroup) {
        specGroup.setId(null);
        int count = specGroupMapper.insert(specGroup);
        if(count != 1){
            throw new LyException(ExceptionEnum.CATEGORY_SAVE_ERROR);
        }
    }

    public void deleteGroup(Long id) {
        int count = specGroupMapper.deleteByPrimaryKey(id);
        if(count != 1){
            throw new LyException(ExceptionEnum.CATEGORY_DELETE_ERROR);
        }
    }

    public void updateGroup(SpecGroup group) {
        int count = specGroupMapper.updateByPrimaryKeySelective(group);
        if(count != 1){
            throw new LyException(ExceptionEnum.CATEGORY_UPDATE_ERROR);
        }
    }

    /**
     * 查询规格组及组内参数
     * @param cid
     * @return
     */
    public List<SpecGroup> queryListByCid(Long cid) {
        //查询规格组
        List<SpecGroup> specGroups = querySpecGroupByCid(cid);
        //查询当前分类下的所有参数
        List<SpecParam> specParams = querySpecParamList(null, cid, null);
        //填充params到group
        /*for (SpecGroup specGroup : specGroups) { 方法1 双重for 不好
            for (SpecParam specParam : specParams) {
                if(specGroup.getId() == specParam.getGroupId()){
                }}}*/
        //方法2
        //先把规格参数变成map map的key是规格组id map的v是组下的所有参数  方便 遍历一次
        Map<Long, List<SpecParam>> map = new HashMap<>();
        for (SpecParam param : specParams) { //遍历规格参数 把一个List处理成一个map map嵌套List
            if(!map.containsKey(param.getGroupId())){
                //这个组id在map中不存在，新增一个List
                map.put(param.getGroupId(), new ArrayList<>());
            }
            map.get(param.getGroupId()).add(param);//添加规格参数到map
        }
        //填充params到group
        for (SpecGroup specGroup : specGroups) {
            specGroup.setParams(map.get(specGroup.getId()));
        }
        return specGroups;
    }
}