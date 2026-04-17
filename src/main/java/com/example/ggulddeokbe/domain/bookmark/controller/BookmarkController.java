package com.example.ggulddeokbe.domain.bookmark.controller;

import com.example.ggulddeokbe.domain.bookmark.dto.BookmarkRequest;
import com.example.ggulddeokbe.domain.bookmark.dto.BookmarkResponse;
import com.example.ggulddeokbe.domain.bookmark.service.BookmarkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
    @PostMapping
    public ResponseEntity<BookmarkResponse> addBookmark(@RequestBody BookmarkRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookmarkService.addBookmark(request));
    }

    @Operation(summary = "북마크 삭제")
    @DeleteMapping("/{policyId}")
    public ResponseEntity<Void> removeBookmark(@PathVariable String policyId) {
        bookmarkService.removeBookmark(policyId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "내 북마크 목록 조회")
    @GetMapping("/me")
    public ResponseEntity<List<BookmarkResponse>> getMyBookmarks() {
        return ResponseEntity.ok(bookmarkService.getMyBookmarks());
    }
}
