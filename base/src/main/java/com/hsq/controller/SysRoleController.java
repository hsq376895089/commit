package com.hsq.controller;

import com.hsq.entity.SysRole;
import com.hsq.service.SysRoleService;
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
@RequestMapping("role")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @org.springframework.web.bind.annotation.InitBinder
    public void InitBinder(WebDataBinder binder, WebRequest request)
    {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        CustomDateEditor editor = new CustomDateEditor(df,true);//参数为true表示允许为空值
        binder.registerCustomEditor(Date.class, editor);
    }

    @RequestMapping("roleAll")
    public ResponseResult roleAll(SysRole sysRole, Integer page, Integer limit){
        Map<String,Object> map = sysRoleService.roleAll(sysRole,page,limit);
        ResponseResult result = new ResponseResult();
        result.getData().putAll(map);
        return result;
    }

    @RequestMapping("deleteRoleById")
    public ResponseResult deleteRoleById(Integer id){
        sysRoleService.deleteRoleById((long) id);
        ResponseResult result = new ResponseResult();
        return result;
    }

    @RequestMapping(value = "addRole")
    public ResponseResult addRole(SysRole sysRole){
        ResponseResult result = new ResponseResult();
        sysRole.setCreateTime(new Date());
        sysRole.setLastUpdateTime(new Date());
        sysRoleService.addRole(sysRole);
        return result;
    }

    @PostMapping("updateRoleById")
    public ResponseResult updateRoleById(SysRole sysRole){
        ResponseResult result = new ResponseResult();
        sysRole.setLastUpdateTime(new Date());
        sysRoleService.updateRoleById(sysRole);
        return result;
    }

    @RequestMapping("selectByName")
    public ResponseResult selectByName(String rname){
        ResponseResult result = new ResponseResult();
        boolean flag = sysRoleService.selectByName(rname);
        result.getData().put("flag",flag);
        return result;
    }
}
