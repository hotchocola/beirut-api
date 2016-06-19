package com.gdn.x.beirut.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdn.x.beirut.entities.Position;

public interface PositionService {

  List<Position> getAllPosition(String storeId);

  Page<Position> getAllPositionWithPageable(String storeId, Pageable pageable);

  Position getPosition(String storeId, String positionId) throws Exception;

  List<Position> getPositionByIds(List<String> positionIds);

  List<Position> getPositionByStoreIdAndMarkForDelete(String storeId, boolean markForDelete);

  List<Position> getPositionByTitle(String title, String storeId);

  Position getPositionDetailByIdAndStoreId(String id, String storeId) throws Exception;

  boolean insertNewPosition(Position position);

  void markForDeletePosition(String storeId, List<String> id) throws Exception;

  boolean updatePositionTitle(String storeId, String id, String title) throws Exception;
}
