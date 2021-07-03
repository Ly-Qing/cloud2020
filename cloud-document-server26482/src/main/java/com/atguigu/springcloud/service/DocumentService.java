package com.atguigu.springcloud.service;


import java.util.List;
import java.util.Map;

/**
 * Created by LQ on 2020/3/26.
 */

public interface DocumentService {

    void saveFileMessage(String fileName, List<Map<String,Object>> items);

    void genTableDocument(Map<String, Object> params);

    void genJavaDocument(Map<String, Object> params);
}
