package com.systelab.studies.model.study;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Data
@NoArgsConstructor
@Table(name = "studyresult")
public class StudyResult {

    @EmbeddedId
    private StudyResultId id;

    @JsonIgnore
    @ManyToOne
    @MapsId("studyId")
    private Study study;

    @ManyToOne
    @MapsId("resultId")
    private Result result;

    private boolean isOmmited;

    @Size(min = 1, max = 255)
    private String comments;

    public StudyResult(Study b, Result p) {
        // create primary key
        this.id = new StudyResultId(b.getId(), p.getId());
        this.study = b;
        this.result = p;
    }

}
