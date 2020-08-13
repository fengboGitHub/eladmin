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
package me.zhengjie.hd.SystemRegion.repository;

import me.zhengjie.base.BaseEntity;
import me.zhengjie.hd.SystemRegion.domain.SystemRegion;
import org.hibernate.annotations.SQLUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
* @website https://el-admin.vip
* @author fb
* @date 2020-08-04
**/
public interface SystemRegionRepository extends JpaRepository<SystemRegion, String>, JpaSpecificationExecutor<SystemRegion> {

    /**
     * 获取顶级行政区划
     * @return /
     */
    List<SystemRegion> findByParentIdIsNull();

    /**
     * 根据 PID 查询
     * @param parentId
     * @return /
     */
    List<SystemRegion> findByParentId(String parentId);

    /**
     * count 行政区划编码
     * @param regionCode
     * @return
     */
    @Query(value = "select count(1) from system_region where REGION_CODE = ?1", nativeQuery = true)
    int countByRegionCode(String regionCode);

    /**
     *
     * @param id
     * @param isLeaf
     * @return
     */
    @Modifying
    @Query(value = "update system_region set IS_LEAF =?2 where id=?1", nativeQuery = true)
    int updateLeafById (String id,String isLeaf);
}