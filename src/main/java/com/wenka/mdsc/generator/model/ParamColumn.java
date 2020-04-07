package com.wenka.mdsc.generator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IDEA
 *
 * @author wenka wkwenka@gmail.com
 * @date 2020/04/06  下午 01:20
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParamColumn extends Column {

    private String operator;
}
