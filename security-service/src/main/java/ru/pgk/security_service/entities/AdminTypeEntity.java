package ru.pgk.security_service.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@Entity(name = "admin_types")
public class AdminTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    @Enumerated(EnumType.STRING)
    private Type type;

    @OneToMany(mappedBy = "type", fetch = FetchType.LAZY)
    private Collection<AdminEntity> admins;

    public enum Type {
        FULL,
        EDUCATION_PART
    }
}
