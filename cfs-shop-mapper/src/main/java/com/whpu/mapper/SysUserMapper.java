package com.whpu.mapper;


import com.github.mapper.common.Mapper;
import com.whpu.model.SysUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SysUserMapper extends Mapper<SysUser>{

    public SysUser getSysUser(String userLoginId);

    /**获取用户带权限*/
    public SysUser getSysUserWithPermission(String userLoginId);

    public List<SysUser> getUserList(@Param(value = "keyword") String keyword);


}
