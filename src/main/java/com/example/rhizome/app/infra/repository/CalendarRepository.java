package com.example.rhizome.app.infra.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.rhizome.app.infra.entity.CalendarEntity;

@Repository
public interface CalendarRepository extends JpaRepository<CalendarEntity, String> {

    List<CalendarEntity> findByIdUser(Integer idUser);

    // 他に必要なクエリメソッドを追加することも可能
}
