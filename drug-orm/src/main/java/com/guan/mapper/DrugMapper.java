package com.guan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guan.entiity.Drug;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface DrugMapper extends BaseMapper<Drug> {

    @Update("set FOREIGN_KEY_CHECKS = 0;")
    void freeFOREIGN_KEY_CHECKS();

    @Update("set FOREIGN_KEY_CHECKS = 1;")
    void regainFOREIGN_KEY_CHECKS();

    @Select("SELECT  @@FOREIGN_KEY_CHECKS;")
    Integer selectFOREIGN_KEY_CHECKS();

}
