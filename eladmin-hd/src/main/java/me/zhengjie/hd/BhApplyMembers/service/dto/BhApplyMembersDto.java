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
package me.zhengjie.hd.BhApplyMembers.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author fb
* @date 2020-08-11
**/
@Data
public class BhApplyMembersDto implements Serializable {

    /** 主键 */
    private String id;

    /** 姓名 */
    private String name;

    /** 性别：0-未知,1-男性,2-女性,9-未说明性别 */
    private String sex;

    /** 证件类型：01-身份证,02-护照,03-居住证,04-军官证,05-出生证,06-绿卡,07-港澳通行证,08-士兵证,99-其他 */
    private String idCardCategory;

    /** 证件号码 */
    private String idCardNo;

    /** 与主申请人关系:01-本人,10-配偶,20-子,30-女,40-子孙外孙,50-父母,60-祖父母外祖父母,70兄弟姐妹,99-其他 */
    private String relationship;

    /** 婚姻状况:10-未婚,20-已婚,21-初婚,22-再婚,23-复婚,30-丧偶,40-离婚,90-未知 */
    private String maritalStatus;

    /** 申请编号 */
    private String applyId;

    /** 批次主键 */
    private String batchId;

    /** 行政区划 */
    private String areaCode;

    /** 家庭居住地 */
    private String address;

    /** 户籍所在地 */
    private String domicileAddress;

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