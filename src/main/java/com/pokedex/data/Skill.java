package com.pokedex.data;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="skills")
public class Skill {

    @Id
    @Column(name="skill_name")
    protected String name;

    @Column(name="skill_type")
    protected String type;

}