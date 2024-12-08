package com.fallt.spring_data_jdbc.controller;

import com.fallt.spring_data_jdbc.domain.dto.response.BookResponse;
import com.fallt.spring_data_jdbc.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static com.fallt.spring_data_jdbc.TestConstant.COMMON_BOOK_PATH;
import static com.fallt.spring_data_jdbc.TestConstant.FIRST_BOOK_RESPONSE;
import static com.fallt.spring_data_jdbc.TestConstant.ID_PATH;
import static com.fallt.spring_data_jdbc.TestConstant.REQUEST;
import static com.fallt.spring_data_jdbc.TestConstant.SECOND_BOOK_RESPONSE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Sql(scripts = "/sql/before-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/after-test.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("Get by id")
    void whenGetById_thenReturnBookResponse() throws Exception {
        Long id = 100L;

        mockMvc.perform(get(ID_PATH, id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(FIRST_BOOK_RESPONSE)));
    }

    @Test
    @DisplayName("Get by incorrect id")
    void whenGetByIncorrectId_thenThrowNotFound() throws Exception {
        Long id = 500L;

        mockMvc.perform(get(ID_PATH, id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Get all")
    void whenGetAll_thenReturnListBookResponse() throws Exception {
        List<BookResponse> expected = List.of(
                FIRST_BOOK_RESPONSE, SECOND_BOOK_RESPONSE
        );

        mockMvc.perform(get(COMMON_BOOK_PATH))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(expected)));
    }

    @Test
    @DisplayName("Create book")
    void whenCreate_thenBookSaveInDatabase() throws Exception {
        mockMvc.perform(post(COMMON_BOOK_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(REQUEST)))
                .andDo(print())
                .andExpect(status().isCreated());
        int count = bookRepository.findAll().size();
        assertThat(count).isEqualTo(3);
    }

    @Test
    @DisplayName("Update book")
    void whenUpdate_thenUpdateBookInDatabase() throws Exception {
        Long id = 100L;

        mockMvc.perform(put(ID_PATH, id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(REQUEST)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Update by incorrect id")
    void whenUpdateByIncorrectId_thenThrowNotFound() throws Exception {
        Long id = 500L;

        mockMvc.perform(put(ID_PATH, id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(REQUEST)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Delete book")
    void whenDelete_thenRemoveBookFromDatabase() throws Exception{
        Long id = 100L;

        mockMvc.perform(delete(ID_PATH, id))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
