package com.assignment.spring.it;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.assignment.spring.model.entity.WeatherEntity;
import com.assignment.spring.repository.WeatherRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class WeatherRepositoryIT extends PostgresIT {

  @Autowired
  private WeatherRepository weatherRepository;

  @Test
  void whenPersistingObject_IsStoredInPostgres() {
    var toPersist = new WeatherEntity();
    toPersist.setCity("sevilla");
    toPersist.setCountry("Spain");
    toPersist.setTemperature(181.12d);
    var stored = weatherRepository.save(toPersist);

    assertNotNull(stored.getId());
    assertNotNull(stored.getCity());
    assertNotNull(stored.getCountry());
    assertNotNull(stored.getTemperature());
    assertNotNull(stored.getCreatedAt());
  }

}