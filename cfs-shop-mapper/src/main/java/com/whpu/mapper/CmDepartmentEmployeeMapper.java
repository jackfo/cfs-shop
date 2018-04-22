package com.whpu.mapper;

import com.github.mapper.common.Mapper;
import com.whpu.model.CmDepartmentEmployee;
import org.apache.ibatis.annotations.Param;

public interface CmDepartmentEmployeeMapper extends Mapper<CmDepartmentEmployee> {

    public Integer deleteByEmployeeId(@Param(value = "employeeId") String employeeId);

    public Integer deleteByDepartmentId(@Param(value = "departmentId") String departmentId);
}