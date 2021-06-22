package com.pokedex.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity
@Table(name="evolutions")
public class Evolution{

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name="evol_name")
    protected String name;

    @Column(name="evol_types")
    protected String types;
    @Column(name="evol_level")
    protected int level;

}