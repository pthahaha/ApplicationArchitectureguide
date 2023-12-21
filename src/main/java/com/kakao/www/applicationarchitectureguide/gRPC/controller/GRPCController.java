package com.kakao.www.applicationarchitectureguide.gRPC.controller;

import com.kakao.www.applicationarchitectureguide.gRPC.service.StubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GRPCController {

    @Autowired
    StubService stubService;
    @GetMapping("/productInfo")
    public void hello(){
        System.out.println("gogo");
        stubService.productInfoClient();
    }
}
