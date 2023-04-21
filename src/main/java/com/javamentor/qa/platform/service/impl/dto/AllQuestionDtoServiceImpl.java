package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.AllQuestionDtoDao;
import com.javamentor.qa.platform.models.dto.AllQuestionDto;
import com.javamentor.qa.platform.service.abstracts.dto.AllQuestionDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AllQuestionDtoServiceImpl implements AllQuestionDtoService {
    private final AllQuestionDtoDao allQuestionDtoDao;
@Autowired
    public AllQuestionDtoServiceImpl(AllQuestionDtoDao allQuestionDtoDao) {
        this.allQuestionDtoDao = allQuestionDtoDao;
    }

    @Override
    public List<AllQuestionDto> getAllQuestions(String email) {
      return allQuestionDtoDao.getAllQuestions(email);
    }
}
