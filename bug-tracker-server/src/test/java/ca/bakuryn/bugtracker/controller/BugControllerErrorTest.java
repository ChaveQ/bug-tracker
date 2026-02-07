package ca.bakuryn.bugtracker.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ca.bakuryn.bugtracker.service.BugService;
import javax.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class BugControllerErrorTest {

  private MockMvc mockMvc;

  @Mock
  private BugService bugService;
  @InjectMocks
  private BugController bugController;

  @BeforeEach
  public void setup() {
    this.mockMvc = MockMvcBuilders.standaloneSetup(bugController)
        .setControllerAdvice(new GlobalExceptionHandler())
        .build();
  }

  @Test
  public void shouldReturnNotFoundError() throws Exception {
    when(bugService.findById(1L)).thenThrow(new EntityNotFoundException("Bug not found"));

    mockMvc.perform(get("/api/bugs/1"))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.message", is("Bug not found")));
  }

  @Test
  public void shouldReturnBadRequestError() throws Exception {
    when(bugService.findById(10L)).thenThrow(new IllegalArgumentException("Internal Error Message"));

    mockMvc.perform(get("/api/bugs/10"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message", is("Internal Error Message")));
  }

  @Test
  public void shouldReturnInternalServerError() throws Exception {
    when(bugService.findAll(any())).thenThrow(new RuntimeException("Database is down!"));

    mockMvc.perform(get("/api/bugs"))
        .andExpect(status().isInternalServerError())
        .andExpect(jsonPath("$.message", is("Internal Server Error")));
  }

}
