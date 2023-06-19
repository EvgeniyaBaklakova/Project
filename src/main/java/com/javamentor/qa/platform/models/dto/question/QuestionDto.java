package com.javamentor.qa.platform.models.dto.question;


import com.javamentor.qa.platform.models.dto.tag.TagDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDto {
    private Long id;
    private String title;
    private Long authorId;
    private Integer authorReputation;
    private String authorName;
    private String authorImage;
    private String description;
    private int viewCount;
    private int countAnswer;
    private int countValuable;
    private LocalDateTime persistDateTime;
    private LocalDateTime lastUpdateDateTime;
    private List<TagDto> listTagDto;

    public QuestionDto(Long id, String title, Long authorId, Integer authorReputation, String authorName, String authorImage, String description, Long viewCount, Long countAnswer, Long countValuable, LocalDateTime persistDateTime, LocalDateTime lastUpdateDateTime) {
        this.id = id;
        this.title = title;
        this.authorId = authorId;
        this.authorReputation = authorReputation;
        this.authorName = authorName;
        this.authorImage = authorImage;
        this.description = description;
        this.viewCount = Math.toIntExact(viewCount);
        this.countAnswer = Math.toIntExact(countAnswer);
        this.countValuable = Math.toIntExact(countValuable);
        this.persistDateTime = persistDateTime;
        this.lastUpdateDateTime = lastUpdateDateTime;
    }


}
