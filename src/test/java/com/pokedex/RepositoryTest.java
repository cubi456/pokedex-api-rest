package com.pokedex;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import com.pokedex.data.Evolution;
import com.pokedex.data.Pokemon;
import com.pokedex.data.Skill;
import com.pokedex.repositories.pokemonRepository;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class RepositoryTest {

    @Autowired
    private pokemonRepository repository;

    @Test
    void findAllTest()
    {
        //Recupero todos los pokemons de la base de datos.
        List<Pokemon> pokemons = Lists.newArrayList(repository.findAll());
        //Verifico que se encuentren todos los pokemons.
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
    void findByIdTest()
    {
        //Recupero un pokemon de la base de datos
        Pokemon pokemon = repository.findById("Charmander").orElse(null);

        //Verifico que efectivamente sea el pokemon buscado
        assertNotNull(pokemon);

        //Controlo sus datos basicos.
        assertEquals("Charmander", pokemon.getName());
        assertEquals("Fire", pokemon.getTypes());
        assertEquals(8, pokemon.getLevel());

        //Controlo sus habilidades
        List<Skill> skills = Lists.newArrayList(pokemon.getSkills());
        assertEquals(1, skills.size());
        assertEquals("Fire Blast",skills.get(0).getName());
        assertEquals("Fire",skills.get(0).getType());      

        //Controlo sus evoluciones
        List<Evolution> evolutions = Lists.newArrayList(pokemon.getEvolutions());
        assertEquals(2, evolutions.size());
        //Controlo la la primer evolucion
        assertEquals("Charmeleon", evolutions.get(0).getName());
        assertEquals("Fire", evolutions.get(0).getTypes());
        assertEquals(12, evolutions.get(0).getLevel());
        //Controlo la segunda evolucion
        assertEquals("Charizard", evolutions.get(1).getName());
        assertEquals("Fire - Flying", evolutions.get(1).getTypes());
        assertEquals(40, evolutions.get(1).getLevel());
    }

    @Test
    void saveTest()
    {
        //Genero un nuevo pokemon
        Pokemon newPokemon =  new Pokemon();
        newPokemon.setName("test");
        newPokemon.setTypes("test");
        newPokemon.setLevel(0);
        //Guardo el pokemon en la base de datos
        repository.save(newPokemon);

        //Recupero el pokemon
        Pokemon aux = repository.findById("test").orElse(null);
        assertNotNull(aux);
        assertEquals(newPokemon.getName(), aux.getName());
        assertEquals(newPokemon.getTypes(), aux.getTypes());
        assertEquals(newPokemon.getLevel(), aux.getLevel());
    }

    @Test
    void updateTest()
    {
         //Genero un nuevo pokemon
         Pokemon newPokemon =  new Pokemon();
         newPokemon.setName("Charmander");
         newPokemon.setTypes("agua");
         newPokemon.setLevel(0);

         //Guardo el pokemon
         repository.save(newPokemon);

         //Recupero el pokemon
         Pokemon aux = repository.findById("Charmander").orElse(null);
         assertEquals("agua",aux.getTypes());
         assertEquals(0, aux.getLevel());
         assertNull(aux.getSkills());
         assertNull(aux.getEvolutions());
    }

    @Test
    void deleteTest()
    {
        //Genero un nuevo pokemon
        Pokemon newPokemon =  new Pokemon();
        newPokemon.setName("test");
        newPokemon.setTypes("test");
        newPokemon.setLevel(0);
        //Guardo el pokemon en la base de datos
        repository.save(newPokemon);

        //Recupero el pokemon
        Pokemon aux = repository.findById("test").orElse(null);
        //Me aseguro que exista
        assertNotNull(aux);

        //Lo elimino 
        repository.delete(newPokemon);

         //Recupero el pokemon
         Pokemon deletedPokemon = repository.findById("test").orElse(null);
         //Me aseguro de que no exista el pokemon
         assertNull(deletedPokemon);
    }

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
