#----------------------------
#Creacion de la base de datos
#----------------------------

CREATE DATABASE pokedex;

USE pokedex;

#-------------------------------
#Creacion de tablas de entidades
#-------------------------------

CREATE TABLE pokemons(
    pok_name  varchar(45) NOT NULL,
    pok_types   TEXT NOT NULL, 
    pok_level   INTEGER NOT NULL,

    CONSTRAINT pk_pokemons PRIMARY KEY(pok_name)
);


CREATE TABLE evolutions(
    evol_name  varchar(45) NOT NULL,
    evol_types   TEXT NOT NULL, 
    evol_level   INTEGER NOT NULL,

    CONSTRAINT pk_evolutions PRIMARY KEY(evol_name)
);

CREATE TABLE skills(
    skill_name  varchar(45) NOT NULL,
    skill_type    varchar(45) NOT NULL, 

    CONSTRAINT pk_skills PRIMARY KEY(skill_name)
);

#--------------------------------
#Creacion de tablas de relaciones
#--------------------------------

CREATE TABLE evolve(
    pok_name   varchar(45) NOT NULL,
    evol_name  varchar(45) NOT NULL,

    CONSTRAINT pk_evolve PRIMARY KEY(pok_name,evol_name),
    CONSTRAINT fk_evolve_pokemon FOREIGN KEY(pok_name) REFERENCES pokemons(pok_name) ON UPDATE CASCADE,
    CONSTRAINT fk_evolve_evolution FOREIGN KEY(evol_name) REFERENCES evolutions(evol_name)
);

CREATE TABLE pok_skill(
    pok_name   varchar(45) NOT NULL,
    skill_name  varchar(45) NOT NULL,

    CONSTRAINT pk_pok_skill PRIMARY KEY(pok_name,skill_name),
    CONSTRAINT fk_pok_skill_pokemon FOREIGN KEY(pok_name) REFERENCES pokemons(pok_name) ON UPDATE CASCADE,
    CONSTRAINT fk_pok_skill_skills FOREIGN KEY(skill_name) REFERENCES skills(skill_name)
);
#-------------------------
#---- Datos de prueba ----
#-------------------------

#Pokemons

INSERT INTO pokemons VALUES("Bulbasaur","Grass - Poison",12);
INSERT INTO pokemons VALUES("Charmander","Fire",8);
INSERT INTO pokemons VALUES("Squirtle","Water",28);
INSERT INTO pokemons VALUES("Metapod","Bug",6);
INSERT INTO pokemons VALUES("Butterfree","Bug - Flying",1);
INSERT INTO pokemons VALUES("Pidgey","Normal - Flying",6);
INSERT INTO pokemons VALUES("Rattata","Normal",13);
INSERT INTO pokemons VALUES("Ekans","Poison",9);
INSERT INTO pokemons VALUES("Pikachu","Electric",16);
INSERT INTO pokemons VALUES("Dratini","Dragon",30);


#Evolutions
INSERT INTO evolutions VALUES("Dragonair","Dragon",36);
INSERT INTO evolutions VALUES("Dragonite","Dragon - Flying",42);
INSERT INTO evolutions VALUES("Raichu","Electric",36);
INSERT INTO evolutions VALUES("Charmeleon","Fire",12);
INSERT INTO evolutions VALUES("Charizard","Fire - Flying",40);

#skills
INSERT INTO skills VALUES("Fire Blast","Fire");
INSERT INTO skills VALUES("Hydro Pump","Water");
INSERT INTO skills VALUES("Wing Attack","Flying");
INSERT INTO skills VALUES("Tackle","Normal");
INSERT INTO skills VALUES("Harden","Normal");

#Pokemons - Evolutions
INSERT INTO evolve VALUES("Dratini","Dragonair");
INSERT INTO evolve VALUES("Dratini","Dragonite");
INSERT INTO evolve VALUES("Pikachu","Raichu");
INSERT INTO evolve VALUES("Charmander","Charmeleon");
INSERT INTO evolve VALUES("Charmander","Charizard");

#Pokemons - skills
INSERT INTO pok_skill VALUES("Charmander","Fire Blast");
INSERT INTO pok_skill VALUES("Rattata","Tackle");
INSERT INTO pok_skill VALUES("Squirtle","Hydro Pump");
INSERT INTO pok_skill VALUES("Pidgey","Wing Attack");
INSERT INTO pok_skill VALUES("Metapod","Harden");

#--------------------------------------------------
#Creacion de usuarios y otorgamiento de privilegios
#--------------------------------------------------

CREATE USER 'pokedex_user'@'%' IDENTIFIED BY 'pokemon123'; 
GRANT ALL PRIVILEGES ON pokedex.* TO 'pokedex_user'@'%';
