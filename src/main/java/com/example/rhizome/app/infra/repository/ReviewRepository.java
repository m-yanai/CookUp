package com.example.rhizome.app.infra.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.rhizome.app.infra.entity.ReviewEntity;
@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Integer>{
List<ReviewEntity> findByIdRecipe(Integer idRecipe);
}
