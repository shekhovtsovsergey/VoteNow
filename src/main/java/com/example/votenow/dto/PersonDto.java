package com.example.votenow.dto;


import com.example.votenow.model.VoteType;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.util.Objects;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonDto {

    private Integer id;
    @NotNull
    private String name;
    @NotNull
    private String document;
    @NotNull
    @Enumerated(EnumType.STRING)
    private VoteType voteType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass() && getClass() != o.getClass().getSuperclass()) return false;
        PersonDto personDto = (PersonDto) o;
        return id.equals(personDto.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,name,document,voteType);
    }
}
