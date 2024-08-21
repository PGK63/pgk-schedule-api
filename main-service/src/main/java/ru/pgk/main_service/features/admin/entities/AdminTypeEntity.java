package ru.pgk.main_service.features.admin.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Collection;

@Getter
@Setter
@Entity(name = "admin_types")
public class AdminTypeEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    @Enumerated(EnumType.STRING)
    private Type type;

    @OneToMany(mappedBy = "type", fetch = FetchType.LAZY)
    private Collection<AdminEntity> admins;

    public enum Type implements Serializable {
        FULL,
        EDUCATION_PART
    }
}
