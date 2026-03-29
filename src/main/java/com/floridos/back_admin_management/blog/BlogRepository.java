package com.floridos.back_admin_management.blog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BlogRepository extends JpaRepository<BlogPost, Long> {
    List<BlogPost> findByPublishedTrueOrderByCreatedAtDesc();
    Optional<BlogPost> findBySlugAndPublishedTrue(String slug);
    List<BlogPost> findByCategory(String category);
}
