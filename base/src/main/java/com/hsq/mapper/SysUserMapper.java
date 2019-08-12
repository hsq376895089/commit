package com.hsq.mapper;

import java.util.List;

import com.hsq.entity.SysUser;
import com.hsq.entity.SysUserExample;
import org.apache.ibatis.annotations.Param;

public interface SysUserMapper {
    /**
     * 根据用户名查询角色集合
     * @param username
     * @return
     */
    public List<String> findUserRoles(String username);

    /**
     * 根据用户名查询用户角色和权限
     * @param username 用户名
     * @return
     */
    public List<SysUser> findUserByNameAndRoleAndMenu(String username);

    public void deleteUserById(Long id);

    long countByExample(SysUserExample example);

    int deleteByExample(SysUserExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    List<SysUser> selectByExample(SysUserExample example);

    SysUser selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SysUser record, @Param("example") SysUserExample example);

    int updateByExample(@Param("record") SysUser record, @Param("example") SysUserExample example);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

}
