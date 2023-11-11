package com.academy.onlinestore.api.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsernameAndIsDeletedFalseAndIsVerifiedTrue(String username);

//    JPQL = Jakarta or Java Persistent Query Language
//    JPQL is Spring data feature for DAO implement with SQL-concept
//    JPQL is base on Entity(Java class) not table in database
//    There are 2 ways for binding parameter:
//    ':' Named Binding Parameter
//    '?' Positional Binding Parameter
    // @Query("SELECT u FROM User u WHERE u.uuid = ?1")
    @Query("SELECT u FROM User AS u WHERE u.uuid = :uuid")
    Optional<User> selectUserByUUID(String uuid);

    /**
     * This method used to select users with both isDeleted status(True/False) pass by parameter
     * To write native query we need to set (nativeQuery = true)
     * @param uuid
     * @param isDeleted
     * @return User Entity
     */
    @Query(value = "SELECT * FROM users WHERE uuid = ?1 AND is_deleted = ?2", nativeQuery = true)
    Optional<User> selectUserByUuidAndIsDeleted(String uuid, Boolean isDeleted);

    @Query("SELECT EXISTS (SELECT u FROM User AS u WHERE u.uuid = :uuid)")
    Boolean checkUserByUUID(String uuid);

    @Modifying
    @Query("UPDATE User AS u SET u.isDeleted = :isDeleted WHERE u.uuid = :uuid")
    void updateIsDeletedStatusByUUID(String uuid, Boolean isDeleted);

    // Derived Query Method
    Boolean existsByUsernameAndIsDeletedFalse(String username);
    Boolean existsByEmailAndIsDeletedFalse(String email);
}
