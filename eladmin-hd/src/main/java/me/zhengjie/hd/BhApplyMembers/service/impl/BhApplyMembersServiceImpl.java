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
package me.zhengjie.hd.BhApplyMembers.service.impl;

import me.zhengjie.hd.BhApplyMembers.domain.BhApplyMembers;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.hd.BhApplyMembers.repository.BhApplyMembersRepository;
import me.zhengjie.hd.BhApplyMembers.service.BhApplyMembersService;
import me.zhengjie.hd.BhApplyMembers.service.dto.BhApplyMembersDto;
import me.zhengjie.hd.BhApplyMembers.service.dto.BhApplyMembersQueryCriteria;
import me.zhengjie.hd.BhApplyMembers.service.mapstruct.BhApplyMembersMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.hutool.core.util.IdUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
* @website https://el-admin.vip
* @description 服务实现
* @author fb
* @date 2020-08-11
**/
@Service
@RequiredArgsConstructor
public class BhApplyMembersServiceImpl implements BhApplyMembersService {

    private final BhApplyMembersRepository bhApplyMembersRepository;
    private final BhApplyMembersMapper bhApplyMembersMapper;

    @Override
    public Map<String,Object> queryAll(BhApplyMembersQueryCriteria criteria, Pageable pageable){
        Page<BhApplyMembers> page = bhApplyMembersRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(bhApplyMembersMapper::toDto));
    }

    @Override
    public List<BhApplyMembersDto> queryAll(BhApplyMembersQueryCriteria criteria){
        return bhApplyMembersMapper.toDto(bhApplyMembersRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public BhApplyMembersDto findById(String id) {
        BhApplyMembers bhApplyMembers = bhApplyMembersRepository.findById(id).orElseGet(BhApplyMembers::new);
        ValidationUtil.isNull(bhApplyMembers.getId(),"BhApplyMembers","id",id);
        return bhApplyMembersMapper.toDto(bhApplyMembers);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BhApplyMembersDto create(BhApplyMembers resources) {
        resources.setId(IdUtil.simpleUUID()); 
        return bhApplyMembersMapper.toDto(bhApplyMembersRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(BhApplyMembers resources) {
        BhApplyMembers bhApplyMembers = bhApplyMembersRepository.findById(resources.getId()).orElseGet(BhApplyMembers::new);
        ValidationUtil.isNull( bhApplyMembers.getId(),"BhApplyMembers","id",resources.getId());
        bhApplyMembers.copy(resources);
        bhApplyMembersRepository.save(bhApplyMembers);
    }

    @Override
    public void deleteAll(String[] ids) {
        for (String id : ids) {
            bhApplyMembersRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<BhApplyMembersDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (BhApplyMembersDto bhApplyMembers : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("姓名", bhApplyMembers.getName());
            map.put("性别：0-未知,1-男性,2-女性,9-未说明性别", bhApplyMembers.getSex());
            map.put("证件类型：01-身份证,02-护照,03-居住证,04-军官证,05-出生证,06-绿卡,07-港澳通行证,08-士兵证,99-其他", bhApplyMembers.getIdCardCategory());
            map.put("证件号码", bhApplyMembers.getIdCardNo());
            map.put("与主申请人关系:01-本人,10-配偶,20-子,30-女,40-子孙外孙,50-父母,60-祖父母外祖父母,70兄弟姐妹,99-其他", bhApplyMembers.getRelationship());
            map.put("婚姻状况:10-未婚,20-已婚,21-初婚,22-再婚,23-复婚,30-丧偶,40-离婚,90-未知", bhApplyMembers.getMaritalStatus());
            map.put("申请编号", bhApplyMembers.getApplyId());
            map.put("批次主键", bhApplyMembers.getBatchId());
            map.put("行政区划", bhApplyMembers.getAreaCode());
            map.put("家庭居住地", bhApplyMembers.getAddress());
            map.put("户籍所在地", bhApplyMembers.getDomicileAddress());
            map.put("删除标志:0-未删除,1-已删除", bhApplyMembers.getDeleteFlag());
            map.put("创建时间", bhApplyMembers.getCreateDate());
            map.put("创建人", bhApplyMembers.getCreateBy());
            map.put("修改时间", bhApplyMembers.getUpdateDate());
            map.put("修改人", bhApplyMembers.getUpdateBy());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}