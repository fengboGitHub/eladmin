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
package me.zhengjie.hd.BhBatch.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.hd.BhBatch.domain.BhBatch;
import me.zhengjie.hd.BhBatch.service.BhBatchService;
import me.zhengjie.hd.BhBatch.service.dto.BhBatchQueryCriteria;
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
@Api(tags = "批次接口管理")
@RequestMapping("/api/bhBatch")
public class BhBatchController {

    private final BhBatchService bhBatchService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('bhBatch:list')")
    public void download(HttpServletResponse response, BhBatchQueryCriteria criteria) throws IOException {
        bhBatchService.download(bhBatchService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询批次接口")
    @ApiOperation("查询批次接口")
    @PreAuthorize("@el.check('bhBatch:list')")
    public ResponseEntity<Object> query(BhBatchQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(bhBatchService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增批次接口")
    @ApiOperation("新增批次接口")
    @PreAuthorize("@el.check('bhBatch:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody BhBatch resources){
        return new ResponseEntity<>(bhBatchService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改批次接口")
    @ApiOperation("修改批次接口")
    @PreAuthorize("@el.check('bhBatch:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody BhBatch resources){
        bhBatchService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除批次接口")
    @ApiOperation("删除批次接口")
    @PreAuthorize("@el.check('bhBatch:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody String[] ids) {
        bhBatchService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}