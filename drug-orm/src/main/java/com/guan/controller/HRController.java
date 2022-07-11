package com.guan.controller;

import com.guan.entiity.HRError;
import com.guan.service.HRService;
import com.guan.service.IPInfoService;
import com.guan.util.IPUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/hr/")
public class HRController {

    @Autowired
    private HRService hrService;

    @Autowired
    private IPInfoService ipInfoService;

    @GetMapping("/login")
    public HRError login(String username, String password, Integer ident, HttpServletRequest request) {
        System.out.println(" username : " + username + " <==> " + " password : " + password + " <==> " + "ident : " + ident);
        HRError hrError = hrService.checkForLogin(username, password, ident);
        if (hrError.getType().equals("non error")) {
            String ipAddr = IPUtil.getIpAddr(request);
            System.out.println("Request IP : " + ipAddr);

            ipInfoService.saveOrUpdate(ipAddr, username);
        }
        return hrError;
    }

    @GetMapping("/saveAndSendCode")
    public HRError saveAndSendCode(String username, String email, Integer ident) {
        System.out.println(" username : " + username);
        System.out.println(" email    : " + email);
        System.out.println(" ident    : " + ident);
        return hrService.sandCodeAndSaveInfo(username, email, ident);
    }

    @GetMapping("/doValidateAndStoreInfo")
    public HRError doValidateAndStoreInfo(String username, String password, Integer vCode, Integer ident) {
        System.out.println(" username : " + username);
        System.out.println(" password : " + password);
        System.out.println(" vCode    : " + vCode);
        System.out.println(" ident    : " + ident);
        return hrService.doValidateAndStoreInfo(username, password, vCode, ident);
    }
}
