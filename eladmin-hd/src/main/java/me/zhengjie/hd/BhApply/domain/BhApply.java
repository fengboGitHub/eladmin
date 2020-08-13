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
package me.zhengjie.hd.BhApply.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author fb
* @date 2020-07-28
**/
@Entity
@Data
@Table(name="bh_apply")
public class BhApply implements Serializable {

    @Id
    @Column(name = "ID")
    @ApiModelProperty(value = "主键")
    private String id;

    @Column(name = "REQUESTOR",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "主申请人姓名")
    private String requestor;

    @Column(name = "ID_CARD_CATEGORY",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "主申请人证件类型")
    private String idCardCategory;

    @Column(name = "ID_CARD_NO",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "主申请人证件号码")
    private String idCardNo;

    @Column(name = "AUTHORIZE_TIME",nullable = false)
    @NotNull
    @ApiModelProperty(value = "委托时间")
    private Timestamp authorizeTime;

    @Column(name = "ORGANIZATION",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "核对机构")
    private String organization;

    @Column(name = "BIZ_CATEGORY",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "业务类型")
    private String bizCategory;

    @Column(name = "BATCH_ID",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "批次主键")
    private String batchId;

    @Column(name = "AREA_CODE")
    @ApiModelProperty(value = "行政区划")
    private String areaCode;

    @Column(name = "DEPUTE_FAMILY_ID")
    @ApiModelProperty(value = "委托系统家庭ID")
    private String deputeFamilyId;

    @Column(name = "DEPUTE_APPLICATION_ID")
    @ApiModelProperty(value = "委托系统申请审批ID")
    private String deputeApplicationId;

    @Column(name = "BIZ_TYPE")
    @ApiModelProperty(value = "业务办理类型:01-新申请,02-复查")
    private String bizType;

    @Column(name = "APPROVE_RESULT")
    @ApiModelProperty(value = "业务审批结果:01-同意,02-不同意")
    private String approveResult;

    @Column(name = "APPROVE_MESSAGE")
    @ApiModelProperty(value = "业务审批描述")
    private String approveMessage;

    @Column(name = "STATUS")
    @ApiModelProperty(value = "状态")
    private String status;

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

    public void copy(BhApply source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}