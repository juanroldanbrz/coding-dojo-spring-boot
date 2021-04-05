package com.assignment.spring.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.assignment.spring.utils.TestUtils;
import org.junit.jupiter.api.Test;

class WeatherMapperTest {

  private final WeatherMapper weatherMapper = new WeatherMapper();

  @Test
  void whenMappingResponseToEntity_mapOk() {
    var apiResponse = TestUtils.createWeatherResponse("sevilla", "ES", 123.45d);
    var entity = weatherMapper.map(apiResponse);

    assertEquals("sevilla", entity.getCity());
    assertEquals("ES", entity.getCountry());
    assertEquals(123.45d, entity.getTemperature());
    assertNull(entity.getId());
    assertNull(entity.getCreatedAt());
  }

  @Test
  void whenMappingEntityToDto_mapOk() {
    var weatherEntity = TestUtils.createWeatherEntity(1, "sevilla", "ES", 123.45d);
    var dto = weatherMapper.map(weatherEntity);

    assertEquals("sevilla", dto.getCity());
    assertEquals("ES", dto.getCountry());
    assertEquals(123.45d, dto.getTemperature());
    assertEquals(1, dto.getId());
  }
}