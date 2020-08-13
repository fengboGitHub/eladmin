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
package me.zhengjie.hd.BhBatch.service.impl;

import me.zhengjie.hd.BhBatch.domain.BhBatch;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.hd.BhBatch.repository.BhBatchRepository;
import me.zhengjie.hd.BhBatch.service.BhBatchService;
import me.zhengjie.hd.BhBatch.service.dto.BhBatchDto;
import me.zhengjie.hd.BhBatch.service.dto.BhBatchQueryCriteria;
import me.zhengjie.hd.BhBatch.service.mapstruct.BhBatchMapper;
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
* @date 2020-07-28
**/
@Service
@RequiredArgsConstructor
public class BhBatchServiceImpl implements BhBatchService {

    private final BhBatchRepository bhBatchRepository;
    private final BhBatchMapper bhBatchMapper;

    @Override
    public Map<String,Object> queryAll(BhBatchQueryCriteria criteria, Pageable pageable){
        Page<BhBatch> page = bhBatchRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(bhBatchMapper::toDto));
    }

    @Override
    public List<BhBatchDto> queryAll(BhBatchQueryCriteria criteria){
        return bhBatchMapper.toDto(bhBatchRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public BhBatchDto findById(String id) {
        BhBatch bhBatch = bhBatchRepository.findById(id).orElseGet(BhBatch::new);
        ValidationUtil.isNull(bhBatch.getId(),"BhBatch","id",id);
        return bhBatchMapper.toDto(bhBatch);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BhBatchDto create(BhBatch resources) {
        resources.setId(IdUtil.simpleUUID()); 
        return bhBatchMapper.toDto(bhBatchRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(BhBatch resources) {
        BhBatch bhBatch = bhBatchRepository.findById(resources.getId()).orElseGet(BhBatch::new);
        ValidationUtil.isNull( bhBatch.getId(),"BhBatch","id",resources.getId());
        bhBatch.copy(resources);
        bhBatchRepository.save(bhBatch);
    }

    @Override
    public void deleteAll(String[] ids) {
        for (String id : ids) {
            bhBatchRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<BhBatchDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (BhBatchDto bhBatch : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("名称", bhBatch.getName());
            map.put("委托时间", bhBatch.getEntrustTime());
            map.put("核对机构主键", bhBatch.getOrganization());
            map.put("业务类型", bhBatch.getBizCategory());
            map.put("方案主键", bhBatch.getSolutionId());
            map.put("报告模板主键", bhBatch.getReportTemplateId());
            map.put("状态:1部分退回,2全部退回,3中途终止,4正常终止", bhBatch.getStatus());
            map.put("数据来源:01-数据采集,02-手工批量导入,03-自动批量导入,04-社会救助系统", bhBatch.getDataResource());
            map.put("行政区划", bhBatch.getAreaCode());
            map.put("是否反馈核对报告", bhBatch.getIsReturn());
            map.put("流程key", bhBatch.getProcessKey());
            map.put("流程状态:1-待提交,2-处理中,3-已完成", bhBatch.getBpmStatus());
            map.put("流程类型:0-正常,1-复核", bhBatch.getProcessType());
            map.put("删除标志:0-未删除,1-已删除", bhBatch.getDeleteFlag());
            map.put("创建时间", bhBatch.getCreateDate());
            map.put("创建人", bhBatch.getCreateBy());
            map.put("修改时间", bhBatch.getUpdateDate());
            map.put("修改人", bhBatch.getUpdateBy());
            map.put("处理状态：1.待处理，2.处理中，3已处理", bhBatch.getSynStatus());
            map.put(" xcFlag",  bhBatch.getXcFlag());
            map.put(" dataSourceAreaCode",  bhBatch.getDataSourceAreaCode());
            map.put(" exchangeStatus",  bhBatch.getExchangeStatus());
            map.put(" isSpecialHd",  bhBatch.getIsSpecialHd());
            map.put(" xcStatus",  bhBatch.getXcStatus());
            map.put("纵向协查委托书路径", bhBatch.getEntrustPath());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}