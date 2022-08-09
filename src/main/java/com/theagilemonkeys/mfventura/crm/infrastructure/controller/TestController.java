package com.theagilemonkeys.mfventura.crm.infrastructure.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {
  @GetMapping("/get")
  public String test(Authentication auth){
    return auth.getName();
  }
  
  @GetMapping("/get2")
  public String test2(Authentication auth){
    return "test2";
  }
}
