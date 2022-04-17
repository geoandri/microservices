package org.geoandri.developers.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class DeveloperDto {

    private long id;

    @NotEmpty(message = "Developer's name is required.")
    private String name;

    @NotNull(message = "Developer's teamId is required.")
    private Long teamId;

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

    public long getTeamId() {
        return teamId;
    }

    public void setTeamId(long teamId) {
        this.teamId = teamId;
    }
}
