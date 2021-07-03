package com.atguigu.springcloud.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by LQ on 2020/3/26.
 */
@Mapper
@Component
public interface DocumentDao {

    void saveFileMessage(@Param("fileName") String fileName, @Param("filePath") String filePath);

    List<Map> getTableColumnsMessage(@Param("tableName") String tableName);

    List<String> getTablesMessage();


}
