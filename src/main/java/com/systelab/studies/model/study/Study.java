package com.systelab.studies.model.study;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.systelab.studies.model.ModelBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "study")
public class Study extends ModelBase {

    private StudyType type;

    @Size(min = 1, max = 255)
    private String description;

    @Size(max = 255)
    private String studyUser;

    private StudyStatus status;

    @ApiModelProperty(notes = "YYYY-MM-DD")
    private LocalDate lastUpdate;

    @ManyToMany
    @JoinTable(name = "study_connections",
            joinColumns = @JoinColumn(table = "study", name = "study_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(table = "connection", name = "connection_id", referencedColumnName = "connectionid"))
    private Set<Connection> connections = new HashSet<>();


    @ManyToMany
    @JoinTable(name = "study_tests",
            joinColumns = @JoinColumn(table = "study", name = "study_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(table = "test", name = "test_id", referencedColumnName = "testid"))
    private Set<Test> tests = new HashSet<>();

    @OneToMany( mappedBy="study" )
    @JsonBackReference(value="study")
    private Set<StudyResult> studyResult;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinTable(name = "study_results",
            joinColumns = @JoinColumn(table = "study", name = "study_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(table = "result", name = "result_id", referencedColumnName = "id"))
    private Set<Result> results= new HashSet<>();

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