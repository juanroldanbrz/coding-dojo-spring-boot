package com.assignment.spring.utils;

import com.assignment.spring.model.api.Main;
import com.assignment.spring.model.api.Sys;
import com.assignment.spring.model.api.WeatherResponse;
import com.assignment.spring.model.entity.WeatherEntity;
import java.time.LocalDateTime;

public class TestUtils {

  public static WeatherResponse createWeatherResponse(String city, String country,
      Double temperature) {
    var apiResponse = new WeatherResponse();
    apiResponse.setName(city);

    var sys = new Sys();
    sys.setCountry(country);
    apiResponse.setSys(sys);

    var main = new Main();
    main.setTemp(temperature);
    apiResponse.setMain(main);
    return apiResponse;
  }

  public static WeatherEntity createWeatherEntity(Integer id, String city, String country,
      Double temperature) {
    var weatherEntity = new WeatherEntity();
    weatherEntity.setId(id);
    weatherEntity.setTemperature(temperature);
    weatherEntity.setCountry(country);
    weatherEntity.setCity(city);
    weatherEntity.setCreatedAt(LocalDateTime.now());
    return weatherEntity;
  }
}
