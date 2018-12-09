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
@Table(name = "connection")
public class Connection {

    @Id
    @GeneratedValue
    @ApiModelProperty(notes = "The database generated  ID")
    @Column( name = "connectionid" )
    protected Long id;

    @Column( name = "connectiondescription" )
    @Size(min = 1, max = 255)
    private String description;

}
