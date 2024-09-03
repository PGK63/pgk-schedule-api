package ru.pgk.security_service.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity(name = "admin_types")
public class AdminTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    @Enumerated(EnumType.STRING)
    private Type type;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "type", fetch = FetchType.LAZY)
    private Collection<AdminEntity> admins;

    public enum Type {
        FULL,
        EDUCATION_PART
    }
}
