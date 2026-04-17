package com.example.ggulddeokbe.domain.bookmark.service;

import com.example.ggulddeokbe.domain.bookmark.domain.Bookmark;
import com.example.ggulddeokbe.domain.bookmark.domain.repository.BookmarkRepository;
import com.example.ggulddeokbe.domain.bookmark.dto.BookmarkRequest;
import com.example.ggulddeokbe.domain.bookmark.dto.BookmarkResponse;
import com.example.ggulddeokbe.domain.user.domain.User;
import com.example.ggulddeokbe.domain.user.facade.UserFacade;
import com.example.ggulddeokbe.global.exception.BaseException;
import com.example.ggulddeokbe.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final UserFacade userFacade;

    @Transactional
    public BookmarkResponse addBookmark(BookmarkRequest request) {
        User user = userFacade.getCurrentUser();

        if (bookmarkRepository.existsByUserAndPolicyId(user, request.policyId())) {
            throw new BaseException(ErrorCode.BOOKMARK_ALREADY_EXISTS);
        }

        Bookmark bookmark = Bookmark.builder()
            .user(user)
            .policyId(request.policyId())
            .policyName(request.policyName())
            .keywords(request.keywords())
            .largeCategoryName(request.largeCategoryName())
            .description(request.description())
            .build();

        return BookmarkResponse.from(bookmarkRepository.save(bookmark));
    }

    @Transactional
    public void removeBookmark(String policyId) {
        User user = userFacade.getCurrentUser();

        if (!bookmarkRepository.existsByUserAndPolicyId(user, policyId)) {
            throw new BaseException(ErrorCode.BOOKMARK_NOT_FOUND);
        }

        bookmarkRepository.deleteByUserAndPolicyId(user, policyId);
    }

    @Transactional(readOnly = true)
    public List<BookmarkResponse> getMyBookmarks() {
        User user = userFacade.getCurrentUser();
        return bookmarkRepository.findByUser(user).stream()
            .map(BookmarkResponse::from)
            .toList();
    }
}
