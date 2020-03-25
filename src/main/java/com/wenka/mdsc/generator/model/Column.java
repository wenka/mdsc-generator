package com.wenka.mdsc.generator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class Column {

    private String name;

    private String humpName;

    private String javaType;

    private String jdbcType;

    private String remark;

}
