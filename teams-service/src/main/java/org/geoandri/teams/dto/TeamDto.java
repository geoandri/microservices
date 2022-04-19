package org.geoandri.teams.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

public class TeamDto {
    private long id;

    @NotBlank(message = "Team's name is required.")
    private String name;

    private String description;

    public TeamDto() {
    }

    public TeamDto(long id, @NotEmpty(message = "Team's name is required.") String name) {
        this.id = id;
        this.name = name;
    }

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
        return id == teamDto.id &&
                name.equals(teamDto.name) &&
                description.equals(teamDto.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }
}
