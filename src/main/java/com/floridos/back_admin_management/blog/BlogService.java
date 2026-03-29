package com.floridos.back_admin_management.blog;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;

    public List<BlogPost> findPublished() {
        return blogRepository.findByPublishedTrueOrderByCreatedAtDesc();
    }

    public Optional<BlogPost> findBySlug(String slug) {
        return blogRepository.findBySlugAndPublishedTrue(slug);
    }

    public List<BlogPost> findAll() {
        return blogRepository.findAll();
    }

    @Transactional
    public BlogPost create(BlogPost post) {
        return blogRepository.save(post);
    }

    @Transactional
    public BlogPost update(Long id, BlogPost incoming) {
        BlogPost p = blogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post no encontrado: " + id));

        if (incoming.getTitle()     != null) p.setTitle(incoming.getTitle());
        if (incoming.getSlug()      != null) p.setSlug(incoming.getSlug());
        if (incoming.getExcerpt()   != null) p.setExcerpt(incoming.getExcerpt());
        if (incoming.getContent()   != null) p.setContent(incoming.getContent());
        if (incoming.getImageUrl()  != null) p.setImageUrl(incoming.getImageUrl());
        if (incoming.getCategory()  != null) p.setCategory(incoming.getCategory());
        if (incoming.getAuthor()    != null) p.setAuthor(incoming.getAuthor());
        if (incoming.getPublished() != null) p.setPublished(incoming.getPublished());

        return blogRepository.save(p);
    }

    @Transactional
    public void delete(Long id) {
        if (!blogRepository.existsById(id))
            throw new RuntimeException("Post no encontrado: " + id);
        blogRepository.deleteById(id);
    }
}
