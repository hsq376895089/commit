package com.hsq.controller;

import com.hsq.service.SysDeptService;
import com.hsq.entity.SysDept;
import com.hsq.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("dept")
public class SysDeptController {
    @Autowired
    private SysDeptService sysDeptService;

    @org.springframework.web.bind.annotation.InitBinder
    public void InitBinder(WebDataBinder binder, WebRequest request)
    {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        CustomDateEditor editor = new CustomDateEditor(df,true);//参数为true表示允许为空值
        binder.registerCustomEditor(Date.class, editor);
    }

    @RequestMapping("groupDept")
    public ResponseResult groupDept(){
        ResponseResult result = new ResponseResult();
        result.getData().put("deptList",sysDeptService.groupDept());
        return result;
    }

    @RequestMapping("deptAll")
    public ResponseResult deptAll(SysDept sysDept, Integer page, Integer limit){
        Map<String,Object> map = sysDeptService.deptAll(sysDept,page,limit);
        ResponseResult result = new ResponseResult();
        result.getData().putAll(map);
        return result;
    }

    @RequestMapping("deleteDeptById")
    public ResponseResult deleteDeptById(Long id){
        ResponseResult result = new ResponseResult();
        boolean flag = sysDeptService.deleteDeptById(id);
        result.getData().put("flag",flag);
        return result;
    }

    @RequestMapping(value = "addDept")
    public ResponseResult addDept(SysDept sysDept){
        ResponseResult result = new ResponseResult();
        sysDept.setDelFlag((byte)0);
        //sysDept.setCreateTime(new Date());
        //sysDept.setLastUpdateTime(new Date());
        sysDeptService.addDept(sysDept);
        return result;
    }

    @PostMapping("updateDeptById")
    public ResponseResult updateDeptById(SysDept sysDept){
        ResponseResult result = new ResponseResult();
        //sysDept.setLastUpdateTime(new Date());
        sysDeptService.updateDeptById(sysDept);
        return result;
    }

    @RequestMapping("selectByDeptname")
    public ResponseResult selectByDeptname(String dname){
        ResponseResult result = new ResponseResult();
        boolean flag = sysDeptService.selectByDeptname(dname);
        result.getData().put("flag",flag);
        return result;
    }

    @RequestMapping("selectById")
    public ResponseResult selectById(Long id){
        ResponseResult result = new ResponseResult();
        boolean flag = sysDeptService.selectById(id);
        result.getData().put("flag",flag);
        return result;
    }
}
