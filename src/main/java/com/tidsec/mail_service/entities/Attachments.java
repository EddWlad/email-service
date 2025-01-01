package com.tidsec.mail_service.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name= "attachments")
public class Attachments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JoinColumn(name = "mail_id", nullable = false)
    private Mail mail;

    @Column(name = "route_attachment", nullable = false)
    private String routeAttachment;

    @Column(nullable = false)
    private Integer status = 1;
}
