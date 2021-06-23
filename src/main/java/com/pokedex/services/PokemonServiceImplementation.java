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
    
    /**
    * Este metodo recupera todos los pokemons de la base de datos
    * y los retorna en forma de lista. 
    * @return Una lista que contiene todos los pokemons.
    */
    @Transactional(readOnly = true)
    public List<Pokemon> listarPokemons() {
        return (List<Pokemon>) pokemons.findAll();
    }

    /**
     * Este metodo actualiza o guarda el pokemon recibido
     * como parametro.
     * @param Pokemon Este parametro indica el pokemon a guardar o
     * actualizar dentro de la BD.
     */
    @Transactional
    public void guardar(Pokemon pokemon) {
        pokemons.save(pokemon);
    }
    /**
     * Este metodo elimina un pokemon de la base de datos.
     * @param String Nombre del pokemon a eliminar en la base
     * de datos.
     */
    @Transactional
    public void eliminar(String poke_name) {
        Pokemon aux = encontrarPokemon(poke_name);
        if(aux!=null)
            pokemons.delete(aux);
        
    }

    /**
     * Este metodo recupera un pokemon de la base de datos
     * @param String Pokemon a recuperar en la base de datos.
     * @return Pokemon encontrado en la base de datos o null en caso contrario.
     */
    @Transactional(readOnly = true)
    public Pokemon encontrarPokemon(String poke_name) {
        return pokemons.findById(poke_name).orElse(null);
    }

    
}
