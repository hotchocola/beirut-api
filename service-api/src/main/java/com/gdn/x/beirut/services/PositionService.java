package com.gdn.x.beirut.services;

import java.util.List;

import com.gdn.x.beirut.entities.Position;

public interface PositionService {

  List<Position> getAllPosition();

  List<Position> getPositionByTitle(String title, String storeId);

  boolean insertNewPosition(Position position);

  void markForDeletePosition(List<String> id);

  boolean updatePositionTitle(String id, String title);
}
