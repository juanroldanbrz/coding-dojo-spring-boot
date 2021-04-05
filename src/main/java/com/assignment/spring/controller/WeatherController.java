package com.assignment.spring.controller;

import com.assignment.spring.model.dto.WeatherDTO;
import com.assignment.spring.service.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class WeatherController {

  private final WeatherService weatherService;

  @GetMapping("/weather")
  public ResponseEntity<WeatherDTO> weather(@RequestParam String city) {
    log.debug("Requested city: {}", city);
    return ResponseEntity.ok(weatherService.getAndSave(city));
  }

}
