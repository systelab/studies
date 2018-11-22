package com.systelab.studies.rest.study;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.systelab.studies.config.TokenProvider;
import com.systelab.studies.model.study.Study;
import com.systelab.studies.model.study.StudyType;
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
public class StudyControllerTest {
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private TokenProvider tokenProvider;

    @MockBean
    private StudyRepository mockStudyRepository;


    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testGetAllStudy() throws Exception {
        Study studyA = createStudy("Study A");
        Study studyB = createStudy("Study B");
        List<Study> studies = Arrays.asList(studyA,
                studyB);

        Page<Study> pageofStudy = new PageImpl<>(studies);

        when(mockStudyRepository.findAll(isA(Pageable.class))).thenReturn(pageofStudy);

        mvc.perform(get("/studies/v1/studies")
                .header("Authorization", "Bearer 5d1103e-b3e1-4ae9-b606-46c9c1bc915a"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.content[1].id", is("a98b8fe5-7cc5-4348-8f99-4860f5b84b13")))
                .andExpect(jsonPath("$.content[0].description", is("Study A")));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testGetStudy() throws Exception {
        Optional<Study> patient = Optional.of(createStudy("Study A"));

        when(mockStudyRepository.findById(isA(UUID.class))).thenReturn(patient);

        mvc.perform(get("/studies/v1/studies/{id}", "a98b8fe5-7cc5-4348-8f99-4860f5b84b13")
                .header("Authorization", "Bearer 5d1103e-b3e1-4ae9-b606-46c9c1bc915a"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id", is("a98b8fe5-7cc5-4348-8f99-4860f5b84b13")))
                .andExpect(jsonPath("$.description", is("Study A")));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testAddStudy() throws Exception {
        Study study = createStudy("A");

        when(mockStudyRepository.save(any())).thenReturn(study);

        mvc.perform(post("/studies/v1/studies/study")
                .header("Authorization", "Bearer 5d1103e-b3e1-4ae9-b606-46c9c1bc915a")
                .contentType(MediaType.APPLICATION_JSON).content(createStudyInJson(study)))
                .andExpect(status().is2xxSuccessful());

    }

    @Test
    @WithMockUser(roles = "User")
    public void testDeleteStudy() throws Exception {
        Optional<Study> study = Optional.of(createStudy("Study A"));
        when(mockStudyRepository.findById(isA(UUID.class))).thenReturn(study);

        mvc.perform(delete("/studies/v1/studies/{1}", "a98b8fe5-7cc5-4348-8f99-4860f5b84b13")
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
        study.setType(StudyType.A);
        study.setDescription(studyName);
        return study;
    }
}
