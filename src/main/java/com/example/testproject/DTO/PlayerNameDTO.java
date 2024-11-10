package com.example.testproject.DTO;


public class PlayerNameDTO {
    private String name;
    private String login;
    private String password;

    public void setName(String name){
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setLogin(String login){
        this.login = login;
    }
    public String getLogin()
    {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
