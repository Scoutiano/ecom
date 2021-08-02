package com.example.ecom.repository;

import com.example.ecom.model.Area;
import com.example.ecom.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AreaRepository extends JpaRepository<Area, Long> {

    @Query("SELECT COUNT(a) FROM Area a WHERE a.areaName=?1 AND a.city = ?2")
    Integer findConflictingAreas(String areaName, City city);
}
