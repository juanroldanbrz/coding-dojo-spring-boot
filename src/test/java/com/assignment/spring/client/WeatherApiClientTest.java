package com.assignment.spring.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.client.MockRestServiceServer;

@TestPropertySource(properties = "api.weather.key=changeme")
@RestClientTest(WeatherApiClient.class)
@AutoConfigureWebClient(registerRestTemplate = true)
class WeatherApiClientTest {

  @Autowired
  private MockRestServiceServer server;

  @Autowired
  private WeatherApiClient weatherApiClient;

  @Test
  public void whenRequestingData_thenClientMakesCorrectCall() {
    var expectedJson =
        "{\n" + "  \"coord\": {\n" + "    \"lon\": -0.1257,\n" + "    \"lat\": 51.5085\n" + "  },\n"
            + "  \"weather\": [\n" + "    {\n" + "      \"id\": 802,\n"
            + "      \"main\": \"Clouds\",\n" + "      \"description\": \"scattered clouds\",\n"
            + "      \"icon\": \"03d\"\n" + "    }\n" + "  ],\n" + "  \"base\": \"stations\",\n"
            + "  \"main\": {\n" + "    \"temp\": 280.37,\n" + "    \"feels_like\": 276.99,\n"
            + "    \"temp_min\": 279.15,\n" + "    \"temp_max\": 280.93,\n"
            + "    \"pressure\": 1020,\n" + "    \"humidity\": 28\n" + "  },\n"
            + "  \"visibility\": 10000,\n" + "  \"wind\": {\n" + "    \"speed\": 5.66,\n"
            + "    \"deg\": 350\n" + "  },\n" + "  \"clouds\": {\n" + "    \"all\": 47\n" + "  },\n"
            + "  \"dt\": 1617631200,\n" + "  \"sys\": {\n" + "    \"type\": 1,\n"
            + "    \"id\": 1414,\n" + "    \"country\": \"GB\",\n"
            + "    \"sunrise\": 1617600383,\n" + "    \"sunset\": 1617647973\n" + "  },\n"
            + "  \"timezone\": 3600,\n" + "  \"id\": 2643743,\n" + "  \"name\": \"London\",\n"
            + "  \"cod\": 200\n" + "}";

    this.server
        .expect(requestTo("http://api.openweathermap.org/data/2.5/weather?q=london&APPID=changeme"))
        .andRespond(withSuccess(expectedJson, MediaType.APPLICATION_JSON));

    var response = weatherApiClient.request("london");

    assertEquals("London", response.getName());
    assertEquals("GB", response.getSys().getCountry());
    assertEquals(280.37d, response.getMain().getTemp());
  }
}