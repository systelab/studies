package com.systelab.studies.rest.study;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.systelab.studies.config.TokenProvider;
import com.systelab.studies.model.study.Result;
import com.systelab.studies.model.study.Study;
import com.systelab.studies.model.study.StudyResult;
import com.systelab.studies.model.study.StudyType;
import com.systelab.studies.repository.StudyRepository;
import com.systelab.studies.repository.StudyResultRepository;
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
public class StudyResultControllerTest {
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private TokenProvider tokenProvider;

    @MockBean
    private StudyResultRepository mockStudyRepository;


    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testGetAllStudyResults() throws Exception {
        StudyResult studyResultA = createStudyResult(1234);
        StudyResult studyResultB = createStudyResult(5678);

        List<StudyResult> studyResult = Arrays.asList(studyResultA,studyResultB);

        Page<StudyResult> pageofStudy = new PageImpl<>(studyResult);

        when(mockStudyRepository.findAll(isA(Pageable.class))).thenReturn(pageofStudy);

        mvc.perform(get("/studies/v1/studyresults")
                .header("Authorization", "Bearer 5d1103e-b3e1-4ae9-b606-46c9c1bc915a"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.content[1].id", is(5678)))
                .andExpect(jsonPath("$.content[1].result.comments", is("comments")))
                .andExpect(jsonPath("$.content[0].description", is("description")))
                .andExpect(jsonPath("$.content[0].study.id", is("a98b8fe5-7cc5-4348-8f99-4860f5b84b13")));

    }

    @Test
    @WithMockUser(roles = "USER")
    public void testGetStudyResult() throws Exception {

        Optional<StudyResult> studyResult = Optional.of(createStudyResult(1234));

        when(mockStudyRepository.findById(isA(Long.class))).thenReturn(studyResult);

        mvc.perform(get("/studies/v1/studyresults/{id}", "1234")
                .header("Authorization", "Bearer 5d1103e-b3e1-4ae9-b606-46c9c1bc915a"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id", is(1234)))
                .andExpect(jsonPath("$.description", is("description")));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testAddStudyResult() throws Exception {
        StudyResult studyResult = createStudyResult(1234);

        when(mockStudyRepository.save(any())).thenReturn(studyResult);

        mvc.perform(post("/studies/v1/studyresults/studyresult")
                .header("Authorization", "Bearer 5d1103e-b3e1-4ae9-b606-46c9c1bc915a")
                .contentType(MediaType.APPLICATION_JSON).content(createStudyResultInJson(studyResult)))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(roles = "User")
    public void testDeleteStudyResult() throws Exception {
        Optional<StudyResult> studyResult = Optional.of(createStudyResult(1234));
        when(mockStudyRepository.findById(isA(Long.class))).thenReturn(studyResult);

        mvc.perform(delete("/studies/v1/studyresults/{1}", "1234")
                .header("Authorization", "Bearer 5d1103e-b3e1-4ae9-b606-46c9c1bc915a"))
                .andExpect(status().is2xxSuccessful());
    }

    private static String createStudyInJson(Study study) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(study);
    }

    private Study createStudy(String studyName) {
        Study study = new Study();
        study.setId(UUID.fromString("a98b8fe5-7cc5-4348-8f99-4860f5b84b13"));
        study.setType(StudyType.REAGENT);
        study.setDescription(studyName);
        return study;
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

    private StudyResult createStudyResult(int id) {
        StudyResult studyResult = new StudyResult();
        studyResult.setId(new Long(id));
        studyResult.setDescription("description");
        studyResult.setResult(createResult(1234));
        studyResult.setStudy(createStudy("Study"));
        studyResult.setOmmited(true);
        return studyResult;
    }

    private static String createStudyResultInJson(StudyResult studyResult) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(studyResult);
    }

}
