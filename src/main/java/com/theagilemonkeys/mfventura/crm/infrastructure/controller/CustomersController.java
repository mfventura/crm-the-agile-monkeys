package com.theagilemonkeys.mfventura.crm.infrastructure.controller;

import com.theagilemonkeys.mfventura.crm.domain.services.CustomersService;
import com.theagilemonkeys.mfventura.crm.infrastructure.controller.requests.CreateCustomerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.util.Base64;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomersController extends ErrorHandler {
  @Autowired
  private CustomersService customersService;
  @GetMapping()
  public ResponseEntity listCustomers(){
    final var customers = customersService.listCustomers();
    return ResponseEntity.ok(customers);
  }

  @PostMapping()
  public ResponseEntity createCustomer(@RequestBody CreateCustomerRequest request, Authentication auth) throws UnsupportedEncodingException {
    //TODO validar datos
    final var customer = customersService.createCustomer(request, Integer.parseInt(auth.getName()));
    return ResponseEntity.status(HttpStatus.CREATED).body(customer);
  }
  
  @GetMapping("/{id}")
  @ResponseBody
  public ResponseEntity getCustomerImage(@PathVariable Integer id) throws IOException {
    final var b64raw = customersService.getImageFromCustomer(id);
    if(b64raw != null){
      final var mimeType = getMimeType(b64raw);
      final var b64Split = b64raw.split(",");
      final var b64 = (b64Split.length > 1 ? b64Split[1] : b64Split[0]).replaceAll(" ","");
      final var bytes = Base64.getDecoder().decode(b64);
      return ResponseEntity.ok()
              .contentLength(bytes.length)
              .contentType(mimeType != null ? MediaType.parseMediaType(mimeType) : MediaType.IMAGE_JPEG)
              .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"image."+mimeType.split("/")[1]+"\"")
              .body(new ByteArrayResource(bytes));
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
  }
  
  private String getMimeType(String b64) {
    if(b64.startsWith("data:")){
      final var data = b64.split(";")[0];
      final var mimeType = data.split(":")[1];
      return mimeType;
    }
    return null;
  }

//
//  @GetMapping("/{id}")
//  public ResponseEntity getCustomer(@PathVariable Integer id, Authentication auth) {
//    return "test3";
//  }
//
//  @PutMapping("/{id}")
//  public ResponseEntity updateCustomer(@PathVariable Integer id, Authentication auth) { return "test4"; }
//
//  @DeleteMapping("/{id}")
//  public ResponseEntity deleteCustomer(@PathVariable Integer id, Authentication auth) { return "test5"; }
}
