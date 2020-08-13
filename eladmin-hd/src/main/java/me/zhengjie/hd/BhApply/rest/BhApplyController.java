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
package me.zhengjie.hd.BhApply.rest;


import me.zhengjie.annotation.Log;
import me.zhengjie.hd.BhApply.domain.BhApply;
import me.zhengjie.hd.BhApply.service.BhApplyService;
import me.zhengjie.hd.BhApply.service.dto.BhApplyQueryCriteria;
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
* @date 2020-07-28
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "主申请人管理")
@RequestMapping("/api/bhApply")
public class BhApplyController {

    private final BhApplyService bhApplyService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('bhApply:list')")
    public void download(HttpServletResponse response, BhApplyQueryCriteria criteria) throws IOException {
        bhApplyService.download(bhApplyService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询主申请人")
    @ApiOperation("查询主申请人")
    @PreAuthorize("@el.check('bhApply:list')")
    public ResponseEntity<Object> query(BhApplyQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(bhApplyService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增主申请人")
    @ApiOperation("新增主申请人")
    @PreAuthorize("@el.check('bhApply:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody BhApply resources){
        return new ResponseEntity<>(bhApplyService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改主申请人")
    @ApiOperation("修改主申请人")
    @PreAuthorize("@el.check('bhApply:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody BhApply resources){
        bhApplyService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除主申请人")
    @ApiOperation("删除主申请人")
    @PreAuthorize("@el.check('bhApply:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody String[] ids) {
        bhApplyService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}