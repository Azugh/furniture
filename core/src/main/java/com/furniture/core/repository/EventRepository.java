package com.furniture.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.furniture.core.model.Events;

public interface EventRepository extends JpaRepository<Events, Long> {

  List<Events> findEventsByRestaurantId(Long id);
}
