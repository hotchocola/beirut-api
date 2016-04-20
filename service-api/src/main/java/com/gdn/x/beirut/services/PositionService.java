package com.gdn.x.beirut.services;

import java.util.List;

import com.gdn.x.beirut.entities.Position;

public interface PositionService {

    List<Position> getAllPosition();
    void insertNewPosition(Position position);
    void markForDeletePosition(Position position);
    void updatePosition(Position oldPosition, Position newPosition);
}
