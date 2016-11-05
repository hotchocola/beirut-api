package com.gdn.x.beirut.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdn.x.beirut.entities.Position;
import com.gdn.x.beirut.entities.PositionDescription;
import com.gdn.x.beirut.entities.StatusPosition;

public interface PositionService {

  public List<Position> getAllPositionByStoreId(String storeId);

  public Page<Position> getAllPositionByStoreIdWithPageable(String storeId, Pageable pageable);

  public Page<Position> getAllPositionByStoreIdWithPageableAndMarkForDelete(String storeId,
      Pageable generatePageable, boolean isDeleted);

  public Position getPosition(String storeId, String positionId) throws Exception;

  public Position getPositionByStoreIdAndId(String storeId, String id);

  public List<Position> getPositionByStoreIdAndMarkForDelete(String storeId, boolean markForDelete);

  public List<Position> getPositionByTitle(String title, String storeId);

  public PositionDescription getPositionDescriptionAndStoreId(String id, String storeId)
      throws Exception;

  public Position getPositionDetailByIdAndStoreId(String id, String storeId) throws Exception;

  public Position insertNewPosition(Position position);

  public void markForDeletePosition(String storeId, List<String> id) throws Exception;

  public boolean updatePositionInformation(Position position) throws Exception;

  public void updatePositionStatus(String storeId, String idPosition, StatusPosition status)
      throws Exception;

  public void updatePositionStatusBulk(String storeId, List<String> idPositions,
      StatusPosition status) throws Exception;
}
