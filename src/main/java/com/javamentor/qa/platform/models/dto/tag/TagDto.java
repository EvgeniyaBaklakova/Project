package com.javamentor.qa.platform.models.dto.tag;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TagDto {
    private Long  id;
    private String name;
    private String  description;
    private LocalDateTime persistDateTime;
}
