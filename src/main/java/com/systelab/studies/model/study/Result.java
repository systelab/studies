package com.systelab.studies.model.study;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "result")
public class Result {

    @Id
    @GeneratedValue
    @ApiModelProperty(notes = "The database generated  ID")
    protected Long id;

    private String containerLabel;

    @ManyToOne
    @JoinColumn(name = "test_id")
    private StudyTest studyTest;

    @ManyToOne
    @JoinColumn(name = "instrument_id")
    private Instrument instrument;

    private Double value;
    private String userresult;
    private Instant resultDate;

    private String instrumentAlarms;
    private String pseudoCodeValue;
    private InstrumentTestStatus status;

    @ManyToOne
    @JoinColumn(name = "material_id")
    private Material material;

    private String comments;

}
