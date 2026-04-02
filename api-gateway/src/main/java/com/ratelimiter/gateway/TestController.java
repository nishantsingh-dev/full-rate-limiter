
package com.ratelimiter.gateway;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TestController {
  @GetMapping("/ping")
  public String ping(){
    return "OK";
  }
}
