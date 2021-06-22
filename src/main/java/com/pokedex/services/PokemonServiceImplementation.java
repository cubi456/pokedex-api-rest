package com.pokedex.services;

import java.util.List;

import com.pokedex.data.Pokemon;
import com.pokedex.repositories.pokemonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PokemonServiceImplementation implements PokemonService{

    @Autowired
    private pokemonRepository pokemons;

    @Transactional(readOnly = true)
    public List<Pokemon> listarPokemons() {
        return (List<Pokemon>) pokemons.findAll();
    }

    @Transactional
    public void guardar(Pokemon pokemon) {
        pokemons.save(pokemon);
    }

    @Transactional
    public void eliminar(String poke_name) {
        Pokemon aux = encontrarPokemon(poke_name);
        if(aux!=null)
            pokemons.delete(aux);
        
    }

    @Transactional(readOnly = true)
    public Pokemon encontrarPokemon(String poke_name) {
        return pokemons.findById(poke_name).orElse(null);
    }

    
}
