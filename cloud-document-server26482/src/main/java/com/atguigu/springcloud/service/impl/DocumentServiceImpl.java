package com.atguigu.springcloud.service.impl;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.atguigu.springcloud.config.DocumentConfig;
import com.atguigu.springcloud.dao.DocumentDao;
import com.atguigu.springcloud.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

/**
 * Created by LQ on 2020/3/26.
 */
@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private DocumentDao documentDao;

    @Autowired
    private Executor executor;

    @Autowired
    private DocumentConfig documentConfig;

    @Override
    public void saveFileMessage(String fileName, List<Map<String, Object>> items) {
        String filePath = documentConfig.getPreFilePath() + "/" + fileName + System.currentTimeMillis() + documentConfig.getSuffixFilePathExcel();
        executor.execute(() -> {
            // 通过工具类创建writer
            ExcelWriter writer = ExcelUtil.getWriter(filePath);
            //自定义标题别名
            writer.addHeaderAlias("name", "参数名称");
            writer.addHeaderAlias("des", "参数描述");
            writer.addHeaderAlias("check", "是否必输");
            writer.addHeaderAlias("remark", "备注");
            // 合并单元格后的标题行，使用默认标题样式
            items.stream().forEach(item -> {
                String method = (String) item.get("method");
                ArrayList<Map<String, Object>> rows = CollUtil.newArrayList((List<Map<String, Object>>) item.get("request"));
                writer.merge(2, method);
                // 一次性写出内容，使用默认样式，强制输出标题
                writer.write(rows, true);
                rows = CollUtil.newArrayList((List<Map<String, Object>>) item.get("response"));
                writer.write(rows, true);
            });
            // 关闭writer，释放内存
            writer.close();
            documentDao.saveFileMessage(fileName, filePath);
        });
    }

    @Override
    public void genTableDocument(Map<String, Object> params) {
        Map<String, List<Map>> tableMessage = this.getTableMessage();
        String fileName = (String) params.get("name");
        executor.execute(() -> {
            String filePath = documentConfig.getPreFilePath() + "/" + fileName + System.currentTimeMillis() + documentConfig.getSuffixFilePathExcel();
            ExcelWriter writer = ExcelUtil.getWriter(filePath);
            //自定义标题别名
            writer.addHeaderAlias("Field", "字段名称");
            writer.addHeaderAlias("Type", "字段类型");
            writer.addHeaderAlias("Null", "可以为空");
            writer.addHeaderAlias("Key", "约束");
            writer.addHeaderAlias("Default", "默认");
            writer.addHeaderAlias("Extra", "其他");
            tableMessage.entrySet().stream().forEach(entry -> {
                String tableName = entry.getKey();
                List<Map> columns = entry.getValue();
                columns = columns.stream().map(column -> {
                    String defaultMsg = (String) column.get("Default");
                    if (defaultMsg == null || "".equals(defaultMsg)) {
                        column.put("Default", "");
                    }
                    return column;
                }).collect(Collectors.toList());
                writer.merge(5, tableName);
                writer.write(columns, true);
            });
            writer.close();
            documentDao.saveFileMessage(fileName, filePath);
        });
    }

    @Override
    public void genJavaDocument(Map<String, Object> params) {
        Map<String, List<Map>> tableMessage = this.getTableMessage();
        executor.execute(() -> {
            tableMessage.entrySet().stream().forEach(entry -> {
                try {
                    String tableName = entry.getKey();
                    List<Map> columns = entry.getValue();
                    String filePath = documentConfig.getPreFilePath() + "/" + tableName + documentConfig.getSuffixFilePathJava();
                    //新建文件
                    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath));
                    columns.forEach(map -> {
                        try {
                            bufferedWriter.write(map.get("Type").toString() + map.get("Field").toString() + documentConfig.getNextLine());
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {

                        }
                    });
                    bufferedWriter.flush();
                    bufferedWriter.close();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {

                }
            });
        });
    }

    private Map<String, List<Map>> getTableMessage() {
        List<String> tables = documentDao.getTablesMessage();
        Map<String, List<Map>> tableMessage = new HashMap<>();
        tables.stream().forEach(table -> {
            List<Map> columnsMessage = documentDao.getTableColumnsMessage(table);
            tableMessage.put(table, columnsMessage);
        });
        return tableMessage;
    }
}
