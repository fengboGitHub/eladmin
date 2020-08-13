/*
*  Copyright 2019-2020 Zheng Jie
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*  http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*/
package me.zhengjie.hd.SystemRegion.service;

import me.zhengjie.hd.SystemRegion.domain.SystemRegion;
import me.zhengjie.hd.SystemRegion.service.dto.SystemRegionDto;
import me.zhengjie.hd.SystemRegion.service.dto.SystemRegionQueryCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;

import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @description 服务接口
* @author fb
* @date 2020-08-04
**/
public interface SystemRegionService {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(SystemRegionQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     * @param criteria 条件
     * @param isQuery
     * @return Map<String,Object>
     */
    List<SystemRegionDto> queryAll(SystemRegionQueryCriteria criteria, Boolean isQuery) throws Exception;

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<SystemRegionDto>
    */
    List<SystemRegionDto> queryAll(SystemRegionQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id ID
     * @return SystemRegionDto
     */
    SystemRegionDto findById(String id);


    /**
    * 创建
    * @param resources /
    * @return SystemRegionDto
    */
    SystemRegionDto create(SystemRegion resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(SystemRegion resources);

    /**
    * 多选删除
    * @param ids /
    */
    void deleteAll(String[] ids);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<SystemRegionDto> all, HttpServletResponse response) throws IOException;

    /**
     * 构建树形数据
     * @param systemRegions /
     * @return /
     */
    Object buildTree(List<SystemRegionDto> systemRegions);

    /**
     * 获取父级行政区划
     * @param systemRegionDto
     * @param systemRegions
     * @return
     */
    public List<SystemRegionDto> getSuperior(SystemRegionDto systemRegionDto, List<SystemRegion> systemRegions);

    /**
     * 获取
     * @param id
     * @param systemRegionList
     * @return
     */
    List<String> getSystemRegionChildren(String id, List<SystemRegion> systemRegionList);

    /**
     * 计算数量
     * @param regionCode 条件参数
     * @return int
     */
    int countByRegionCode (String regionCode);

    /**
     * 更新节点属性
     * @param  id 条件参数
     * @return int
     */
    int updateLeafById (String id,String isLeaf);

}