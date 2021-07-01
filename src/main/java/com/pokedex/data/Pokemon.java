package com.pokedex.data;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import lombok.Data;

@Data //Genera todos los setters y getters
@Entity //Especifico que es una entidad de la base de datos
@Table(name="pokemons")
public class Pokemon{

    @Id
    @Column(name="pok_name",unique = true,nullable = false)
    protected String name;

    @Column(name="pok_types",nullable = false)
    protected String types;

    @Column(name="pok_level")
    protected int level;
    
    @ManyToMany(cascade ={CascadeType.PERSIST,CascadeType.MERGE})
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(
        name="pok_skill",
        joinColumns = @JoinColumn(name = "pok_name"),
        inverseJoinColumns = @JoinColumn(name = "skill_name")
    )
    protected Set<Skill> skills;

    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(
        name = "evolve",
        joinColumns = @JoinColumn(name = "pok_name"),
        inverseJoinColumns = @JoinColumn(name = "evol_name")
    )
    protected Set<Evolution> evolutions;
}