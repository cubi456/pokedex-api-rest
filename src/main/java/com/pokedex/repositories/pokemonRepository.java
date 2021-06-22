package com.pokedex.repositories;

import com.pokedex.data.Pokemon;

import org.springframework.data.repository.CrudRepository;


public interface pokemonRepository extends CrudRepository<Pokemon,String>
{
    
}
