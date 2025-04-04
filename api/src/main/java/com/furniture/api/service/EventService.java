package com.furniture.api.service;

import java.util.List;

import com.furniture.core.exception.RestaurantException;
import com.furniture.core.model.Events;

public interface EventService {

  public Events createEvent(Events event, Long restaurantId) throws RestaurantException;

  public List<Events> findAllEvent();

  public List<Events> findRestaurantsEvent(Long id);

  public void deleteEvent(Long id) throws Exception;

  public Events findById(Long id) throws Exception;

}
