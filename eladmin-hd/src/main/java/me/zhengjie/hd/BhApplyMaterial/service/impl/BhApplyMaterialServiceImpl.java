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
package me.zhengjie.hd.BhApplyMaterial.service.impl;

import me.zhengjie.hd.BhApplyMaterial.domain.BhApplyMaterial;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.hd.BhApplyMaterial.repository.BhApplyMaterialRepository;
import me.zhengjie.hd.BhApplyMaterial.service.BhApplyMaterialService;
import me.zhengjie.hd.BhApplyMaterial.service.dto.BhApplyMaterialDto;
import me.zhengjie.hd.BhApplyMaterial.service.dto.BhApplyMaterialQueryCriteria;
import me.zhengjie.hd.BhApplyMaterial.service.mapstruct.BhApplyMaterialMapper;
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
public class BhApplyMaterialServiceImpl implements BhApplyMaterialService {

    private final BhApplyMaterialRepository bhApplyMaterialRepository;
    private final BhApplyMaterialMapper bhApplyMaterialMapper;

    @Override
    public Map<String,Object> queryAll(BhApplyMaterialQueryCriteria criteria, Pageable pageable){
        Page<BhApplyMaterial> page = bhApplyMaterialRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(bhApplyMaterialMapper::toDto));
    }

    @Override
    public List<BhApplyMaterialDto> queryAll(BhApplyMaterialQueryCriteria criteria){
        return bhApplyMaterialMapper.toDto(bhApplyMaterialRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public BhApplyMaterialDto findById(String id) {
        BhApplyMaterial bhApplyMaterial = bhApplyMaterialRepository.findById(id).orElseGet(BhApplyMaterial::new);
        ValidationUtil.isNull(bhApplyMaterial.getId(),"BhApplyMaterial","id",id);
        return bhApplyMaterialMapper.toDto(bhApplyMaterial);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BhApplyMaterialDto create(BhApplyMaterial resources) {
        resources.setId(IdUtil.simpleUUID()); 
        return bhApplyMaterialMapper.toDto(bhApplyMaterialRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(BhApplyMaterial resources) {
        BhApplyMaterial bhApplyMaterial = bhApplyMaterialRepository.findById(resources.getId()).orElseGet(BhApplyMaterial::new);
        ValidationUtil.isNull( bhApplyMaterial.getId(),"BhApplyMaterial","id",resources.getId());
        bhApplyMaterial.copy(resources);
        bhApplyMaterialRepository.save(bhApplyMaterial);
    }

    @Override
    public void deleteAll(String[] ids) {
        for (String id : ids) {
            bhApplyMaterialRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<BhApplyMaterialDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (BhApplyMaterialDto bhApplyMaterial : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("名称", bhApplyMaterial.getName());
            map.put("种类", bhApplyMaterial.getCategory());
            map.put("申请编号", bhApplyMaterial.getApplyId());
            map.put("批次号", bhApplyMaterial.getBatchId());
            map.put("文件名称", bhApplyMaterial.getFileName());
            map.put("文件路径", bhApplyMaterial.getFilePath());
            map.put("删除标志:0-未删除,1-已删除", bhApplyMaterial.getDeleteFlag());
            map.put("创建时间", bhApplyMaterial.getCreateDate());
            map.put("创建人", bhApplyMaterial.getCreateBy());
            map.put("修改时间", bhApplyMaterial.getUpdateDate());
            map.put("修改人", bhApplyMaterial.getUpdateBy());
            map.put("证件号码", bhApplyMaterial.getIdCardNo());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}