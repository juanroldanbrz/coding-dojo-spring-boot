package com.assignment.spring.client;

import com.assignment.spring.model.api.WeatherResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@RequiredArgsConstructor
@Component
public class WeatherApiClient {

  private static final String WEATHER_API_URL = "http://api.openweathermap.org/data/2.5/weather";

  private final RestTemplate restTemplate;

  @Value("${api.weather.key}")
  private String apiWeatherKey;

  public WeatherResponse request(String city) {
    var uri = UriComponentsBuilder.fromUriString(WEATHER_API_URL).queryParam("q", city)
        .queryParam("APPID", apiWeatherKey).build().toUri();

    log.debug("Requesting URI: {}", uri.toString());
    return restTemplate.getForEntity(uri, WeatherResponse.class).getBody();
  }


}
