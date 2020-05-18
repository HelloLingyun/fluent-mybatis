/*
 * Copyright (c) 2011-2020, baomidou (jobob@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.mybatisplus.extension.plugins.pagination.dialects;

import com.mybatisplus.extension.plugins.pagination.DialectModel;

/**
 * Postgre 数据库分页语句组装实现
 *
 * @author hubin
 * @since 2016-01-23
 */
public class PostgreDialect implements IDialect {

    @Override
    public DialectModel buildPaginationSql(String originalSql, long offset, long limit) {
        String sql = originalSql + " limit " + FIRST_MARK + " offset " + SECOND_MARK;
        return new DialectModel(sql, limit, offset).setConsumerChain();
    }
}