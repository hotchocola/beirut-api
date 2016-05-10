package com.gdn.x.beirut.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gdn.x.beirut.entities.Position;

public interface PositionDAO extends JpaRepository<Position, String> {

  List<Position> findByStoreIdAndMarkForDelete(String storeId, boolean bool);

  List<Position> findByTitleContainingAndStoreIdAndMarkForDelete(String title, String storeId, boolean bool);
}
