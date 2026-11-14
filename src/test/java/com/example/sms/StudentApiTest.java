package com.example.sms;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class StudentApiTest {

    @Autowired
    MockMvc mvc;

    @Test
    void listsSeededStudents() throws Exception {
        mvc.perform(get("/api/students")).andExpect(status().isOk());
    }

    @Test
    void rejectsInvalidStudent() throws Exception {
        mvc.perform(post("/api/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\":\"\",\"email\":\"bad\"}"))
           .andExpect(status().isBadRequest());
    }

    @Test
    void returns404ForMissingStudent() throws Exception {
        mvc.perform(get("/api/students/9999")).andExpect(status().isNotFound());
    }
}
