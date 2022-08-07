package com.theagilemonkeys.mfventura.crm.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {
  @GetMapping("/get")
  public String test(){
    return "test";
  }
  
  @GetMapping("/get2")
  public String test2(){
    return "test2";
  }
}
