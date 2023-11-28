package hk.derrick.mbdemo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import hk.derrick.client.ClientApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties.StubsMode;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ClientApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@AutoConfigureStubRunner(ids = {"hk.derrick:mb-demo-server:+:stubs:8080"},
    stubsMode = StubsMode.LOCAL)
public class IntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void whenDueDateIsEarlierThenReturnError() throws Exception {
    mockMvc.perform(post("/todos")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"description\":\"test desc\",\"dueDate\":\"2012-04-23T18:25:43.511Z\"}")
    ).andExpect(
        status().isBadRequest()
    );

  }
}
