package com.gdn.x.beirut.services;

import java.util.List;

import com.gdn.x.beirut.entities.Position;

public interface PositionService {

    List<Position> getAllPosition();
    void insertNewPosition(Position position);

    List<Position> markForDeletePosition(List<String> id);
    void updatePositionTitle(String id, String title);
}
