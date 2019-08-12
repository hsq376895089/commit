package com.hsq.controller;

import com.hsq.entity.SysUser;
import com.hsq.service.SysUserService;
import com.hsq.utils.ResponseResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
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
@RequestMapping("user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @org.springframework.web.bind.annotation.InitBinder
    public void InitBinder(WebDataBinder binder, WebRequest request)
    {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        CustomDateEditor editor = new CustomDateEditor(df,true);//参数为true表示允许为空值
        binder.registerCustomEditor(Date.class, editor);
    }

    @RequestMapping("info")
    public ResponseResult info(String token){
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        ResponseResult result = new ResponseResult();
        if(token.equals(subject.getSession().getId().toString())){
            // 从shiro的session里获得保存的用户信息
            SysUser loginUser = (SysUser) session.getAttribute("USER_SESSION");
            // 获得角色字符串集合
            List<String> roles = (List<String>) session.getAttribute("roles");
            if(loginUser!=null){
                // 根据用户获得角色字符串数组
                roles = sysUserService.findUserRoles(loginUser.getUsername());
                result.getData().put("roles",roles);
                result.getData().put("introduction",loginUser.getIntroduction());
                result.getData().put("avatar",loginUser.getAvatar());
                result.getData().put("name",loginUser.getUsername());
                return result;
            }
        }
        return null;
    }

    /*@RequestMapping("info")
    public ResponseResult info(String token){
        SysUser user = sysUserService.findUserByUserName(token);
        List<String> roles =  sysUserService.findUserRoles(token);
        ResponseResult result = new ResponseResult();
        result.getData().put("roles",roles);
        result.getData().put("introduction",user.getIntroduction());
        result.getData().put("avatar",user.getAvatar());
        result.getData().put("name",user.getUsername());
        return result;
    }*/

/*    @RequestMapping("login")
    public ResponseResult login( SysUser sysUser){
        SysUser loginUser = sysUserService.login(sysUser);
        ResponseResult result = new ResponseResult();
        if(loginUser!=null){
            result.getData().put("token",loginUser.getUsername());
        }else{
            result.getData().put("message","登录失败，用户名或密码错误");
        }
        return result;

    }*/
@RequestMapping("login")
public ResponseResult login(SysUser sysUser){
    ResponseResult result = new ResponseResult();
    UsernamePasswordToken token = new UsernamePasswordToken(sysUser.getUsername(), sysUser.getPassword());
    Subject subject = SecurityUtils.getSubject();
    subject.login(token);
    // 返回给前台的toke，唯一标识用户
    result.getData().put("token",subject.getSession().getId());
    return result;
}

    @RequestMapping("userAll")
    @RequiresPermissions("user/userAll")
    public ResponseResult userAll(SysUser sysUser, Integer page, Integer limit){
        Map<String,Object> map = sysUserService.userAll(sysUser,page,limit);
        ResponseResult result = new ResponseResult();
        result.getData().putAll(map);
        return result;
    }

    @RequestMapping("deleteUserById")
    @RequiresPermissions("user/deleteUserById")
    public ResponseResult deleteUserById(Integer id){
        sysUserService.deleteUserById((long) id);
        ResponseResult result = new ResponseResult();
        result.getData().put("message","删除成功");
        return result;
    }
    @RequestMapping("logout")
    public ResponseResult logout(){
        ResponseResult result = new ResponseResult();
        Subject subject = SecurityUtils.getSubject();
        // 只需调用shiro的logout方法就可以了
        subject.logout();
        result.getData().put("message","退出登录");
        return result;
    }


/*    @RequestMapping("addUser")
    public ResponseResult addUser(SysUser sysUser){
        ResponseResult result = new ResponseResult();
        sysUser.setStatus((byte)1);
        sysUser.setDelFlag((byte)0);
        sysUser.setCreateTime(new Date());
        sysUser.setLastUpdateTime(new Date());
        sysUserService.addUser(sysUser);
        return result;
    }*/
    @RequestMapping("addUser")
    @RequiresPermissions("user/addUser")
    public ResponseResult addUser(SysUser sysUser){
        //生成盐（部分，需要存入数据库中）
        String salt=new SecureRandomNumberGenerator().nextBytes().toHex();
        //将原始密码加盐（上面生成的盐），并且用md5算法加密两次，将最后结果存入数据库中
        String password = new Md5Hash(sysUser.getPassword(),salt,2).toString();
        sysUser.setSalt(salt);
        sysUser.setPassword(password);
        sysUser.setCreateTime(new Date());
        sysUser.setLastUpdateTime(new Date());
        sysUserService.addUser(sysUser);
        ResponseResult result = new ResponseResult();
        result.getData().put("message","添加成功");
        return result;
    }

   /* @RequestMapping("updateUserById")
    public ResponseResult updateUserById(SysUser sysUser){
        ResponseResult result = new ResponseResult();
        sysUser.setLastUpdateTime(new Date());
        sysUserService.updateUserById(sysUser);
        return result;
    }*/

    @RequestMapping("updateUserById")
    @RequiresPermissions("user/updateUserById")
    public ResponseResult update(SysUser sysUser){
        //生成盐（部分，需要存入数据库中）
        String salt=new SecureRandomNumberGenerator().nextBytes().toHex();
        //将原始密码加盐（上面生成的盐），并且用md5算法加密两次，将最后结果存入数据库中
        String password = new Md5Hash(sysUser.getPassword(),salt,2).toString();
        sysUser.setSalt(salt);
        sysUser.setPassword(password);
        sysUser.setLastUpdateTime(new Date());
        sysUserService.updateUserById(sysUser);
        ResponseResult result = new ResponseResult();
        result.getData().put("message","修改成功");
        return result;
    }

    @RequestMapping("selectByUsername")
    public ResponseResult selectByUsername(String name){
        ResponseResult result = new ResponseResult();
        boolean flag = sysUserService.selectByUsername(name);
        result.getData().put("flag",flag);
        return result;
    }
}

