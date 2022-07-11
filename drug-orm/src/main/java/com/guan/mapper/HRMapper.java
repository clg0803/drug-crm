package com.guan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guan.entiity.HR;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface HRMapper extends BaseMapper<HR> {
}
