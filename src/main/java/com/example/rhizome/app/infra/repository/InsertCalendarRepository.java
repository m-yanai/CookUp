package com.example.rhizome.app.infra.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.rhizome.app.infra.entity.CalendarEntity;
@Repository
public interface InsertCalendarRepository extends JpaRepository<CalendarEntity, Integer>{
	List<CalendarEntity>findAllByIdUser(Integer idUser);
}
