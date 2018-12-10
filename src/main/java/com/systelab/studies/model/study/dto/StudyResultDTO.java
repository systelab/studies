package com.systelab.studies.model.study.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudyResultDTO {

    private Long resultId;

    private boolean isOmmited;

    @Size(min = 1, max = 255)
    private String comments;
}
