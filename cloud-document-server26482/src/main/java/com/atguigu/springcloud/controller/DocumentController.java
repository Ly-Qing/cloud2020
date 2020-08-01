package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.service.DocumentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by LQ on 2020/3/26.
 */
@Controller
@Slf4j
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST,value = "genDocument")
    public CommonResult<Map> genDocument(@RequestBody Map<String,Object> params){
        //写入文档
        String fileName = (String)params.get("name");
        documentService.saveFileMessage(fileName,(List<Map<String,Object>>)params.get("items"));
        return new CommonResult<Map>(000,"success",params);
    }
}
