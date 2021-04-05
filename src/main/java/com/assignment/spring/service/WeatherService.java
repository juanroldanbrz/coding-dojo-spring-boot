package com.assignment.spring.service;

import com.assignment.spring.client.WeatherApiClient;
import com.assignment.spring.mapper.WeatherMapper;
import com.assignment.spring.model.dto.WeatherDTO;
import com.assignment.spring.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherService {

  private final WeatherApiClient weatherApiClient;
  private final WeatherRepository weatherRepository;
  private final WeatherMapper weatherMapper;

  public WeatherDTO getAndSave(String city) {
    var response = weatherApiClient.request(city);
    var weatherEntity = weatherMapper.map(response);
    weatherEntity = weatherRepository.save(weatherEntity);
    log.debug("Saved new weather entity. <id: {}>", weatherEntity.getId());
    return weatherMapper.map(weatherEntity);
  }
}
