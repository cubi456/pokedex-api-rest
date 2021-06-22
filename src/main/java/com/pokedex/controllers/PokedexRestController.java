package com.pokedex.controllers;


import java.util.List;

import com.pokedex.data.Pokemon;
import com.pokedex.services.PokemonService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class PokedexRestController {
    
@Autowired
private PokemonService pokemonService;

@GetMapping(
    value="/pokemons",
	produces = { MimeTypeUtils.APPLICATION_JSON_VALUE },
	headers = "Accept=application/json")
public List<Pokemon> getPokemons() {
   return pokemonService.listarPokemons();
}


@PostMapping(
    value="/updatePokemon",
	produces = { MimeTypeUtils.APPLICATION_JSON_VALUE },
	headers = "Accept=application/json")
public void addPokemon(@RequestBody Pokemon pokemon)
{
    pokemonService.guardar(pokemon);
}

@GetMapping(
    value="/deletepokemon/{poke_name}",
	produces = { MimeTypeUtils.APPLICATION_JSON_VALUE },
	headers = "Accept=application/json")
public void deletePokemon(@PathVariable String poke_name) {
    pokemonService.eliminar(poke_name);
}

@GetMapping(
    value="/getPokemon/{poke_name}",
	produces = { MimeTypeUtils.APPLICATION_JSON_VALUE },
	headers = "Accept=application/json")
public Pokemon getPokemon(@PathVariable String poke_name) {
   return pokemonService.encontrarPokemon(poke_name);
}

}