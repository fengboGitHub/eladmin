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
package me.zhengjie.hd.BhApplyMaterial.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.hd.BhApplyMaterial.domain.BhApplyMaterial;
import me.zhengjie.hd.BhApplyMaterial.service.BhApplyMaterialService;
import me.zhengjie.hd.BhApplyMaterial.service.dto.BhApplyMaterialQueryCriteria;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @author fb
* @date 2020-08-11
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "申请人材料接口管理")
@RequestMapping("/api/bhApplyMaterial")
public class BhApplyMaterialController {

    private final BhApplyMaterialService bhApplyMaterialService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('bhApplyMaterial:list')")
    public void download(HttpServletResponse response, BhApplyMaterialQueryCriteria criteria) throws IOException {
        bhApplyMaterialService.download(bhApplyMaterialService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询申请人材料接口")
    @ApiOperation("查询申请人材料接口")
    @PreAuthorize("@el.check('bhApplyMaterial:list')")
    public ResponseEntity<Object> query(BhApplyMaterialQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(bhApplyMaterialService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增申请人材料接口")
    @ApiOperation("新增申请人材料接口")
    @PreAuthorize("@el.check('bhApplyMaterial:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody BhApplyMaterial resources){
        return new ResponseEntity<>(bhApplyMaterialService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改申请人材料接口")
    @ApiOperation("修改申请人材料接口")
    @PreAuthorize("@el.check('bhApplyMaterial:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody BhApplyMaterial resources){
        bhApplyMaterialService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除申请人材料接口")
    @ApiOperation("删除申请人材料接口")
    @PreAuthorize("@el.check('bhApplyMaterial:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody String[] ids) {
        bhApplyMaterialService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}