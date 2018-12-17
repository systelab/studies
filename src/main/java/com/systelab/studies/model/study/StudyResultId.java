package com.systelab.studies.model.study;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudyResultId implements Serializable {

    @Column(name = "fk_study")
    protected UUID studyId;

    @Column(name = "fk_result")
    protected Long resultId;
}
