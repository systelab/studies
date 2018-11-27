package com.systelab.studies.model.study;

import com.systelab.studies.model.ModelBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "study")
public class Study extends ModelBase implements Serializable {

    private StudyType type;

    @Size(min = 1, max = 255)
    private String description;

    @Size(max = 255)
    private String userstudy;

    private StudyStatus status;

    @ApiModelProperty(notes = "YYYY-MM-DD")
    private LocalDate lastUpdate;

    @ManyToMany
    @JoinTable(name = "study_instruments",
            joinColumns = @JoinColumn(table = "study", name = "study_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(table = "instrument", name = "instrument_id", referencedColumnName = "id"))
    private Set<Instrument> instruments = new HashSet<>();


    @ManyToMany
    @JoinTable(name = "study_tests",
            joinColumns = @JoinColumn(table = "study", name = "study_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(table = "studyTest", name = "studytest_id", referencedColumnName = "id"))
    private Set<StudyTest> studyTests = new HashSet<>();

    @ApiModelProperty(notes = "YYYY-MM-DD")
    private LocalDate dateFrom;

    @ApiModelProperty(notes = "YYYY-MM-DD")
    private LocalDate DateTo;

    @Size(max = 50)
    private String currentLot;
    @Size(max = 2000)
    private String comments;
    @Size(max = 200)
    private String printableFile;
    @Size(max = 200)
    private String spreadSheetFile;


}