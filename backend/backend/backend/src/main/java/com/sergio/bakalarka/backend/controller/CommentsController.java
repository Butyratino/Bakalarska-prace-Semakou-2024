package com.sergio.bakalarka.backend.controller;

import com.sergio.bakalarka.backend.model.dto.CommentsDto;
import com.sergio.bakalarka.backend.service.CommentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentsController {

    private final CommentsService commentsService;

    @GetMapping("/all")
    public List<CommentsDto> getAllComments() {
        List<CommentsDto> products = commentsService.getAllComments();
        return products;
    }

}
