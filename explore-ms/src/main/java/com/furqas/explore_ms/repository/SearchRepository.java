package com.furqas.explore_ms.repository;

import com.furqas.explore_ms.domains.search.Search;
import feign.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SearchRepository extends JpaRepository<Search, Long> {

    @Query(value = "SELECT s FROM Search s WHERE s.userId = :userId ORDER BY s.searchedAt DESC")
    List<Search> getRecentSearches(@Param("userId") String userId, Pageable pageable);

    @Query(value = "SELECT s FROM Search s WHERE s.userId = :userId")
    List<Search> getAllByUserId(@Param("userId") String userId);

    @Query("SELECT s FROM Search s WHERE s.content = :content AND s.userId = :userId")
    Optional<Search> getByContentAndUserId(@Param("content") String content, @Param("userId") String userId);

}
