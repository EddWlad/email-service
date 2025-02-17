package com.tidsec.mail_service.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name= "mail")
public class Mail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private Long idRecipients;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JoinColumn(name = "mailingGroup_id", nullable = false, foreignKey = @ForeignKey(name = "FK_MAIL_MAILING-GROUP"))
    private MailingGroup mailingGroup;

    @NotBlank
    @Size(min = 3, max = 50)
    private String bill;

    @Column(nullable = false)
    private Integer priority = 1;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JoinColumn(name = "supplier_id", nullable = false, foreignKey = @ForeignKey(name = "FK_MAIL_SUPPLIER"))
    private Supplier supplier;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JoinColumn(name = "project_id", nullable = false, foreignKey = @ForeignKey(name = "FK_MAIL_PROJECT"))
    private Project project;

    /*@ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JoinColumn(name = "signature_id", nullable = false, foreignKey = @ForeignKey(name = "FK_MAIL_SIGNATURE"))
    private Signature signature;*/

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JoinColumn(name = "paymentAgreement_id", nullable = false, foreignKey = @ForeignKey(name = "FK_MAIL_PAYMENT-AGREEMENT"))
    private PaymentAgreement paymentAgreement;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false, foreignKey = @ForeignKey(name = "FK_CONSULT_USER"))
    private User user;

    @OneToMany (mappedBy = "mail", cascade=CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private List<Attachments> attachments = new ArrayList<Attachments>();


    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateCreate;

    @Column(nullable = true)
    private String observation;

    @Column(nullable = false)
    private Integer status = 1;



}
