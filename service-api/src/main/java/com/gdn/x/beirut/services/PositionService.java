package com.gdn.x.beirut.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdn.x.beirut.entities.Position;

public interface PositionService {

  List<Position> getAllPosition();

  Page<Position> getAllPositionWithPageable(Pageable pageable);

  Position getPosition(String positionId) throws Exception;

  List<Position> getPositionByStoreIdAndMarkForDelete(String storeId, boolean markForDelete);

  List<Position> getPositionByTitle(String title, String storeId);

  Position getPositionDetailByIdAndStoreId(String id, String storeId) throws Exception;

  boolean insertNewPosition(Position position);

  void markForDeletePosition(List<String> id);

  boolean updatePositionTitle(String id, String title);
}
