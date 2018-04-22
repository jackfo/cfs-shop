package com.whpu;


import com.github.pagehelper.PageInfo;
import com.whpu.model.SysUser;

public interface ISysUserService {
    public SysUser getSysUser(String userLoginId);

    public PageInfo<SysUser> findUserByPage(int page, int limit, String keyword);

    public SysUser createUser(SysUser sysUser);

    public SysUser findUserById(String UserId);

    public int updateUser(SysUser sysUser);

    public int delUser(String UserId);
}
