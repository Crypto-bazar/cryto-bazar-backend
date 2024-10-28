package com.ororura.cryptobazar.entities.product;

import com.ororura.cryptobazar.entities.user.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "product_entity")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id", nullable = false)
    private UserEntity ownerId;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @ColumnDefault("0")
    @Column(name = "amount")
    private Integer amount;

}