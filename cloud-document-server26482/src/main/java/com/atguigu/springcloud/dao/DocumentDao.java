package com.atguigu.springcloud.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by LQ on 2020/3/26.
 */
@Mapper
public interface DocumentDao {

    void saveFileMessage(@Param("fileName") String fileName, @Param("filePath") String filePath);
}
