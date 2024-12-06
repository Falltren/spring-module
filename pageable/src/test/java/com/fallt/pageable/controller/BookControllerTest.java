package com.fallt.pageable.controller;

import com.fallt.pageable.domain.dto.request.UpsertBookRequest;
import com.fallt.pageable.domain.dto.response.BookResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Stream;

import static com.fallt.pageable.TestConstant.BOOK_PATH;
import static com.fallt.pageable.TestConstant.BOOK_PATH_WITH_ID;
import static com.fallt.pageable.TestConstant.FIFTH_BOOK_RESPONSE;
import static com.fallt.pageable.TestConstant.FIRST_BOOK_RESPONSE;
import static com.fallt.pageable.TestConstant.FOURTH_BOOK_RESPONSE;
import static com.fallt.pageable.TestConstant.SECOND_BOOK_RESPONSE;
import static com.fallt.pageable.TestConstant.THIRD_BOOK_RESPONSE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

    @Test
    @DisplayName("Create book")
    void whenCreateBook_thenReturnSuccessResponse() throws Exception {
        UpsertBookRequest request = createRequest();

        mockMvc.perform(post(BOOK_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Update book")
    void whenUpdateBook_thenReturnSuccessResponse() throws Exception {
        UpsertBookRequest request = createRequest();
        Long id = 100L;

        mockMvc.perform(put(BOOK_PATH_WITH_ID, id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Update book by incorrect id")
    void whenUpdateBookByIncorrectId_thenReturnNotFound() throws Exception{
        UpsertBookRequest request = createRequest();
        Long id = 500L;

        mockMvc.perform(put(BOOK_PATH_WITH_ID, id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Book with ID: " + id + " not found"));
    }

    @Test
    @DisplayName("Get book")
    void whenGetById_thenReturnBookFromDatabase() throws Exception {
        Long id = 101L;

        mockMvc.perform(get(BOOK_PATH_WITH_ID, id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Title2"))
                .andExpect(jsonPath("$.genre").value("THRILLER"))
                .andExpect(jsonPath("$.publication_date").value("1992-06-15"));
    }

    @Test
    @DisplayName("Get book by incorrect id")
    void whenGetByIncorrectId_thenReturnNotFound() throws Exception{
        Long id = 500L;

        mockMvc.perform(get(BOOK_PATH_WITH_ID, id))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Book with ID: " + id + " not found"));
    }

    @ParameterizedTest
    @DisplayName("Get all books with parameters")
    @MethodSource("providedOffsetWithLimitAndExpectedResponse")
    void whenGetAllByDefault_thenReturnListBooksWith5Items(Integer offset, Integer limit, List<BookResponse> expected) throws Exception {

        mockMvc.perform(get(BOOK_PATH + "?offset=" + offset + "&limit=" + limit))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(expected)));
    }

    private static Stream<Arguments> providedOffsetWithLimitAndExpectedResponse() {
        return Stream.of(
                Arguments.of(0, 3, List.of(FIRST_BOOK_RESPONSE, SECOND_BOOK_RESPONSE, THIRD_BOOK_RESPONSE)),
                Arguments.of(1, 3, List.of(FOURTH_BOOK_RESPONSE, FIFTH_BOOK_RESPONSE)),
                Arguments.of(2, 2, List.of(FIFTH_BOOK_RESPONSE)),
                Arguments.of(5, 1, List.of()),
                Arguments.of(3, 1, List.of(FOURTH_BOOK_RESPONSE))
        );
    }

    @Test
    @DisplayName("Get all books without parameters")
    void whenGetAllWithoutParameters_thenReturnListBooks() throws Exception {
        List<BookResponse> expected = List.of(
                FIRST_BOOK_RESPONSE, SECOND_BOOK_RESPONSE, THIRD_BOOK_RESPONSE, FOURTH_BOOK_RESPONSE, FIFTH_BOOK_RESPONSE
        );

        mockMvc.perform(get(BOOK_PATH))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(expected)));
    }

    private UpsertBookRequest createRequest() {
        return UpsertBookRequest.builder()
                .title("New book")
                .genre("ROMANCE")
                .publicationDate("2001-08-19")
                .authorId(100L)
                .build();
    }
}
