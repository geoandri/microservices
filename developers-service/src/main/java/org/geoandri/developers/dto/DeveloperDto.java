package org.geoandri.developers.dto;

import javax.validation.constraints.NotBlank;

public class DeveloperDto {

    private long id;

    @NotBlank(message = "Developer's name is required.")
    private String name;

    @NotBlank(message = "Developer's team is required.")
    private String team;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }
}
