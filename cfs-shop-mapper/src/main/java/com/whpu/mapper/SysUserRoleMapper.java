package com.whpu.mapper;

import com.github.mapper.common.Mapper;
import com.whpu.model.SysUser;
import com.whpu.model.SysUserRole;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysUserRoleMapper extends Mapper<SysUserRole> {

    public List<SysUserRole> getRoleList(String userLoginId);

    public SysUser getSysUserWithRole(String userLoginId);
}
