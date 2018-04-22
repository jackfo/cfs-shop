package com.whpu;



import com.github.pagehelper.PageInfo;
import com.whpu.model.CmDepartment;
import com.whpu.model.CmDepartmentEmployee;
import com.whpu.model.CmEmployee;

/**
 * Created by jack on 2018/1/18.
 */
public interface IDepartmentService {

    public PageInfo<CmDepartment> findUserByPage(int page, int limit, String keyword);

    public CmDepartment createDepartment(CmDepartment cmDepartment);

    public CmDepartment findDepartmentById(String DepartmentId);

    public int updateDepartment(CmDepartment cmDepartment);

    public int delDepartment(String departmentId);

    public PageInfo<CmEmployee> getEmployeeByDepartment(int page, int limit, String departmentId);

    public int addDepartmentEmployee(CmDepartmentEmployee cmDepartmentEmployee);

    public int deleteDepartmentEmployee(CmDepartmentEmployee cmDepartmentEmployee);
}
