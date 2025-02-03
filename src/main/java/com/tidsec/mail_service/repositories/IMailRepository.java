package com.tidsec.mail_service.repositories;

import com.tidsec.mail_service.entities.Mail;
import com.tidsec.mail_service.model.IMailProcDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IMailRepository extends IGenericRepository<Mail, Long> {

    //JPQL Java Persistence Query Language
    @Query("FROM Mail m WHERE m.bill = :bill OR LOWER(m.observation) LIKE %:observation%")
    List<Mail> search(@Param("bill") String bill, @Param("observation") String observation);

    @Query("FROM Mail m WHERE m.dateCreate BETWEEN :date1 AND :date2")
    List<Mail> searchByDates(@Param("date1") LocalDateTime date1, @Param("date2") LocalDateTime date2);

    @Query(value = "select * from fn_list()", nativeQuery = true)
    List<Object[]> callProcedureOrFunctionNative();

    @Query(value = "select * from fn_list()", nativeQuery = true)
    List<IMailProcDTO> callProcedureOrFunctionProjection();
}
