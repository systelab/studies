package com.systelab.studies.rest.study;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.systelab.studies.config.TokenProvider;
import com.systelab.studies.model.study.Result;
import com.systelab.studies.model.study.Study;
import com.systelab.studies.model.study.StudyType;
import com.systelab.studies.repository.ResultRepository;
import com.systelab.studies.repository.StudyRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ResultControllerTest {
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private TokenProvider tokenProvider;

    @MockBean
    private ResultRepository mockStudyRepository;


    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testGetAllResults() throws Exception {
        Result resultA = createResult(1234);
        Result resultB = createResult(5678);
        List<Result> results = Arrays.asList(resultA,
                resultB);

        Page<Result> pageofResults = new PageImpl<>(results);

        when(mockStudyRepository.findAll(isA(Pageable.class))).thenReturn(pageofResults);

        mvc.perform(get("/studies/v1/results")
                .header("Authorization", "Bearer 5d1103e-b3e1-4ae9-b606-46c9c1bc915a"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.content[1].id", is(5678)))
                .andExpect(jsonPath("$.content[0].id", is(1234)));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testGetResult() throws Exception {
        Optional<Result> result = Optional.of(createResult(1234));

        when(mockStudyRepository.findById(isA(Long.class))).thenReturn(result);

        mvc.perform(get("/studies/v1/results/{id}", "1234")
                .header("Authorization", "Bearer 5d1103e-b3e1-4ae9-b606-46c9c1bc915a"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id", is(1234)))
                .andExpect(jsonPath("$.comments", is("comments")));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testAddResult() throws Exception {
        Result result = createResult(1234);

        when(mockStudyRepository.save(any())).thenReturn(result);

        mvc.perform(post("/studies/v1/results/result")
                .header("Authorization", "Bearer 5d1103e-b3e1-4ae9-b606-46c9c1bc915a")
                .contentType(MediaType.APPLICATION_JSON).content(createResultInJson(result)))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(roles = "User")
    public void testDeleteStudy() throws Exception {
        Optional<Result> result = Optional.of(createResult(1234));
        when(mockStudyRepository.findById(isA(Long.class))).thenReturn(result);

        mvc.perform(delete("/studies/v1/results/{1}", "1234")
                .header("Authorization", "Bearer 5d1103e-b3e1-4ae9-b606-46c9c1bc915a"))
                .andExpect(status().is2xxSuccessful());
    }

    private static String createResultInJson(Result result) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(result);
    }

    private Result createResult(int id) {
        Result result = new Result();
        result.setId(new Long(id));
        result.setComments("comments");
        result.setContainerLabel("container");
        return result;
    }
}
