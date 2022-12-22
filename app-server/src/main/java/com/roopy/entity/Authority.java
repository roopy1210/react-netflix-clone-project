package com.roopy.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Table(name = "authority")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Authority {

    @Id
    @Column(name = "authority_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authorityId;

    @Column(name = "authority_name", length = 50)
    private String authorityName;
}