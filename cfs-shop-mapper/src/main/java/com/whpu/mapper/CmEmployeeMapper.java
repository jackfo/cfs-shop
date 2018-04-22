package com.whpu.mapper;



import com.github.mapper.common.Mapper;
import com.whpu.model.CmEmployee;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author jack
 * */
public interface CmEmployeeMapper extends Mapper<CmEmployee> {
    /**
     * 查找任意列包含当前传入参数的数据
     * */
    public List<CmEmployee> getEmployeeList(@Param(value = "keyword") String keyword);
}