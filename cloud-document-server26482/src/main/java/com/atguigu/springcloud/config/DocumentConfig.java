package com.atguigu.springcloud.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by LQ on 2020/11/22.
 */
@Component
@ConfigurationProperties(prefix = "document")
@Data
public class DocumentConfig {

    private String preFilePath;

    private String suffixFilePathExcel;

    private String suffixFilePathJava;

    private String nextLine;

    private String space;


}
