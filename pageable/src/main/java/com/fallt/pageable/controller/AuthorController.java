package com.fallt.pageable.controller;

import com.fallt.pageable.domain.dto.request.UpsertAuthorRequest;
import com.fallt.pageable.domain.dto.response.SuccessResponse;
import com.fallt.pageable.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping
    public SuccessResponse create(@RequestBody UpsertAuthorRequest request) {
        return authorService.create(request);
    }
}
