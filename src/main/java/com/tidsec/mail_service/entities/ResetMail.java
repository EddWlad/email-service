package com.tidsec.mail_service.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ResetMail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String random;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    private User user;

    @Column(nullable = false)
    private LocalDateTime expiration;

    public void setExpiration(int minutes){
        LocalDateTime now = LocalDateTime.now();
        expiration = now.plusMinutes(minutes);
    }

    public boolean isExpired(){
        return LocalDateTime.now().isAfter(expiration);
    }

    @Column(nullable = false)
    private Integer status = 1;
}
