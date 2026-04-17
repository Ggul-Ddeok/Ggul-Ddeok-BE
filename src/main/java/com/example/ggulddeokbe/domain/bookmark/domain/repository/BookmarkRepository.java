package com.example.ggulddeokbe.domain.bookmark.domain.repository;

import com.example.ggulddeokbe.domain.bookmark.domain.Bookmark;
import com.example.ggulddeokbe.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    List<Bookmark> findByUser(User user);

    boolean existsByUserAndPolicyId(User user, String policyId);

    void deleteByUserAndPolicyId(User user, String policyId);
}
