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
package me.zhengjie.hd.BhBatch.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import org.hibernate.annotations.CreationTimestamp;

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
@Table(name="bh_batch")
public class BhBatch implements Serializable {

    @Id
    @Column(name = "ID")
    @ApiModelProperty(value = "主键")
    private String id;

    @Column(name = "NAME",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "名称")
    private String name;

    @Column(name = "ENTRUST_TIME",nullable = false)
    @NotNull
    @CreationTimestamp
    @ApiModelProperty(value = "委托时间")
    private Timestamp entrustTime;

    @Column(name = "ORGANIZATION",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "核对机构主键")
    private String organization;

    @Column(name = "BIZ_CATEGORY",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "业务类型")
    private String bizCategory;

    @Column(name = "SOLUTION_ID")
    @ApiModelProperty(value = "方案主键")
    private String solutionId;

    @Column(name = "REPORT_TEMPLATE_ID")
    @ApiModelProperty(value = "报告模板主键")
    private String reportTemplateId;

    @Column(name = "STATUS")
    @ApiModelProperty(value = "状态:1部分退回,2全部退回,3中途终止,4正常终止")
    private String status;

    @Column(name = "DATA_RESOURCE")
    @ApiModelProperty(value = "数据来源:01-数据采集,02-手工批量导入,03-自动批量导入,04-社会救助系统")
    private String dataResource;

    @Column(name = "AREA_CODE")
    @ApiModelProperty(value = "行政区划")
    private String areaCode;

    @Column(name = "IS_RETURN")
    @ApiModelProperty(value = "是否反馈核对报告")
    private String isReturn;

    @Column(name = "PROCESS_KEY")
    @ApiModelProperty(value = "流程key")
    private String processKey;

    @Column(name = "BPM_STATUS")
    @ApiModelProperty(value = "流程状态:1-待提交,2-处理中,3-已完成")
    private String bpmStatus;

    @Column(name = "PROCESS_TYPE")
    @ApiModelProperty(value = "流程类型:0-正常,1-复核")
    private String processType;

    @Column(name = "DELETE_FLAG")
    @ApiModelProperty(value = "删除标志:0-未删除,1-已删除")
    private String deleteFlag;

    @Column(name = "CREATE_DATE",nullable = false)
    @NotNull
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

    @Column(name = "SYN_STATUS")
    @ApiModelProperty(value = "处理状态：1.待处理，2.处理中，3已处理")
    private String synStatus;

    @Column(name = "XC_FLAG")
    @ApiModelProperty(value = "xcFlag")
    private String xcFlag;

    @Column(name = "DATA_SOURCE_AREA_CODE")
    @ApiModelProperty(value = "dataSourceAreaCode")
    private String dataSourceAreaCode;

    @Column(name = "EXCHANGE_STATUS")
    @ApiModelProperty(value = "exchangeStatus")
    private String exchangeStatus;

    @Column(name = "IS_SPECIAL_HD")
    @ApiModelProperty(value = "isSpecialHd")
    private String isSpecialHd;

    @Column(name = "XC_STATUS")
    @ApiModelProperty(value = "xcStatus")
    private String xcStatus;

    @Column(name = "ENTRUST_PATH")
    @ApiModelProperty(value = "纵向协查委托书路径")
    private String entrustPath;

    //private String processTypeName="新申请";

    public void copy(BhBatch source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}