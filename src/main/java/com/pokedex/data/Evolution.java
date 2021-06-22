package com.pokedex.data;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;


import lombok.Data;

@Data
@Entity
@Table(name="evolutions")
public class Evolution{

    @Id
    @Column(name="evol_name",unique = true)
    protected String name;

    @Column(name="evol_types",nullable = false)
    protected String types;
    @Column(name="evol_level")
    protected int level;
    
}