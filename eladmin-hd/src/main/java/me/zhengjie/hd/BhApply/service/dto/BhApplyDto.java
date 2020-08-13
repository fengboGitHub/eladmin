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
package me.zhengjie.hd.BhApply.service.dto;

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
public class BhApplyDto implements Serializable {

    /** 主键 */
    private String id;

    /** 主申请人姓名 */
    private String requestor;

    /** 主申请人证件类型 */
    private String idCardCategory;

    /** 主申请人证件号码 */
    private String idCardNo;

    /** 委托时间 */
    private Timestamp authorizeTime;

    /** 核对机构 */
    private String organization;

    /** 业务类型 */
    private String bizCategory;

    /** 批次主键 */
    private String batchId;

    /** 行政区划 */
    private String areaCode;

    /** 委托系统家庭ID */
    private String deputeFamilyId;

    /** 委托系统申请审批ID */
    private String deputeApplicationId;

    /** 业务办理类型:01-新申请,02-复查 */
    private String bizType;

    /** 业务审批结果:01-同意,02-不同意 */
    private String approveResult;

    /** 业务审批描述 */
    private String approveMessage;

    /** 状态 */
    private String status;

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
}