package com.pokedex;

import java.util.ArrayList;
import java.util.List;

import com.pokedex.data.Evolution;
import com.pokedex.data.Pokemon;
import com.pokedex.data.Skill;
import com.pokedex.services.PokemonService;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PokedexApplicationTests {

	@Autowired
	private PokemonService servicio;

	@Test
	/**
	 * Test para recuperar todos los pokemons de la base de datos.
	 */
	void listAllPokemonTest() {
		List<Pokemon> pokemons = servicio.listarPokemons();
		//Controlo que esten los 10 pokemons de prueba almacenados en la base de datos
		assertEquals(true,searchPokemon("Bulbasaur",pokemons));
		assertEquals(true,searchPokemon("Charmander",pokemons));
		assertEquals(true,searchPokemon("Squirtle",pokemons));
		assertEquals(true,searchPokemon("Metapod",pokemons));
		assertEquals(true,searchPokemon("Butterfree",pokemons));
		assertEquals(true,searchPokemon("Pidgey",pokemons));
		assertEquals(true,searchPokemon("Rattata",pokemons));
		assertEquals(true,searchPokemon("Ekans",pokemons));
		assertEquals(true,searchPokemon("Pikachu",pokemons));
		assertEquals(true,searchPokemon("Dratini",pokemons));	
	}
	@Test
	/**
	 * Test para recuperar los pokemons de una base de datos.
	 */
	void checkPokemonInfo(){
		//Recupero el pokemon de la base de datos.
		Pokemon pokemon = servicio.encontrarPokemon("Charmander");
		//Controlo que todos los datos del pokemon sean correctos.
		assertEquals("Charmander",pokemon.getName());
		assertEquals("Fire",pokemon.getTypes());
		assertEquals(8,pokemon.getLevel());
	}
	
	@Test
	/**
	 * Test para recuperar las habilidades y evoluciones de un pokemon.
	 */
	void checkPokemonSkillsAndEvolutions(){
		//Recupero el pokemon
		Pokemon pokemon = servicio.encontrarPokemon("Charmander");
		//Recupero las evoluciones y habilidades de un pokemon.
		List<Skill> Skills = new ArrayList<Skill>();
		Skills.addAll(pokemon.getSkills());
		List<Evolution> evolutions = new ArrayList<Evolution>();
		evolutions.addAll(pokemon.getEvolutions());
		//Controlo que todas las habilidades sean correctas.
		assertEquals(1,Skills.size());
		assertEquals("Fire Blast",Skills.get(0).getName());
		//Controlo que todas las evoluciones sean correctas.
		assertEquals(2,evolutions.size());
		//Controlo una de las evoluciones
		assertEquals("Charizard",evolutions.get(1).getName());
		assertEquals("Fire - Flying",evolutions.get(1).getTypes());
		assertEquals(40,evolutions.get(1).getLevel());
		//Controlo la otra evolucion
		assertEquals("Charmeleon",evolutions.get(0).getName());
		assertEquals("Fire",evolutions.get(0).getTypes());
		assertEquals(12,evolutions.get(0).getLevel());
	}
	
	
	@Test
	/**
	 * Test para insertar un nuevo pokemon en la pokedex.
	 */
	void addPokemonTest(){
		//Creo los datos del pokemon.
		String types = "test - test2";
		//Inserto el nuevo pokemon en la base de datos.
		Pokemon savePokemon = new Pokemon();
		savePokemon.setName("testPokemon");
		savePokemon.setTypes(types);
		savePokemon.setLevel(10);
		servicio.guardar(savePokemon);
		//Recupero el pokemon de la base de datos.
		Pokemon auxiliar = servicio.encontrarPokemon("testPokemon");
		//Chequeo que la informacion utilizada anteriormente este correcta.
		assertEquals("testPokemon",auxiliar.getName());
		assertEquals(types,auxiliar.getTypes());
		assertEquals(10,auxiliar.getLevel());
		
		//Elimino el pokemon ingresado en la base de datos.
		servicio.eliminar("testPokemon");
	}
	
	@Test
	/**
	 * Test para actualizar la informacion de un pokemon en la base de datos.
	 */
	void updatePokemonTest() {
		//Recupero el pokemon a editar.
		Pokemon backup = servicio.encontrarPokemon("Charmander");
		//Creo los nuevos datos para editar el pokemon.
		Pokemon nuevaEntrada = new Pokemon();
		
		nuevaEntrada.setName(backup.getName());
		nuevaEntrada.setTypes("test3 - test4");
		nuevaEntrada.setLevel(40);
		nuevaEntrada.setSkills(backup.getSkills());
		nuevaEntrada.setEvolutions(backup.getEvolutions());
		
		//Edito el pokemon en la base de datos.
		servicio.guardar(nuevaEntrada);
		//Recupero el pokemon editado de la base de datos.
		Pokemon auxiliar = servicio.encontrarPokemon(backup.getName());
		//Controlo que la informacion este correcta.
		assertEquals(backup.getName(),auxiliar.getName());
		assertEquals("test3 - test4",auxiliar.getTypes());
		assertEquals(40,auxiliar.getLevel());

		servicio.guardar(backup);
	}

	/**
	 * Este metodo busca un pokemon en una lista.
	 * @param name Este parametro indica el nombre del pokemon buscado.
	 * @param pokemons Este parametro indica la coleccion de pokemons.
	 * @return Si el pokemon se encuentra en la coleccion de pokemons.
	 */
	private boolean searchPokemon(String name, List<Pokemon>pokemons)
	{
		boolean find = false;
		int i = 0;
		while(!find && i<pokemons.size())
			if(pokemons.get(i).getName().equals(name))
				find = true;
			else 
				i++;
		return find;
	}

}
