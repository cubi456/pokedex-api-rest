package com.pokedex;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pokedex.controllers.PokedexRestController;
import com.pokedex.data.Pokemon;
import com.pokedex.services.PokemonService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
        pokemon_2.setName("prueba2");
        lista.add(pokemon_1);
        lista.add(pokemon_2);

        when(service.listarPokemons()).thenReturn(lista);
        doNothing().when(service).eliminar("test");

    }

    @Test
    void getPokemonsTest() throws Exception
    {
        String json = this.mockMvc.perform(get("/api/pokemons")).andDo(print()).andExpect(status().isOk())
                         .andExpect(content().contentType("application/json"))
                      .andReturn().getResponse().getContentAsString();
        List<Pokemon> pokemons = new ObjectMapper().readValue(json, new TypeReference<List<Pokemon>>(){});
        assertEquals("prueba1", pokemons.get(0).getName());
        assertEquals("prueba2", pokemons.get(1).getName());
    }

    @Test
    void addPokemonTest()
    {
        
    }
}
