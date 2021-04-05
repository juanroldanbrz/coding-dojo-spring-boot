package com.assignment.spring.model.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "weather")
public class WeatherEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "city")
  private String city;

  @Column(name = "country")
  private String country;

  @Column(name = "temperature", columnDefinition = "NUMERIC", length = 8)
  private Double temperature;

  @CreatedDate
  @Column(name = "created_at")
  private LocalDateTime createdAt;
}
