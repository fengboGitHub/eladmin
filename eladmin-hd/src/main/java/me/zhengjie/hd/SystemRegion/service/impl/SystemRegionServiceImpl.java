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
package me.zhengjie.hd.SystemRegion.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import me.zhengjie.hd.SystemRegion.domain.SystemRegion;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.hd.SystemRegion.repository.SystemRegionRepository;
import me.zhengjie.hd.SystemRegion.service.SystemRegionService;
import me.zhengjie.hd.SystemRegion.service.dto.SystemRegionDto;
import me.zhengjie.hd.SystemRegion.service.dto.SystemRegionQueryCriteria;
import me.zhengjie.hd.SystemRegion.service.mapstruct.SystemRegionMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.hutool.core.util.IdUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;

import java.lang.reflect.Field;
import java.util.*;
import java.io.IOException;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @description 服务实现
* @author fb
* @date 2020-08-04
**/
@Service
@RequiredArgsConstructor
public class SystemRegionServiceImpl implements SystemRegionService {

    private final SystemRegionRepository systemRegionRepository;
    private final SystemRegionMapper systemRegionMapper;

    @Override
    public Map<String,Object> queryAll(SystemRegionQueryCriteria criteria, Pageable pageable){
        Page<SystemRegion> page = systemRegionRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(systemRegionMapper::toDto));
    }

    @Override
    public List<SystemRegionDto> queryAll(SystemRegionQueryCriteria criteria, Boolean isQuery) throws Exception {
        Sort sort = new Sort(Sort.Direction.ASC, "regionCode");
        if (isQuery) {
            criteria.setParentIdIsNull(true);
            List<Field> fields = QueryHelp.getAllFields(criteria.getClass(), new ArrayList<>());
            List<String> fieldNames = new ArrayList<String>(){{ add("parentIdIsNull");}};
            for (Field field : fields) {
                //设置对象的访问权限，保证对private的属性的访问
                field.setAccessible(true);
                Object val = field.get(criteria);
                if(fieldNames.contains(field.getName())){
                    continue;
                }
                if (ObjectUtil.isNotNull(val)) {
                    criteria.setParentIdIsNull(null);
                    break;
                }
            }
        }
        return systemRegionMapper.toDto(systemRegionRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),sort));
    }

    @Override
    public List<SystemRegionDto> queryAll(SystemRegionQueryCriteria criteria){
        return systemRegionMapper.toDto(systemRegionRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public SystemRegionDto findById(String id) {
        SystemRegion systemRegion = systemRegionRepository.findById(id).orElseGet(SystemRegion::new);
        ValidationUtil.isNull(systemRegion.getId(),"SystemRegion","id",id);
        return systemRegionMapper.toDto(systemRegion);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public SystemRegionDto create(SystemRegion resources) {
        resources.setId(IdUtil.simpleUUID()); 
        return systemRegionMapper.toDto(systemRegionRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SystemRegion resources) {
        SystemRegion systemRegion = systemRegionRepository.findById(resources.getId()).orElseGet(SystemRegion::new);
        ValidationUtil.isNull( systemRegion.getId(),"SystemRegion","id",resources.getId());
        systemRegion.copy(resources);
        systemRegionRepository.save(systemRegion);
    }

    @Override
    public void deleteAll(String[] ids) {
        for (String id : ids) {
            systemRegionRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<SystemRegionDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (SystemRegionDto systemRegion : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("创建人登录名称", systemRegion.getCreateBy());
            map.put("更新人登录名称", systemRegion.getUpdateBy());
            map.put("区划编码", systemRegion.getRegionCode());
            map.put("区划名称", systemRegion.getRegionName());
            map.put("区划缩写", systemRegion.getRegionShortName());
            map.put("区划级别", systemRegion.getRegionLevel());
            map.put("父级id", systemRegion.getParentId());
            map.put("isLeaf",  systemRegion.getIsLeaf());
            map.put("创建时间", systemRegion.getCreateTime());
            map.put("更新时间", systemRegion.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }


    @Override
    public List<SystemRegionDto> getSuperior(SystemRegionDto systemRegionDto, List<SystemRegion> systemRegions) {
        if(systemRegionDto.getParentId() == null){
            systemRegions.addAll(systemRegionRepository.findByParentIdIsNull());
            return systemRegionMapper.toDto(systemRegions);
        }
        systemRegions.addAll(systemRegionRepository.findByParentId(systemRegionDto.getParentId()));
        return getSuperior(findById(systemRegionDto.getParentId()), systemRegions);
    }

    @Override
    public List<String> getSystemRegionChildren(String id, List<SystemRegion> systemRegionList) {
        List<String> list = new ArrayList<>();
        systemRegionList.forEach(systemRegion -> {
                    if (systemRegion!=null){
                        List<SystemRegion> systemRegions =  systemRegionRepository.findByParentId(systemRegion.getId());
                        if(systemRegionList.size() != 0){
                            list.addAll(getSystemRegionChildren(systemRegion.getId(), systemRegions));
                        }
                        list.add(systemRegion.getId());
                    }
                }
        );
        return list;
    }

    @Override
    public int countByRegionCode (String regionCode) {
        return systemRegionRepository.countByRegionCode(regionCode);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateLeafById (String id,String isLeaf) {
        return systemRegionRepository.updateLeafById(id,isLeaf);
    }

    @Override
    public Object buildTree(List<SystemRegionDto> systemRegionDtos) {
        Set<SystemRegionDto> trees = new LinkedHashSet<>();
        Set<SystemRegionDto> systemRegions= new LinkedHashSet<>();
        List<String> systemRegionNames = systemRegionDtos.stream().map(SystemRegionDto::getRegionName).collect(Collectors.toList());
        boolean isChild;
        for (SystemRegionDto systemRegionDTO : systemRegionDtos) {
            isChild = false;
            if (systemRegionDTO.getParentId() == null) {
                trees.add(systemRegionDTO);
            }
            for (SystemRegionDto it : systemRegionDtos) {
                if (it.getParentId() != null && systemRegionDTO.getId().equals(it.getParentId())) {
                    isChild = true;
                    if (systemRegionDTO.getChildren() == null) {
                        systemRegionDTO.setChildren(new ArrayList<>());
                    }
                    systemRegionDTO.getChildren().add(it);
                }
            }
            if(isChild) {
                systemRegions.add(systemRegionDTO);
            } else if(systemRegionDTO.getParentId() != null &&  !systemRegionNames.contains(findById(systemRegionDTO.getParentId()).getRegionName())) {
                systemRegions.add(systemRegionDTO);
            }
        }

        if (CollectionUtil.isEmpty(trees)) {
            trees = systemRegions;
        }
        Map<String,Object> map = new HashMap<>(2);
        map.put("totalElements",systemRegionDtos.size());
        map.put("content",CollectionUtil.isEmpty(trees)? systemRegionDtos :trees);
        return map;
    }
}