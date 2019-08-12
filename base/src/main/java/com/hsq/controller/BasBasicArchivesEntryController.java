package com.hsq.controller;

import com.hsq.entity.BasBasicArchivesEntry;
import com.hsq.service.BasBasicArchivesEntryService;
import com.hsq.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("basBasicArchivesEntry")
public class BasBasicArchivesEntryController {

    @Autowired
    private BasBasicArchivesEntryService basBasicArchivesEntryService;
    //  ---------- 查看列表
    @RequestMapping("findByPid")
    public ResponseResult findByPid(Integer pid){
        ResponseResult result=new ResponseResult();
        List<BasBasicArchivesEntry> list=basBasicArchivesEntryService.findByPid(pid);
        result.getData().put("BList",list);
        return result;
    }

    @RequestMapping("deleteBasById")
    public ResponseResult deleteBasById(Long id){
        basBasicArchivesEntryService.deleteBasById(id);
        ResponseResult result = new ResponseResult();
        return result;
    }

    @RequestMapping("addBas")
    public ResponseResult addBas(BasBasicArchivesEntry basBasicArchivesEntry){
        ResponseResult result = new ResponseResult();
        // basBasicArchives.setOperationtime(new Date());
        basBasicArchivesEntryService.addBas(basBasicArchivesEntry);
        return result;
    }

    @RequestMapping("updateBasById")
    public ResponseResult updateBasById(BasBasicArchivesEntry basBasicArchivesEntry){
        ResponseResult result = new ResponseResult();
        // basBasicArchives.setOperationtime(new Date());
        basBasicArchivesEntryService.updateBasById(basBasicArchivesEntry);
        return result;
    }



    /**
     * 时间解析
     * @param binder
     * @param request
     */
    @org.springframework.web.bind.annotation.InitBinder
    public void InitBinder(WebDataBinder binder, WebRequest request)
    {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        CustomDateEditor editor = new CustomDateEditor(df,true);//参数为true表示允许为空值
        binder.registerCustomEditor(Date.class, editor);
    }
}
