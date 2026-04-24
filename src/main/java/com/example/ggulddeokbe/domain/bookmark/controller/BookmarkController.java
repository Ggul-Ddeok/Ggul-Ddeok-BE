package com.example.ggulddeokbe.domain.bookmark.controller;

import com.example.ggulddeokbe.domain.bookmark.dto.BookmarkRequest;
import com.example.ggulddeokbe.domain.bookmark.dto.BookmarkResponse;
import com.example.ggulddeokbe.domain.bookmark.service.BookmarkService;
import com.example.ggulddeokbe.global.exception.ErrorCode;
import com.example.ggulddeokbe.global.swagger.ApiErrorCodes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Bookmark", description = "북마크 API")
@RestController
@RequestMapping("/api/bookmarks")
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @Operation(summary = "북마크 추가")
    @ApiErrorCodes({ErrorCode.UNAUTHORIZED, ErrorCode.USER_NOT_FOUND, ErrorCode.POLICY_NOT_FOUND, ErrorCode.BOOKMARK_ALREADY_EXISTS})
    @PostMapping
    public ResponseEntity<BookmarkResponse> addBookmark(@Valid @RequestBody BookmarkRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookmarkService.addBookmark(request));
    }

    @Operation(summary = "북마크 삭제")
    @ApiErrorCodes({ErrorCode.UNAUTHORIZED, ErrorCode.USER_NOT_FOUND, ErrorCode.BOOKMARK_NOT_FOUND})
    @DeleteMapping("/{policyId}")
    public ResponseEntity<Void> removeBookmark(@PathVariable String policyId) {
        bookmarkService.removeBookmark(policyId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "내 북마크 목록 조회")
    @ApiErrorCodes({ErrorCode.UNAUTHORIZED, ErrorCode.USER_NOT_FOUND})
    @GetMapping("/me")
    public ResponseEntity<List<BookmarkResponse>> getMyBookmarks() {
        return ResponseEntity.ok(bookmarkService.getMyBookmarks());
    }
}
