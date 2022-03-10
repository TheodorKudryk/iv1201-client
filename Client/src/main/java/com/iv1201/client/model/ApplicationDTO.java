/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iv1201.client.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;




/**
 *Class for when an applicant makes an application with their competence, their
 * experience and when they are available
 * @author theok
 */
public class ApplicationDTO {
    
    @NotNull
    @Min(0)
    private final int competence;
    
    @NotNull
    @Min(0)
    private final int experience;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private final String start;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private final String end;
    
    public ApplicationDTO(int competence, int experience, String start, String end) {
        this.competence = competence;
        this.experience = experience;
        this.start = start;
        this.end = end;
        
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public int getCompetence() {
        return competence;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public int getExperience() {
        return experience;
    }
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public String getStart() {
        return start;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public String getEnd() {
        return end;
    }
}
