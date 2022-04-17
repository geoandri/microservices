package org.geoandri.developers.dto;

import javax.validation.constraints.NotEmpty;
import java.util.Objects;

public class TeamDto {

    public TeamDto(long id) {
        this.id = id;
    }

    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeamDto teamDto = (TeamDto) o;
        return id == teamDto.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "TeamDto{" +
                "id=" + id +
                '}';
    }
}
