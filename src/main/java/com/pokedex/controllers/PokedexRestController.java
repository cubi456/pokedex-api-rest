package com.pokedex.controllers;


import java.util.List;

import com.pokedex.data.Pokemon;
import com.pokedex.services.PokemonService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

//Defino el controlador REST
@RestController
@RequestMapping("/api")
public class PokedexRestController {
    
@Autowired
private PokemonService pokemonService;

/**
 * Este mapeo del metodo GET en la direccion /pokemons retorna
 * mediante un JSON toda la colección de pokemons contenida en
 * la base de datos.
 * @return Un JSON con todos los pokemons en la base de datos.
 */
@GetMapping(
    value="/pokemons",
	produces = { MimeTypeUtils.APPLICATION_JSON_VALUE },
	headers = "Accept=application/json")
public List<Pokemon> getPokemons() {
   return pokemonService.listarPokemons();
}

/**
 * Este mapeo del metodo POST en la direccion /updatePokemon 
 * actualiza el pokemon recibido como parametro dentro de la
 * base de datos y si no existe lo crea.
 * @param pokemon Este parametro indica el pokemon a actualizar.
 */
@PostMapping(
    value="/updatePokemon",
	produces = { MimeTypeUtils.APPLICATION_JSON_VALUE },
	headers = "Accept=application/json")
public void addPokemon(@RequestBody Pokemon pokemon)
{
    pokemonService.guardar(pokemon);
}

/**
 * Este mapeo del metodo GET en la direccion /deletePokemon
 * elimina el pokemon con el nombre {poke_name} recibido en
 * forma de path variable de la BD.
 * @param poke_name Nombre del pokemon a eliminar en la BD.
 */
@GetMapping(
    value="/deletePokemon/{poke_name}",
	produces = { MimeTypeUtils.APPLICATION_JSON_VALUE },
	headers = "Accept=application/json")
public void deletePokemon(@PathVariable String poke_name) {
    pokemonService.eliminar(poke_name);
}

/**
 * Este mapeo del metodo GET en la direccion /getPokemon
 * recupera el pokemon con el nombre {poke_name} recibido 
 * en forma de path variable de la BD.
 * @param poke_name Nombre del pokemon a recuperar en la BD.
 * @return El pokemon recuperado de la base de datos.
 */
@GetMapping(
    value="/getPokemon/{poke_name}",
	produces = { MimeTypeUtils.APPLICATION_JSON_VALUE },
	headers = "Accept=application/json")
public Pokemon getPokemon(@PathVariable String poke_name) {
   return pokemonService.encontrarPokemon(poke_name);
}

}