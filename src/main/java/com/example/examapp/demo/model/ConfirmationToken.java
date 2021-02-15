package com.example.examapp.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "CONFIRMATION_TOKEN")
@Getter
@Setter
@NoArgsConstructor
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tokenId")
    private long id;

    @Column(name = "Token", nullable = false)
    private String token;

    @Column(name = "ExpiresAt")
    private LocalDateTime expiresAt;

    @Column(name = "CreatedAt")
    private LocalDateTime createdAt;

    @Column(name = "ConfirmedAt")
    private LocalDateTime confirmedAt;

    @ManyToOne
    @JoinColumn(name = "ExamId")
    private Exam exam;

    public ConfirmationToken(String token, LocalDateTime expiresAt,
                             LocalDateTime createdAt,
                             LocalDateTime confirmedAt, Exam exam) {
        this.token = token;
        this.expiresAt = expiresAt;
        this.createdAt = createdAt;
        this.confirmedAt = confirmedAt;
        this.exam = exam;
    }
}
