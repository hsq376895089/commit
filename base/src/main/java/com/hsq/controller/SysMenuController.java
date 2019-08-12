package com.hsq.controller;

import com.hsq.entity.SysMenu;
import com.hsq.service.SysMenuService;
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
import java.util.Map;

@RestController
@RequestMapping("menu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @org.springframework.web.bind.annotation.InitBinder
    public void InitBinder(WebDataBinder binder, WebRequest request)
    {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        CustomDateEditor editor = new CustomDateEditor(df,true);//参数为true表示允许为空值
        binder.registerCustomEditor(Date.class, editor);
    }

    @RequestMapping("menuAll")
    public ResponseResult menuAll(SysMenu sysMenu, Integer page, Integer limit){
        Map<String,Object> map = sysMenuService.menuAll(sysMenu,page,limit);
        ResponseResult result = new ResponseResult();
        result.getData().putAll(map);
        return result;
    }

    @RequestMapping("deleteMenuById")
    public ResponseResult deleteMenuById(Long id){
        ResponseResult result = new ResponseResult();
        boolean flag = sysMenuService.deleteMenuById(id);
        result.getData().put("flag",flag);
        return result;
    }

    @RequestMapping("addMenu")
    public ResponseResult addMenu(SysMenu sysMenu){
        ResponseResult result = new ResponseResult();
        sysMenu.setDelFlag((byte)0);
        //sysMenu.setCreateTime(new Date());
        //sysMenu.setLastUpdateTime(new Date());
        sysMenuService.addMenu(sysMenu);
        return result;
    }

    @RequestMapping("updateMenuById")
    public ResponseResult updateMenuById(SysMenu sysMenu){
        ResponseResult result = new ResponseResult();
        //sysMenu.setLastUpdateTime(new Date());
        sysMenuService.updateMenuById(sysMenu);
        return result;
    }

    @RequestMapping("selectByMenuname")
    public ResponseResult selectByMenuname(String mname){
        ResponseResult result = new ResponseResult();
        boolean flag = sysMenuService.selectByMenuname(mname);
        result.getData().put("flag",flag);
        return result;
    }
}
