package com.gdn.x.beirut.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gdn.x.beirut.entities.Position;

public interface PositionDAO extends JpaRepository<Position, String>{
   List<Position> findByTitle(String title);
   List<Position> findByTitleContaining(String title);
}
