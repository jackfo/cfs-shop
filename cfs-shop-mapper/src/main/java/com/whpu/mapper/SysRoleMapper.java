package com.whpu.mapper;

import com.github.mapper.common.Mapper;
import com.whpu.model.SysRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by jack on 2017/10/26.
 */
@Repository
public interface SysRoleMapper extends Mapper<SysRole> {

    public List<SysRole> getRoleList(@Param(value = "keyword") String keyword);
}
