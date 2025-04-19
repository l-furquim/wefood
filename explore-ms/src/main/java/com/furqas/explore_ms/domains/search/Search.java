package com.furqas.explore_ms.domains.search;


import com.furqas.explore_ms.domains.search.enums.SearchCategory;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "searches")
public class Search {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "searched_at")
    private LocalDateTime searchedAt;

    private SearchCategory category;
}
