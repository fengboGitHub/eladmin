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

import lombok.Data;
import java.util.List;
import me.zhengjie.annotation.Query;

/**
* @website https://el-admin.vip
* @author fb
* @date 2020-08-04
**/
@Data
public class SystemRegionQueryCriteria{

    /** 精确 */
    @Query
    private String id;

    /** 精确 */
    @Query
    private String regionCode;

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String regionName;

    /** 精确 */
    @Query
    private String regionLevel;

    /** 精确 */
    @Query
    private String parentId;
    @Query(type = Query.Type.IS_NULL, propName = "parentId")
    private Boolean parentIdIsNull;
}