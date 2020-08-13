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
package me.zhengjie.hd.BhApply.service.impl;

import cn.hutool.core.util.IdUtil;
import me.zhengjie.hd.BhApply.domain.BhApply;

import lombok.RequiredArgsConstructor;
import me.zhengjie.hd.BhApply.repository.BhApplyRepository;
import me.zhengjie.hd.BhApply.service.BhApplyService;
import me.zhengjie.hd.BhApply.service.dto.BhApplyDto;
import me.zhengjie.hd.BhApply.service.dto.BhApplyQueryCriteria;
import me.zhengjie.hd.BhApply.service.mapstruct.BhApplyMapper;
import me.zhengjie.utils.FileUtil;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import me.zhengjie.utils.ValidationUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
* @date 2020-07-28
**/
@Service
@RequiredArgsConstructor
public class BhApplyServiceImpl implements BhApplyService {

    private final BhApplyRepository bhApplyRepository;
    private final BhApplyMapper bhApplyMapper;

    @Override
    public Map<String,Object> queryAll(BhApplyQueryCriteria criteria, Pageable pageable){
        Page<BhApply> page = bhApplyRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(bhApplyMapper::toDto));
    }

    @Override
    public List<BhApplyDto> queryAll(BhApplyQueryCriteria criteria){
        return bhApplyMapper.toDto(bhApplyRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public BhApplyDto findById(String id) {
        BhApply bhApply = bhApplyRepository.findById(id).orElseGet(BhApply::new);
        ValidationUtil.isNull(bhApply.getId(),"BhApply","id",id);
        return bhApplyMapper.toDto(bhApply);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BhApplyDto create(BhApply resources) {
        resources.setId(IdUtil.simpleUUID());
        return bhApplyMapper.toDto(bhApplyRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(BhApply resources) {
        BhApply bhApply = bhApplyRepository.findById(resources.getId()).orElseGet(BhApply::new);
        ValidationUtil.isNull( bhApply.getId(),"BhApply","id",resources.getId());
        bhApply.copy(resources);
        bhApplyRepository.save(bhApply);
    }

    @Override
    public void deleteAll(String[] ids) {
        for (String id : ids) {
            bhApplyRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<BhApplyDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (BhApplyDto bhApply : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("主申请人姓名", bhApply.getRequestor());
            map.put("主申请人证件类型", bhApply.getIdCardCategory());
            map.put("主申请人证件号码", bhApply.getIdCardNo());
            map.put("委托时间", bhApply.getAuthorizeTime());
            map.put("核对机构", bhApply.getOrganization());
            map.put("业务类型", bhApply.getBizCategory());
            map.put("批次主键", bhApply.getBatchId());
            map.put("行政区划", bhApply.getAreaCode());
            map.put("委托系统家庭ID", bhApply.getDeputeFamilyId());
            map.put("委托系统申请审批ID", bhApply.getDeputeApplicationId());
            map.put("业务办理类型:01-新申请,02-复查", bhApply.getBizType());
            map.put("业务审批结果:01-同意,02-不同意", bhApply.getApproveResult());
            map.put("业务审批描述", bhApply.getApproveMessage());
            map.put("状态", bhApply.getStatus());
            map.put("删除标志:0-未删除,1-已删除", bhApply.getDeleteFlag());
            map.put("创建时间", bhApply.getCreateDate());
            map.put("创建人", bhApply.getCreateBy());
            map.put("修改时间", bhApply.getUpdateDate());
            map.put("修改人", bhApply.getUpdateBy());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}