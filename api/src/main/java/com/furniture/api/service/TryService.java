package com.furniture.api.service;

import org.springframework.stereotype.Service;

import com.furniture.core.dto.TryClass;

@Service
public class TryService {

  public TryClass helloWorld() {
    return new TryClass("TryClass", 15);
  }
}
