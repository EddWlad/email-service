package com.tidsec.mail_service.repositories;

import com.tidsec.mail_service.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface IUserRepository extends IGenericRepository<User, Long> {

    //SELECT * FROM user u WHERE u.email = ?
    //@Query("FROM User u WHERE u.email = :email")
    //DerivedQueries
    User findOneByUsername(String username);
    @Transactional
    @Modifying
    @Query("UPDATE User us SET us.password = :password WHERE us.username = :username")
    void changePassword(@Param("username") String username, @Param("password") String newPassword);

}
