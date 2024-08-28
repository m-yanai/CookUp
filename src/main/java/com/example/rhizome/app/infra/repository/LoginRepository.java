package com.example.rhizome.app.infra.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.rhizome.app.infra.entity.UserEntity;

@Repository
public interface LoginRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByMailAddressAndPassword(String mailAddress, String password);

    /**
     * メールアドレスで検索
     **/
    Optional<UserEntity> findByMailAddress(String mailAddress);

    /**
     * IDで検索
     **/
    Optional<UserEntity> findByIdUser(Integer idUser);

    /**
     * IDで検索
     **/
    Optional<UserEntity> findByIconUser(String iconUser);

}
