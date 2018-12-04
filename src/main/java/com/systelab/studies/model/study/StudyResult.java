package com.systelab.studies.model.study;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "studyresult",
        uniqueConstraints = { @UniqueConstraint(columnNames = { "result_id", "study_id" }) })
public class StudyResult implements Serializable {

    @Id
    @GeneratedValue
    @ApiModelProperty(notes = "The database generated  ID")
    protected Long id;

    @ManyToOne
    @JoinColumn(name = "result_id")
    private Result result;

    @ManyToOne
    @JoinColumn(name = "study_id")
    private Study study;

    private boolean isOmmited;

    @Size(min = 1, max = 255)
    private String comments;

}
