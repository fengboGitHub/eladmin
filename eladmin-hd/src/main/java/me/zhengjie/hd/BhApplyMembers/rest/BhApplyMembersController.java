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
package me.zhengjie.hd.BhApplyMembers.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.hd.BhApplyMembers.domain.BhApplyMembers;
import me.zhengjie.hd.BhApplyMembers.service.BhApplyMembersService;
import me.zhengjie.hd.BhApplyMembers.service.dto.BhApplyMembersQueryCriteria;
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
@Api(tags = "家庭成员接口管理")
@RequestMapping("/api/bhApplyMembers")
public class BhApplyMembersController {

    private final BhApplyMembersService bhApplyMembersService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('bhApplyMembers:list')")
    public void download(HttpServletResponse response, BhApplyMembersQueryCriteria criteria) throws IOException {
        bhApplyMembersService.download(bhApplyMembersService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询家庭成员接口")
    @ApiOperation("查询家庭成员接口")
    @PreAuthorize("@el.check('bhApplyMembers:list')")
    public ResponseEntity<Object> query(BhApplyMembersQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(bhApplyMembersService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增家庭成员接口")
    @ApiOperation("新增家庭成员接口")
    @PreAuthorize("@el.check('bhApplyMembers:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody BhApplyMembers resources){
        return new ResponseEntity<>(bhApplyMembersService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改家庭成员接口")
    @ApiOperation("修改家庭成员接口")
    @PreAuthorize("@el.check('bhApplyMembers:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody BhApplyMembers resources){
        bhApplyMembersService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除家庭成员接口")
    @ApiOperation("删除家庭成员接口")
    @PreAuthorize("@el.check('bhApplyMembers:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody String[] ids) {
        bhApplyMembersService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}