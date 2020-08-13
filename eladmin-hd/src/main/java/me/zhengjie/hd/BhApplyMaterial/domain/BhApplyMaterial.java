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
package me.zhengjie.hd.BhApplyMaterial.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author fb
* @date 2020-08-11
**/
@Entity
@Data
@Table(name="bh_apply_material")
public class BhApplyMaterial implements Serializable {

    @Id
    @Column(name = "ID")
    @ApiModelProperty(value = "主键")
    private String id;

    @Column(name = "NAME")
    @ApiModelProperty(value = "名称")
    private String name;

    @Column(name = "CATEGORY")
    @ApiModelProperty(value = "种类")
    private String category;

    @Column(name = "APPLY_ID")
    @ApiModelProperty(value = "申请编号")
    private String applyId;

    @Column(name = "BATCH_ID",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "批次号")
    private String batchId;

    @Column(name = "FILE_NAME",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "文件名称")
    private String fileName;

    @Column(name = "FILE_PATH",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "文件路径")
    private String filePath;

    @Column(name = "DELETE_FLAG")
    @ApiModelProperty(value = "删除标志:0-未删除,1-已删除")
    private String deleteFlag;

    @Column(name = "CREATE_DATE")
    @ApiModelProperty(value = "创建时间")
    private Timestamp createDate;

    @Column(name = "CREATE_BY")
    @ApiModelProperty(value = "创建人")
    private String createBy;

    @Column(name = "UPDATE_DATE")
    @ApiModelProperty(value = "修改时间")
    private Timestamp updateDate;

    @Column(name = "UPDATE_BY")
    @ApiModelProperty(value = "修改人")
    private String updateBy;

    @Column(name = "ID_CARD_NO")
    @ApiModelProperty(value = "证件号码")
    private String idCardNo;

    public void copy(BhApplyMaterial source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}