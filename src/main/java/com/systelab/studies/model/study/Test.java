package com.systelab.studies.model.study;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "test")
public class Test {

    @Id
    @GeneratedValue
    @ApiModelProperty(notes = "The database generated  ID")
    @Column( name = "testid")
    protected Long id;

    @Size(min = 1, max = 255)
    @Column( name = "testdescription")
    private String description;
}
