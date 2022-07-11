package com.guan.controller;

import com.guan.entiity.Drug;
import com.guan.entiity.HR;
import com.guan.service.DrugService;
import com.guan.service.IPInfoService;
import com.guan.util.IPUtil;
import com.guan.vo.DataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/drug/")
public class DrugController {

    @Autowired
    private DrugService drugService;

    @Autowired
    private IPInfoService ipInfoService;

    @GetMapping("/home/all")
    public DataVO<Drug> getDrugDataByPageAndLimit(@RequestParam(value = "page", required = false) Integer page,
                                                  @RequestParam(value = "limit", required = false) Integer limit,
                                                  HttpServletRequest request) {
        String ipAddr = IPUtil.getIpAddr(request);
        System.out.println("Request IP : " + ipAddr);

        // get from ip - [username, ident]
        HR hr = ipInfoService.getIPInfo(ipAddr);
        String username = hr.getUsername();
        Integer ident = hr.getIdent();

        if (username == null || ident == null) {
            System.out.println("NOT LOGIN !!!");
            return null;
        }
        if (page == null || limit == null) {
            return drugService.findAllData(ident);
        }
        System.out.println("Paging query ... ");
        System.out.println("page : " + page + "\t limit : " + limit);
        return drugService.findDataByPageAndLimit(page, limit, ident);
    }

    @PostMapping("/update")
    public Boolean handleUpdate(@RequestBody Drug drug) {
        System.out.println(drug);
        return drugService.updateRefByID(drug);
    }

    @PostMapping("/add")
    public Boolean handleAdd(@RequestBody Drug drug) {
        return drugService.insertEntity(drug);
    }

    @PostMapping("/delete")
    public Boolean deleteByID(@RequestBody Drug drug) {
        // 懒得转换 烦死  ━━(￣ー￣*|||━━
        System.out.println(drug.getDrugID());
        return drugService.deleteById(drug.getDrugID());
    }
}
