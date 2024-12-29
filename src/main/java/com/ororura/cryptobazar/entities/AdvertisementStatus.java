package com.ororura.cryptobazar.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "advertisement_status")
public class AdvertisementStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "advertisement_status_id_gen")
    @SequenceGenerator(name = "advertisement_status_id_gen", sequenceName = "advertisement_status_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @ColumnDefault("now()")
    @Column(name = "created_at")
    private Instant createdAt;

}