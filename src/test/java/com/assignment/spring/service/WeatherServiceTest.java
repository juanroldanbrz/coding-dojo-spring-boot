package com.assignment.spring.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.assignment.spring.client.WeatherApiClient;
import com.assignment.spring.mapper.WeatherMapper;
import com.assignment.spring.repository.WeatherRepository;
import com.assignment.spring.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WeatherServiceTest {

  @Mock
  private WeatherRepository weatherRepository;

  @Mock
  private WeatherApiClient weatherApiClient;

  @Spy
  private WeatherMapper weatherMapper;

  @InjectMocks
  private WeatherService weatherService;

  @Test
  public void whenGetAndSave_CallClientStoreInDbAndMap() {
    var city = "sevilla";
    var apiResponse = TestUtils.createWeatherResponse(city, "ES", 123.45d);
    var weatherEntity = TestUtils.createWeatherEntity(1, city, "ES", 123.45d);

    when(weatherApiClient.request(city)).thenReturn(apiResponse);
    when(weatherRepository.save(any())).thenReturn(weatherEntity);

    var response = weatherService.getAndSave(city);

    verify(weatherApiClient, times(1)).request(eq(city));
    verify(weatherRepository, times(1)).save(any());
    verify(weatherMapper, times(1)).map(eq(apiResponse));
    verify(weatherMapper, times(1)).map(eq(weatherEntity));

    assertEquals(1, response.getId());
  }
}