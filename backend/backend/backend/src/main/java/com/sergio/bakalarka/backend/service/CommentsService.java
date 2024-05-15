package com.sergio.bakalarka.backend.service;

import com.sergio.bakalarka.backend.model.dto.CommentsDto;
import com.sergio.bakalarka.backend.repository.CommentsDao;
import com.sergio.bakalarka.backend.repository.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentsService {

    private final CommentsDao commentsDao;

    public List<CommentsDto> getAllComments() {
        return commentsDao.getAllComments();
    }


}
