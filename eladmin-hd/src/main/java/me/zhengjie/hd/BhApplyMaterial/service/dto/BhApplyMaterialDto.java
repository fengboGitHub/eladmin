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
package me.zhengjie.hd.BhApplyMaterial.service.dto;

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
public class BhApplyMaterialDto implements Serializable {

    /** 主键 */
    private String id;

    /** 名称 */
    private String name;

    /** 种类 */
    private String category;

    /** 申请编号 */
    private String applyId;

    /** 批次号 */
    private String batchId;

    /** 文件名称 */
    private String fileName;

    /** 文件路径 */
    private String filePath;

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

    /** 证件号码 */
    private String idCardNo;
}