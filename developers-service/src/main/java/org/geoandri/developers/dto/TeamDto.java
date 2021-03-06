package org.geoandri.developers.dto;

import javax.validation.constraints.NotEmpty;
import java.util.Objects;

public class TeamDto {
    private long id;

    @NotEmpty(message = "Team's name is required.")
    private String name;

    private String description;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "TeamDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeamDto teamDto = (TeamDto) o;
        return id == teamDto.id && name.equals(teamDto.name) && Objects.equals(description, teamDto.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }
}
