package com.whpu;

import com.whpu.pojo.SysUser;
import org.springframework.beans.factory.annotation.Autowired;

public class SysUserServiceImpl implements SysUserService{

    @Autowired
    SysUserMapper sysUserMapper;

    @Override
    public SysUser getSysUser() {
        return sysUserMapper.getById("admin");
    }

}
