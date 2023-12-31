package com.javamentor.qa.platform.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToMany;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "group_bookmark")
public class GroupBookmark {

    @Id
    @GeneratedValue(generator = "GroupBookmark_seq")
    private Long id;

    private String title;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "group_has_bookmark",
            joinColumns = @JoinColumn(name = "group_bookmark_id"),
            inverseJoinColumns = @JoinColumn(name = "bookmarks_id"))
    private Set<BookMarks> bookMarks = new HashSet<>();
}
