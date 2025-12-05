package com.alysrazor.library.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "token", catalog = "library_auth")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString()
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Token {
    public enum TokenType {
        BEARER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(unique = true)
    private String token;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private TokenType tokenType = TokenType.BEARER;

    private boolean revoked;

    private boolean expired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    @JsonBackReference("user-token")
    private User user;
}
