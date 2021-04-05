package com.assignment.spring.it;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.assignment.spring.client.WeatherApiClient;
import com.assignment.spring.model.dto.WeatherDTO;
import com.assignment.spring.repository.WeatherRepository;
import com.assignment.spring.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.RestClientResponseException;

class WeatherControllerIT extends PostgresIT {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Autowired
  private WeatherRepository weatherRepository;

  @MockBean
  private WeatherApiClient weatherApiClient;

  @Test
  public void happyPathOk() {
    var city = "sevilla";
    var externalApiResponse = TestUtils.createWeatherResponse(city, "ES", 123.45d);
    when(weatherApiClient.request(city)).thenReturn(externalApiResponse);

    var serverResponse = this.restTemplate
        .getForObject("http://localhost:" + port + "/weather?city=" + city, WeatherDTO.class);

    assertNotNull(serverResponse.getId());
    assertEquals(123.45d, serverResponse.getTemperature());
    assertEquals("ES", serverResponse.getCountry());
    assertEquals(city, serverResponse.getCity());
    assertTrue(weatherRepository.existsById(serverResponse.getId()));
  }

  @Test
  public void whenCityNotFound_return404() {
    var city = "sevilla";
    var exception = new RestClientResponseException("City not found", 404, "notFound", null, null,
        null);
    when(weatherApiClient.request(city)).thenThrow(exception);

    var serverResponse = this.restTemplate
        .getForEntity("http://localhost:" + port + "/weather?city=" + city, WeatherDTO.class);

    assertEquals(404, serverResponse.getStatusCode().value());
  }

  @Test
  public void whenApiKeyNotValid_return500() {
    var city = "sevilla";
    var exception = new RestClientResponseException("Invalid API code", 401, "Unauthorized", null,
        null, null);
    when(weatherApiClient.request(city)).thenThrow(exception);

    var serverResponse = this.restTemplate
        .getForEntity("http://localhost:" + port + "/weather?city=" + city, WeatherDTO.class);

    assertEquals(500, serverResponse.getStatusCode().value());
  }


}