package com.pokedex;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pokedex.controllers.PokedexRestController;
import com.pokedex.data.Evolution;
import com.pokedex.data.Pokemon;
import com.pokedex.data.Skill;
import com.pokedex.services.PokemonService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest(PokedexRestController.class)
public class ControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PokemonService service;

    @BeforeEach
    void setUp()
    {
        List<Pokemon> lista = new ArrayList<Pokemon>();
        Pokemon pokemon_1 = new Pokemon();
        Pokemon pokemon_2 = new Pokemon();
        pokemon_1.setName("prueba1");
        pokemon_1.setTypes("test");
        pokemon_1.setLevel(1);
        pokemon_1.setEvolutions(new HashSet<Evolution>());
        pokemon_1.setSkills(new HashSet<Skill>());
        pokemon_2.setName("prueba2");
        lista.add(pokemon_1);
        lista.add(pokemon_2);

        when(service.listarPokemons()).thenReturn(lista);
        when(service.encontrarPokemon("prueba1")).thenReturn(pokemon_1);
        doNothing().when(service).eliminar("prueba1");
        doNothing().when(service).guardar(pokemon_1);

    }

    @Test
    void getPokemonsTest() throws Exception
    {
        String json = this.mockMvc.perform(get("/api/pokemons")).andExpect(status().isOk())
                                                                .andExpect(content().contentType("application/json"))
                                                                .andReturn().getResponse().getContentAsString();
        List<Pokemon> pokemons = new ObjectMapper().readValue(json, new TypeReference<List<Pokemon>>(){});
        assertEquals("prueba1", pokemons.get(0).getName());
        assertEquals("prueba2", pokemons.get(1).getName());
    }

    @Test
    void addPokemonTest() throws Exception
    {
        String json = "{\"name\":\"prueba1\",\"types\":\"test\",\"level\":1,\"skills\":[],\"evolutions\":[]}";
        this.mockMvc.perform(post("/api/updatePokemon").accept("application/json")
                                                       .content(json)
                                                       .contentType("application/json"))
                                                  .andExpect(status().isOk());
    }

    @Test
    void deletePokemonTest()throws Exception
    {
        this.mockMvc.perform(get("/api/deletePokemon/prueba1")).andExpect(status().isOk());
    }

    @Test
    void getPokemonTest() throws Exception
    {
        String json = this.mockMvc.perform(get("/api/getPokemon/prueba1")).andExpect(status().isOk())
                                                                          .andExpect(content().contentType("application/json"))
                                                                          .andReturn().getResponse().getContentAsString();
        Pokemon prueba = new ObjectMapper().readValue(json, Pokemon.class);
        assertEquals("prueba1", prueba.getName());
        assertEquals("test", prueba.getTypes());
        assertEquals(1, prueba.getLevel());                                                              
    }
}
