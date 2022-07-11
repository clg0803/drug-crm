package com.guan.service;

import com.guan.entiity.Drug;
import com.guan.vo.DataVO;

public interface DrugService {
    DataVO<Drug> findAllData(Integer ident);

    DataVO<Drug> findDataByPageAndLimit(Integer page, Integer limit, Integer ident);

    /**
     * @param entity
     * @return `true` if attrs been updated referred by entity.drugID
     */
    Boolean updateRefByID(Drug entity);

    /**
     * fields except `description` must not be null
     * @param entity
     * @return true if insert into `drug_db`
     */
    Boolean insertEntity(Drug entity);

    /**
     *
     * @param id
     * @return 事务
     */
    Boolean deleteById(Integer id);
}
