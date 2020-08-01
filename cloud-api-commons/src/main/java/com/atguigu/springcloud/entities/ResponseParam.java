package com.atguigu.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by LQ on 2020/7/26.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseParam {
    //名称
    private String name;
    //描述
    private String des;
    //备注
    private String remark;
}
