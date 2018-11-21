package com.systelab.studies.model.study;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "instrument")
public class Instrument {

    @Id
    @GeneratedValue
    @ApiModelProperty(notes = "The database generated  ID")
    protected Long id;

    @Size(min = 1, max = 255)
    private String description;

}
