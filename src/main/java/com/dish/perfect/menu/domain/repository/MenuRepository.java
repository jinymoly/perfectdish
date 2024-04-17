package com.dish.perfect.menu.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dish.perfect.menu.domain.Availability;
import com.dish.perfect.menu.domain.CourseType;
import com.dish.perfect.menu.domain.Menu;

public interface MenuRepository extends JpaRepository<Menu, String>{

    @Query("select m from Menu m where m.menuName = :menuName")
    Menu findByMenuName(@Param("menuName") String menuName);
    
    @Query("select m from Menu m where m.courseType = :courseType")
    List<Menu> findByCourseType(@Param("courseType") CourseType type);

    @Query("select m from Menu m where m.availability = 'AVAILABLE'")
    List<Menu> findByAvailability(@Param("availability") Availability availability);

    boolean existsByMenuName(String menuName);
}
