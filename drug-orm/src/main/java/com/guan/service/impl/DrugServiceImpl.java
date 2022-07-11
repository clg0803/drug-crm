package com.guan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.guan.entiity.Drug;
import com.guan.mapper.DrugMapper;
import com.guan.service.DrugService;
import com.guan.vo.DataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

@Service
public class DrugServiceImpl implements DrugService {

    @Autowired
    private DrugMapper drugMapper;

    private void setVOAttribute(DataVO<Drug> drugDataVO, List<Drug> drugList, Integer count, Integer ident) {
        drugDataVO.setCode(0);
        drugDataVO.setMsg("sss");
        drugDataVO.setCount(count);
        drugDataVO.setData(drugList);
        drugDataVO.setDocType(ident);
    }

    @Override
    public DataVO<Drug> findAllData(Integer ident) {
        QueryWrapper<Drug> wrapper = new QueryWrapper<>();
        if (ident != 3) {
            wrapper.eq("class", ident).or().eq("class", 3);
        }
        List<Drug> drugList = drugMapper.selectList(wrapper);
        DataVO<Drug> drugDataVO = new DataVO<>();
        setVOAttribute(drugDataVO, drugList, drugList.size(), ident);
        return drugDataVO;
    }

    @Override
    public DataVO<Drug> findDataByPageAndLimit(Integer page, Integer limit, Integer ident) {
        QueryWrapper<Drug> wrapper = new QueryWrapper<>();
        if (ident != 3) {
            wrapper.eq("class", ident).or().eq("class", 3);
        }
        Long count = drugMapper.selectCount(wrapper);

        wrapper.last("limit " + (page - 1) * limit + ", " + limit);
        List<Drug> drugList = drugMapper.selectList(wrapper);

        DataVO<Drug> drugDataVO = new DataVO<>();
        setVOAttribute(drugDataVO, drugList, count.intValue(), ident);
        return drugDataVO;
    }

    @Override
    public Boolean updateRefByID(Drug entity) {
        UpdateWrapper<Drug> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("drug_id", entity.getDrugID());
        if (drugMapper.update(entity, updateWrapper) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean insertEntity(Drug entity) {
        return drugMapper.insert(entity) > 0;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public Boolean deleteById(Integer id) {
        int toDelete = 0;
        try {
            drugMapper.freeFOREIGN_KEY_CHECKS();
            toDelete = drugMapper.deleteById(id);
            drugMapper.regainFOREIGN_KEY_CHECKS();
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            return false;
        }
        return toDelete > 0;
    }

}
