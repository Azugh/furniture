package com.furniture.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.furniture.api.service.TryService;
import com.furniture.core.dto.TryClass;

@RestController
@RequestMapping("/api/hello")
public class TryController {

  @Autowired
  TryService tryService;

  @GetMapping
  public ResponseEntity<TryClass> getTry() {
    TryClass tryClass = tryService.helloWorld();
    return new ResponseEntity<>(tryClass, HttpStatus.OK);
  }
}
