package com.assignment.spring.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class WeatherDTO {

  private Integer id;
  private String city;
  private String country;
  private Double temperature;

}
