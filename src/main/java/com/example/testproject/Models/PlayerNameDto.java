package com.example.testproject.Models;

import lombok.AllArgsConstructor;


public class PlayerNameDto {
    private String name;
    private String family;

    public void setName(String name){
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setFamily(String family){
        this.family = family;
    }
    public String getFamily()
    {
        return family;
    }
}
