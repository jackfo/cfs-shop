package com.whpu.imlp;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.whpu.ISysUserService;
import com.whpu.mapper.SysUserMapper;
import com.whpu.model.SysUser;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional

public class SysUserServiceImpl implements ISysUserService {
    //TODO:注意添加static 暂时不清楚原因 不加会动态代理错误
    @Autowired
    private  SysUserMapper sysUserMapper;


    @Override
    public PageInfo<SysUser> findUserByPage(int page, int limit, String keyword) {
        PageHelper.startPage(page, limit);
        if(keyword==null){
            keyword = "";
        }
        List<SysUser> list = sysUserMapper.getUserList(keyword);
        //CmDepartmentMapper.select()
        return new PageInfo<SysUser>(list);
    }

    @Override
    public SysUser createUser(SysUser sysUser) {
        sysUserMapper.insertSelective(sysUser);
        return sysUser;
    }

    @Override
    public SysUser findUserById(String userLoginId) {
        return sysUserMapper.selectByPrimaryKey(userLoginId);
    }

    @Override
    public int updateUser(SysUser sysUser) {
        return sysUserMapper.updateByPrimaryKey(sysUser);
    }

    @Override
    public int delUser(String UserId) {
        return sysUserMapper.deleteByPrimaryKey(UserId);
    }

    @Override
    public SysUser getSysUser(String userLoginId) {
        return sysUserMapper.getSysUser(userLoginId);
    }
}
