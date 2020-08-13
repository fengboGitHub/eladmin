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
package me.zhengjie.hd.BhBatch.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author fb
* @date 2020-07-28
**/
@Data
public class BhBatchDto implements Serializable {

    /** 主键 */
    private String id;

    /** 名称 */
    private String name;

    /** 委托时间 */
    private Timestamp entrustTime;

    /** 核对机构主键 */
    private String organization;

    /** 业务类型 */
    private String bizCategory;

    /** 方案主键 */
    private String solutionId;

    /** 报告模板主键 */
    private String reportTemplateId;

    /** 状态:1部分退回,2全部退回,3中途终止,4正常终止 */
    private String status;

    /** 数据来源:01-数据采集,02-手工批量导入,03-自动批量导入,04-社会救助系统 */
    private String dataResource;

    /** 行政区划 */
    private String areaCode;

    /** 是否反馈核对报告 */
    private String isReturn;

    /** 流程key */
    private String processKey;

    /** 流程状态:1-待提交,2-处理中,3-已完成 */
    private String bpmStatus;

    /** 流程类型:0-正常,1-复核 */
    private String processType;

    /** 删除标志:0-未删除,1-已删除 */
    private String deleteFlag;

    /** 创建时间 */
    private Timestamp createDate;

    /** 创建人 */
    private String createBy;

    /** 修改时间 */
    private Timestamp updateDate;

    /** 修改人 */
    private String updateBy;

    /** 处理状态：1.待处理，2.处理中，3已处理 */
    private String synStatus;

    private String xcFlag;

    private String dataSourceAreaCode;

    private String exchangeStatus;

    private String isSpecialHd;

    private String xcStatus;

    /** 纵向协查委托书路径 */
    private String entrustPath;
    /** 行政区划名称 */
    private String areaCodeName;
}