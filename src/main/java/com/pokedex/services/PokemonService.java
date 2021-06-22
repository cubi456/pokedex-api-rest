package com.pokedex.services;

import com.pokedex.data.Pokemon;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PokemonService {

    public List<Pokemon> listarPokemons();

    public void guardar(Pokemon pokemon);

    public void eliminar(String poke_name);

    public Pokemon encontrarPokemon(String poke_name);

}
