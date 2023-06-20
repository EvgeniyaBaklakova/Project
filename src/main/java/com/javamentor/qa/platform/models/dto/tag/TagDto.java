package com.javamentor.qa.platform.models.dto.tag;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor


public class TagDto {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime persistDateTime;


    public TagDto(Long id, String name, String description, LocalDateTime persistDateTime) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.persistDateTime = persistDateTime;
    }
    public TagDto(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
