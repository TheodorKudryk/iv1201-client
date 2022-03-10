/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iv1201.client.model;




/**
 *
 * @author theok
 */
public class ApplicationDTO {
    private String competence;
    private String experience;
    private String start;
    private String end;
    
    public ApplicationDTO(String competence, String experience, String start, String end) {
        this.competence = competence;
        this.experience = experience;
        this.start = start;
        this.end = end;
        
    }


    public String getCompetence() {
        return competence;
    }

    public String getExperience() {
        return experience;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }
}
