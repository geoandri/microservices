package org.geoandri.teams.dto;

import javax.validation.constraints.NotEmpty;

public class TeamDto {
    private long id;

    @NotEmpty(message = "Team's name is required.")
    private String name;

    private String description;
}
