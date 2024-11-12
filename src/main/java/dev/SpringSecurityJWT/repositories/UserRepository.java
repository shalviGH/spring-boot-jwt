package dev.SpringSecurityJWT.repositories;

import dev.SpringSecurityJWT.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    //Optional<UserEntity> findByUserName(String username);

    @Query("SELECT u FROM UserEntity u WHERE u.user_name = ?1")
    Optional<UserEntity> getName(String username);
}
