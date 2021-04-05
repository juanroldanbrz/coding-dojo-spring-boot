package com.assignment.spring.mapper;

import com.assignment.spring.model.api.WeatherResponse;
import com.assignment.spring.model.dto.WeatherDTO;
import com.assignment.spring.model.entity.WeatherEntity;
import org.springframework.stereotype.Component;

@Component
public class WeatherMapper {

  public WeatherEntity map(WeatherResponse response) {
    var entity = new WeatherEntity();
    entity.setCity(response.getName());
    entity.setCountry(response.getSys().getCountry());
    entity.setTemperature(response.getMain().getTemp());
    return entity;
  }

  public WeatherDTO map(WeatherEntity entity) {
    return new WeatherDTO(entity.getId(), entity.getCity(), entity.getCountry(),
        entity.getTemperature());
  }
}
