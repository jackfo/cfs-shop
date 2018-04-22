package com.whpu.mapper;

import com.github.mapper.common.Mapper;
import com.whpu.model.SysPermission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysPermissionMapper extends Mapper<SysPermission> {

    public SysPermission getPermissionByRoleId(String roleId);

    public List<SysPermission> getPermissionList(@Param(value = "keyword") String keyword);


}