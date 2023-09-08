package com.digital.uwp;

import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "classpath:sql/createTask.sql")
    public void getTaskByIdTest_positive() throws Exception {
        MvcResult result = mockMvc.perform(get("/todolist").param("id", "1"))
            .andExpect(status().isOk()).andReturn();

        JSONAssert.assertEquals(TestUtil.getContentAsString("response/task-controller/task.json"),
            result.getResponse().getContentAsString(),
            JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "classpath:sql/createTask.sql")
    public void getAllTaskByIdTest_positive() throws Exception {

        MvcResult result = mockMvc.perform(get("/todolist/all")
            .param("page_number", "0")
            .param("page_size", "10")
        )
            .andExpect(status().isOk()).andReturn();

        JSONAssert.assertEquals(TestUtil.getContentAsString("response/task-controller/pageTask.json"),
            result.getResponse().getContentAsString(),
            JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test
    public void createTaskTest_positive() throws Exception {

        MvcResult result = mockMvc.perform(post("/todolist")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.getContentAsString("request/task-controller/createTask.json")))
            .andExpect(status().isCreated()).andReturn();

        JSONAssert.assertEquals(TestUtil.getContentAsString("response/task-controller/task.json"),
            result.getResponse().getContentAsString(),
            JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "classpath:sql/createTask.sql")
    public void updateStatusTask_positive() throws Exception {
        mockMvc.perform(put("/todolist/status")
            .param("id", "1")
            .param("status", "DONE")
        )
            .andExpect(status().isOk());

        MvcResult result = mockMvc.perform(get("/todolist").param("id", "1"))
            .andExpect(status().isOk()).andReturn();
        JSONAssert.assertEquals(TestUtil.getContentAsString("response/task-controller/taskDone.json"),
            result.getResponse().getContentAsString(),
            JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "classpath:sql/createTask.sql")
    public void updateDescriptionTask_positive() throws Exception {
        mockMvc.perform(put("/todolist/description")
            .param("id", "1")
            .param("description", "task_desc")
        )
            .andExpect(status().isOk());

        MvcResult result = mockMvc.perform(get("/todolist").param("id", "1"))
            .andExpect(status().isOk()).andReturn();
        JSONAssert.assertEquals(TestUtil.getContentAsString("response/task-controller/taskDesc.json"),
            result.getResponse().getContentAsString(),
            JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test
    public void createTaskTest_negative() throws Exception {
        mockMvc.perform(post("/todolist")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.getContentAsString("request/task-controller/createTaskNullName.json")))
            .andExpect(status().isBadRequest());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "classpath:sql/createTask.sql")
    public void getTaskByIdTest_negative() throws Exception {
        mockMvc.perform(get("/todolist").param("id", "-1"))
            .andExpect(status().isBadRequest());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "classpath:sql/createTask.sql")
    public void updateStatusTask_negative() throws Exception {
        mockMvc.perform(put("/todolist/status")
            .param("id", "-10")
            .param("status", "DONE")
        )
            .andExpect(status().isBadRequest());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "classpath:sql/createTask.sql")
    public void updateDescriptionTask_negative() throws Exception {
        mockMvc.perform(put("/todolist/description")
            .param("id", "10")
            .param("description", "task_desc")
        )
            .andExpect(status().isBadRequest());
    }
}
