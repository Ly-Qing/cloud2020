package com.atguigu.springcloud.service.impl;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.atguigu.springcloud.dao.DocumentDao;
import com.atguigu.springcloud.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by LQ on 2020/3/26.
 */
@Service
public class DocumentServiceImpl implements DocumentService {

    @Value("${document.pre-file-path}")
    private String documentPreFilePath;

    @Value("${document.suffix-file-path}")
    private String documentSuffixFilePath;

    @Autowired
    private DocumentDao documentDao;

    @Override
    public void saveFileMessage(String fileName,List<Map<String,Object>> items) {
        String filePath = documentPreFilePath + "/" + fileName + System.currentTimeMillis() + documentSuffixFilePath;
        new Thread(fileName){
            @Override
            public void run() {
                // 通过工具类创建writer
                ExcelWriter writer = ExcelUtil.getWriter(filePath);
                //自定义标题别名
                writer.addHeaderAlias("name", "参数名称");
                writer.addHeaderAlias("des", "参数描述");
                writer.addHeaderAlias("check", "是否必输");
                writer.addHeaderAlias("remark", "备注");
                // 合并单元格后的标题行，使用默认标题样式
                items.stream().forEach(item -> {
                    String method = (String)item.get("method");
                    ArrayList<Map<String, Object>> rows = CollUtil.newArrayList((List<Map<String,Object>>)item.get("request"));
                    writer.merge(2, method);
                    // 一次性写出内容，使用默认样式，强制输出标题
                    writer.write(rows, true);
                    rows = CollUtil.newArrayList((List<Map<String,Object>>)item.get("response"));
                    writer.write(rows, true);
                });
                // 关闭writer，释放内存
                writer.close();
                documentDao.saveFileMessage(fileName,filePath);
            }
        }.start();
    }
}
