package com.whpu;

import com.github.pagehelper.PageInfo;
import com.whpu.model.CmEmployee;

import java.util.List;

/**
 * Created by jack on 2018/1/17.
 */
public interface IEmployeeService {

    public PageInfo<CmEmployee> findUserByPage(int page, int limit, String keyword);

    public CmEmployee createEmployee(CmEmployee cmEmployee);

    public CmEmployee findEmployeeById(String employeeId);

    public int updateEmployee(CmEmployee cmEmployee);

    public int delEmployee(String employeeId);

    public List<CmEmployee> queryAllEmployee();


}
