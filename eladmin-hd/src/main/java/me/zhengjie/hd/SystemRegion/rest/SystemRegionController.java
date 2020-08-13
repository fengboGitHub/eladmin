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
package me.zhengjie.hd.SystemRegion.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.hd.SystemRegion.domain.SystemRegion;
import me.zhengjie.hd.SystemRegion.service.SystemRegionService;
import me.zhengjie.hd.SystemRegion.service.dto.SystemRegionDto;
import me.zhengjie.hd.SystemRegion.service.dto.SystemRegionQueryCriteria;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.SecurityUtils;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import java.util.*;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @author fb
* @date 2020-08-04
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "行政区划接口管理")
@RequestMapping("/api/systemRegion")
public class SystemRegionController {

    private final SystemRegionService systemRegionService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('systemRegion:list')")
    public void download(HttpServletResponse response, SystemRegionQueryCriteria criteria) throws IOException {
        systemRegionService.download(systemRegionService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询行政区划接口")
    @ApiOperation("查询行政区划接口")
    @PreAuthorize("@el.check('systemRegion:list')")
    public ResponseEntity<Object> query(SystemRegionQueryCriteria criteria, Pageable pageable) throws Exception {
       /* Map<String,Object> mpas= systemRegionService.queryAll(criteria,pageable);
        return new ResponseEntity<>(mpas,HttpStatus.OK);*/
        List<SystemRegionDto> systemRegionDtos = systemRegionService.queryAll(criteria,true);
        return new ResponseEntity<>(PageUtil.toPage(systemRegionDtos, systemRegionDtos.size()),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增行政区划接口")
    @ApiOperation("新增行政区划接口")
    @PreAuthorize("@el.check('systemRegion:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody SystemRegion resources){
        // 校验行政区划编码是否唯一
        long count = systemRegionService.countByRegionCode(resources.getRegionCode());
        if( count > 0){
            throw new BadRequestException("行政区划编码重复");
        }
        // 开始保存
        String userName = SecurityUtils.getCurrentUsername();
        resources.setCreateBy(userName);
        ResponseEntity<Object>  obj  = new ResponseEntity<>(systemRegionService.create(resources),HttpStatus.CREATED);
        // 修改父级行政区划的isleaf值为0，标识有节点
        systemRegionService.updateLeafById(resources.getParentId(),"0");
        return obj;
    }

    @PutMapping
    @Log("修改行政区划接口")
    @ApiOperation("修改行政区划接口")
    @PreAuthorize("@el.check('systemRegion:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody SystemRegion resources){
        systemRegionService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除行政区划接口")
    @ApiOperation("删除行政区划接口")
    @PreAuthorize("@el.check('systemRegion:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody String[] ids) {
        systemRegionService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Log("查询行政区划")
    @ApiOperation("查询行政区划:根据ID获取同级与上级数据")
    @PostMapping("/superior")
    @PreAuthorize("@el.check('admin','systemRegion:list')")
    public ResponseEntity<Object> getSuperior(@RequestBody List<String> ids) {
        Set<SystemRegionDto> systemRegionDtos  = new LinkedHashSet<>();
        for (String id : ids) {
            SystemRegionDto systemRegionDto = systemRegionService.findById(id);
            List<SystemRegionDto> systemRegions = systemRegionService.getSuperior(systemRegionDto, new ArrayList<>());
            systemRegionDtos.addAll(systemRegions);
        }
        return new ResponseEntity<>(systemRegionService.buildTree(new ArrayList<>(systemRegionDtos)),HttpStatus.OK);
    }
}