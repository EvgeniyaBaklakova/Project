package com.javamentor.qa.platform.models.dto.tag;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrackedAndIgnoredTagsDto {
    private List<TrackedTagsDto> trackedTagList;
    private List<IgnoredTagsDto> ignoredTagList;
}