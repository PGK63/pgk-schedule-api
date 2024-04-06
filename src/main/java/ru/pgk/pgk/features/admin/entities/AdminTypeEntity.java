package ru.pgk.pgk.features.admin.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Collection;

@Data
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
