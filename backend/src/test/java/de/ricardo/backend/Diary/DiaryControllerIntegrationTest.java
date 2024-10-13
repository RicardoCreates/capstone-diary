package de.ricardo.backend.Diary;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DiaryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DiaryRepository diaryRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @DirtiesContext
    @Test
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/diary"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @DirtiesContext
    @Test
    void postTodo() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/diary")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                            "description": "test",
                                            "status": "OPEN"
                                        }
                                        """)
                )
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "description": "test",
                            "status": "OPEN"
                        }
                        """));
    }

    @DirtiesContext
    @Test
    void getTodoById() throws Exception {
        String responseJson = mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/diary")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                            "description": "test",
                                            "status": "OPEN"
                                        }
                                        """)
                )
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        JsonNode responseNode = objectMapper.readTree(responseJson);
        String id = responseNode.get("id").asText();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/diary/" + id))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "id": "%s",
                            "description": "test",
                            "status": "OPEN"
                        }
                        """.formatted(id)));
    }

    @DirtiesContext
    @Test
    void update() throws Exception {
        String responseJson = mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/diary")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                            "description": "test",
                                            "status": "OPEN"
                                        }
                                        """)
                )
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        JsonNode responseNode = objectMapper.readTree(responseJson);
        String id = responseNode.get("id").asText();

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/diary/" + id + "/update")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                            "id": "%s",
                                            "description": "updated description",
                                            "status": "IN_PROGRESS"
                                        }
                                        """.formatted(id))
                )
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "id": "%s",
                            "description": "updated description",
                            "status": "IN_PROGRESS"
                        }
                        """.formatted(id)));
    }

    @DirtiesContext
    @Test
    void delete() throws Exception {
        String responseJson = mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/diary")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                            "description": "test",
                                            "status": "OPEN"
                                        }
                                        """)
                )
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        JsonNode responseNode = objectMapper.readTree(responseJson);
        String id = responseNode.get("id").asText();

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/diary/" + id))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/diary/" + id))
                .andExpect(status().isNotFound());
    }
}
