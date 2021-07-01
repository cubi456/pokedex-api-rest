package com.pokedex;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.pokedex.data.Evolution;
import com.pokedex.data.Pokemon;
import com.pokedex.data.Skill;
import com.pokedex.repositories.pokemonRepository;
import com.pokedex.services.PokemonService;


@SpringBootTest
public class ServiceTest {

    @MockBean
    private pokemonRepository pokemons;

    @Autowired
	private PokemonService service;

    @BeforeEach
    void setUp()
    {
        setUpMockPokemon();
    }
    
    private void setUpMockPokemon() {
        Pokemon mockPokemon = new Pokemon();
        mockPokemon.setName("Charmander");
        mockPokemon.setTypes("Fire");
        mockPokemon.setLevel(8);

        Set<Skill> skills = new HashSet<Skill>();
        Skill auxiliar = new Skill();
        auxiliar.setName("Fire Blast");
        auxiliar.setType("Fire");
        skills.add(auxiliar);
        mockPokemon.setSkills(skills);

        Set<Evolution> evolutions = new HashSet<Evolution>();
        Evolution charmeleon = new Evolution();
        Evolution charizard = new Evolution();
        charmeleon.setName("Charmeleon");
        charmeleon.setTypes("Fire");
        charmeleon.setLevel(12);

        charizard.setName("Charizard");
        charizard.setTypes("Fire - Flying");
        charizard.setLevel(40);

        evolutions.add(charmeleon);
        evolutions.add(charizard);

        mockPokemon.setEvolutions(evolutions);

		Pokemon pokemon_1 = new Pokemon();
		Pokemon pokemon_2 = new Pokemon();
		pokemon_1.setName("prueba1");
		pokemon_2.setName("prueba2");

		List<Pokemon> pokemonsList = new ArrayList<Pokemon>();
		pokemonsList.add(mockPokemon);
		pokemonsList.add(pokemon_1);
		pokemonsList.add(pokemon_2);

        when(pokemons.findById("Charmander")).thenReturn(Optional.ofNullable(mockPokemon));
		when(pokemons.findAll()).thenReturn(pokemonsList);
		when(pokemons.save(pokemon_1)).thenReturn(pokemon_1);
		doNothing().when(pokemons).delete(mockPokemon);
    }

	@Test
	void listarPokemonTest()
	{
		List<Pokemon> pokemonList = service.listarPokemons();
		assertEquals("Charmander",pokemonList.get(0).getName());
		assertEquals("prueba1",pokemonList.get(1).getName());
		assertEquals("prueba2",pokemonList.get(2).getName());
	}

	@Test
	/**
	 * Test para recuperar los pokemons de una base de datos.
	 */
	void checkPokemonInfo(){
		//Recupero el pokemon de la base de datos.
		Pokemon pokemon = service.encontrarPokemon("Charmander");
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
		Pokemon pokemon = service.encontrarPokemon("Charmander");
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
	void guardarPokemonTest()
	{
		Pokemon pokemon_1 = new Pokemon();
		pokemon_1.setName("prueba1");
		service.guardar(pokemon_1);
	}

	@Test
	void eliminarPokemonTest()
	{
		service.eliminar("Charmander");
	}
}
