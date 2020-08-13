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
package me.zhengjie.hd.SystemRegion.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;
import java.util.List;

/**
* @website https://el-admin.vip
* @description /
* @author fb
* @date 2020-08-04
**/
@Data
public class SystemRegionDto implements Serializable {

    private String id;


    /** 创建人登录名称 */
    private String createBy;

    /** 更新人登录名称 */
    private String updateBy;

    /** 区划编码 */
    private String regionCode;

    /** 区划名称 */
    private String regionName;

    /** 区划缩写 */
    private String regionShortName;

    /** 区划级别 */
    private String regionLevel;

    /** 父级id */
    private String parentId;

    private String isLeaf;

    /** 创建时间 */
    private Timestamp createTime;

    /** 更新时间 */
    private Timestamp updateTime;

    //private Integer subCount;
    public Boolean getHasChildren() {
        return "0".equals(isLeaf);
    }

    public Boolean getLeaf() {
        return !"0".equals(isLeaf);
    }

    public String getLabel() {
        return regionName;
    }
    /**
     * 获取下级行政区划
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<SystemRegionDto> children;
}