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
package me.zhengjie.hd.SystemRegion.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author fb
* @date 2020-08-04
**/
@Entity
@Data
@Table(name="system_region")
public class SystemRegion implements Serializable {

    @Id
    @Column(name = "ID")
    @ApiModelProperty(value = "id")
    private String id;

    @Column(name = "CREATE_BY")
    @ApiModelProperty(value = "创建人登录名称")
    private String createBy;

    @Column(name = "UPDATE_BY")
    @ApiModelProperty(value = "更新人登录名称")
    private String updateBy;

    @Column(name = "REGION_CODE")
    @ApiModelProperty(value = "区划编码")
    private String regionCode;

    @Column(name = "REGION_NAME")
    @ApiModelProperty(value = "区划名称")
    private String regionName;

    @Column(name = "REGION_SHORT_NAME")
    @ApiModelProperty(value = "区划缩写")
    private String regionShortName;

    @Column(name = "REGION_LEVEL")
    @ApiModelProperty(value = "区划级别")
    private String regionLevel;

    @Column(name = "PARENT_ID")
    @ApiModelProperty(value = "父级id")
    private String parentId;

    @Column(name = "IS_LEAF")
    @ApiModelProperty(value = "isLeaf")
    private String isLeaf;

    @Column(name = "CREATE_TIME")
    @CreationTimestamp
    @ApiModelProperty(value = "创建时间")
    private Timestamp createTime;

    @Column(name = "UPDATE_TIME")
    @UpdateTimestamp
    @ApiModelProperty(value = "更新时间")
    private Timestamp updateTime;

   /* @Column(name = "sub_count")
    @ApiModelProperty(value = "子节点数目", hidden = true)
    private Integer subCount = 0;*/

    public void copy(SystemRegion source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}