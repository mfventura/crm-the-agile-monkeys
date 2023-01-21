package com.theagilemonkeys.mfventura.crm.infrastructure.controller;

import com.theagilemonkeys.mfventura.crm.domain.services.CustomersService;
import com.theagilemonkeys.mfventura.crm.infrastructure.controller.requests.CreateCustomerRequest;
import com.theagilemonkeys.mfventura.crm.infrastructure.controller.requests.UpdateCustomerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Base64;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomersController extends ErrorHandler {
  @Autowired
  private CustomersService customersService;
  @GetMapping()
  public ResponseEntity listCustomers(@RequestParam boolean showAll){
    final var customers = customersService.listCustomers();
    return ResponseEntity.ok(customers);
  }

  @PostMapping()
  public ResponseEntity createCustomer(@RequestBody @Valid CreateCustomerRequest request, Authentication auth) {
    final var customer = customersService.createCustomer(request, Integer.parseInt(auth.getName()));
    return ResponseEntity.status(HttpStatus.CREATED).body(customer);
  }
  
  @GetMapping("/{id}")
  public ResponseEntity getCustomer(@PathVariable Integer id) {
    final var customer = customersService.getCustomer(id);
    if(customer != null){
      return ResponseEntity.ok(customer);
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
  }
  
  @GetMapping("/{id}/image")
  @ResponseBody
  public ResponseEntity getCustomerImage(@PathVariable Integer id) {
    final var b64raw = customersService.getImageFromCustomer(id);
    if(b64raw != null){
      final var mimeType = getMimeType(b64raw);
      final var bytes = proccessBase64Chain(b64raw);
      return ResponseEntity.ok()
              .contentLength(bytes.length)
              .contentType(mimeType != null ? MediaType.parseMediaType(mimeType) : MediaType.IMAGE_JPEG)
              .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"image."+(mimeType != null ? mimeType.split("/")[1] : "jpeg")+"\"")
              .body(new ByteArrayResource(bytes));
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
  }
  @PutMapping("/{id}")
  public ResponseEntity updateCustomer(@PathVariable Integer id, @RequestBody @Valid UpdateCustomerRequest request, Authentication auth) {
    final var customer = customersService.updateCustomer(id, Integer.parseInt(auth.getName()), request);
    if(customer != null){
      return ResponseEntity.ok(customer);
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
  }
  @DeleteMapping("/{id}")
  public ResponseEntity deleteCustomer(@PathVariable Integer id, Authentication auth) {
    final var customer = customersService.deleteCustomer(id, Integer.parseInt(auth.getName()));
    if(customer != null){
      return ResponseEntity.ok(customer);
    }
    return ResponseEntity.notFound().build();
  }
  @PutMapping("/{id}/recover")
  public ResponseEntity recoverCustomer(@PathVariable Integer id, Authentication auth) {
    final var customer = customersService.recoverCustomer(id, Integer.parseInt(auth.getName()));
    if(customer != null){
      return ResponseEntity.ok(customer);
    }
    return ResponseEntity.notFound().build();
  }
  private byte[] proccessBase64Chain(String b64raw) {
    final var b64Split = b64raw.split(",");
    final var b64 = (b64Split.length > 1 ? b64Split[1] : b64Split[0]).replaceAll(" ","");
    final var bytes = Base64.getDecoder().decode(b64);
    return bytes;
  }
  
  private String getMimeType(String b64) {
    if(b64.startsWith("data:")){
      final var data = b64.split(";")[0];
      final var mimeType = data.split(":")[1];
      return mimeType;
    }
    return null;
  }
}
