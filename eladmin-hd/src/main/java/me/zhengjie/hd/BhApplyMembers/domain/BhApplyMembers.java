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
package me.zhengjie.hd.BhApplyMembers.domain;

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
@Table(name="bh_apply_members")
public class BhApplyMembers implements Serializable {

    @Id
    @Column(name = "ID")
    @ApiModelProperty(value = "主键")
    private String id;

    @Column(name = "NAME",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "姓名")
    private String name;

    @Column(name = "SEX",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "性别：0-未知,1-男性,2-女性,9-未说明性别")
    private String sex;

    @Column(name = "ID_CARD_CATEGORY",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "证件类型：01-身份证,02-护照,03-居住证,04-军官证,05-出生证,06-绿卡,07-港澳通行证,08-士兵证,99-其他")
    private String idCardCategory;

    @Column(name = "ID_CARD_NO",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "证件号码")
    private String idCardNo;

    @Column(name = "RELATIONSHIP",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "与主申请人关系:01-本人,10-配偶,20-子,30-女,40-子孙外孙,50-父母,60-祖父母外祖父母,70兄弟姐妹,99-其他")
    private String relationship;

    @Column(name = "MARITAL_STATUS")
    @ApiModelProperty(value = "婚姻状况:10-未婚,20-已婚,21-初婚,22-再婚,23-复婚,30-丧偶,40-离婚,90-未知")
    private String maritalStatus;

    @Column(name = "APPLY_ID",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "申请编号")
    private String applyId;

    @Column(name = "BATCH_ID",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "批次主键")
    private String batchId;

    @Column(name = "AREA_CODE")
    @ApiModelProperty(value = "行政区划")
    private String areaCode;

    @Column(name = "ADDRESS")
    @ApiModelProperty(value = "家庭居住地")
    private String address;

    @Column(name = "DOMICILE_ADDRESS")
    @ApiModelProperty(value = "户籍所在地")
    private String domicileAddress;

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

    public void copy(BhApplyMembers source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}