package ca.bakuryn.bugtracker.controller;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ca.bakuryn.bugtracker.config.TestPersistenceConfig;
import ca.bakuryn.bugtracker.config.TestWebConfig;
import ca.bakuryn.bugtracker.dto.BugDto;
import ca.bakuryn.bugtracker.dto.SeverityDto;
import ca.bakuryn.bugtracker.dto.StatusDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestPersistenceConfig.class, TestWebConfig.class})
@WebAppConfiguration
@Sql("/sql/init-db-data.sql")
@Transactional
public class BugControllerIT {

  @Autowired
  private WebApplicationContext webApplicationContext;

  private MockMvc mockMvc;
  private final ObjectMapper objectMapper = new ObjectMapper();

  @BeforeEach
  public void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
  }

  @Test
  @DisplayName("GET /api/bugs")
  public void shouldGetAllBugs() throws Exception {
    mockMvc.perform(get("/api/bugs"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(5)));
  }

  @Test
  @DisplayName("GET /api/bugs?severity=CRITICAL")
  public void shouldFilterBugsBySeverity() throws Exception {
    mockMvc.perform(get("/api/bugs")
            .param("severity", "CRITICAL"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[*].title", containsInAnyOrder("Critical Error", "Another Critical")))
        .andExpect(jsonPath("$[0].severity", is("Critical")))
        .andExpect(jsonPath("$[1].severity", is("Critical")));
  }

  @Test
  @DisplayName("GET /api/bugs/{id}")
  public void shouldGetBugById() throws Exception {
    mockMvc.perform(get("/api/bugs/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(1)))
        .andExpect(jsonPath("$.title", is("Critical Error")));
  }

  @Test
  @DisplayName("GET /api/bugs/{id}")
  public void shouldReturn404WhenBugNotFound() throws Exception {
    mockMvc.perform(get("/api/bugs/99999"))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.message", is("Bug not found")));
  }

  @Test
  @DisplayName("POST /api/bugs")
  public void shouldCreateNewBug() throws Exception {
    BugDto newBug = new BugDto();
    newBug.setTitle("Integration Test Bug");
    newBug.setDescription("Test Description");
    newBug.setSeverity(SeverityDto.LOW);
    newBug.setStatus(StatusDto.OPEN);

    String jsonRequest = objectMapper.writeValueAsString(newBug);

    mockMvc.perform(post("/api/bugs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonRequest))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").isNotEmpty())
        .andExpect(jsonPath("$.title", is("Integration Test Bug")))
        .andExpect(jsonPath("$.description", is("Test Description")))
        .andExpect(jsonPath("$.severity", is("Low")))
        .andExpect(jsonPath("$.status", is("Open")));
  }

  @Test
  @DisplayName("PUT /api/bugs/4")
  public void shouldUpdateBug() throws Exception {
    BugDto updateDto = new BugDto();
    updateDto.setId(4L);
    updateDto.setTitle("Updated Title Text");
    updateDto.setDescription("Updated Desc");
    updateDto.setSeverity(SeverityDto.HIGH);
    updateDto.setStatus(StatusDto.REOPENED);

    mockMvc.perform(put("/api/bugs/4")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updateDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(4)))
        .andExpect(jsonPath("$.title", is("Updated Title Text")))
        .andExpect(jsonPath("$.description", is("Updated Desc")))
        .andExpect(jsonPath("$.severity", is("High")))
        .andExpect(jsonPath("$.status", is("Reopened")));
  }

  @Test
  @DisplayName("PUT /api/bugs/4 - no Id")
  public void shouldUpdateNoIdError() throws Exception {
    BugDto updateDto = new BugDto();
    updateDto.setId(null);
    updateDto.setTitle("Updated Title Text");
    updateDto.setDescription("Updated Desc");
    updateDto.setSeverity(SeverityDto.HIGH);

    mockMvc.perform(put("/api/bugs/4")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updateDto)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message", is("Id mismatch error")));
  }

}
