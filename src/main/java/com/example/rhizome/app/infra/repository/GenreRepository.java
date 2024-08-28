package com.example.rhizome.app.infra.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.rhizome.app.infra.entity.GenreEntity;

@Repository
public interface GenreRepository extends JpaRepository<GenreEntity, Integer>, JpaSpecificationExecutor<GenreEntity> {

    static Specification<GenreEntity> idGenreEqual(Integer idGenre) {
        return idGenre == null ?
                null :
                (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("idGenre"), idGenre);
    }

    static Specification<GenreEntity> nmGenreEqual(String nmGenre) {
        return (nmGenre == null || nmGenre.isEmpty()) ?
                null :
                (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("nmGenre"), nmGenre);
    }

    static Specification<GenreEntity> nmGenreLike(String nmGenre) {
        return (nmGenre == null || nmGenre.isEmpty()) ?
                null :
                (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("nmGenre"), "%" + nmGenre + "%");
    }

}
