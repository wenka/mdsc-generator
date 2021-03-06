package com.wenka.mdsc.generator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Created with IDEA
 *
 * @author wenka wkwenka@gmail.com
 * @date 2020/03/23  下午 04:59
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Column {

    private String name;

    private String humpName;

    private String javaType;

    private String jdbcType;

    private String remark;

    /**
     * 是否为主键
     */
    private boolean primary;

    /**
     * 主键序列号
     */
    private Integer pkSeq;
}
