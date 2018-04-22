package com.whpu.imlp;


import com.github.pagehelper.PageInfo;
import com.whpu.ISysPermissionService;
import com.whpu.mapper.SysPermissionMapper;
import com.whpu.model.SysPermission;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class SysPermissionServiceImpl implements ISysPermissionService {


    @Autowired
    private  SysPermissionMapper sysPermissionMapper;

    @Override
    public List<SysPermission> queryAllPermision() {
        List<SysPermission> sysPermissionList = sysPermissionMapper.selectAll();
        return sysPermissionList;
    }

    @Override
    public SysPermission addPermission(SysPermission syspermission){

        sysPermissionMapper.insertSelective(syspermission);
        return syspermission;
    }


    @Override
    public int delPermission(String permissionId) {

        return sysPermissionMapper.deleteByPrimaryKey(permissionId);
    }


    @Override
    public PageInfo<SysPermission> queryAllPermision(int page, int limit,
                                                     String keyword) {
        if(keyword==null){
            keyword = "";
        }
        List<SysPermission> list = (List<SysPermission>) sysPermissionMapper.getPermissionList(keyword);

        return new PageInfo<SysPermission>(list);
    }


    @Override
    public SysPermission findPermissionById(String permissionId) {

        return sysPermissionMapper.selectByPrimaryKey(permissionId);
    }


    @Override
    public int updatePermission(SysPermission syspermission) {

        return sysPermissionMapper.updateByPrimaryKey(syspermission);
    }

}
