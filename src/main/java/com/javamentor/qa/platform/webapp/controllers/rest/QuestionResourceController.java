package com.javamentor.qa.platform.webapp.controllers.rest;



import com.javamentor.qa.platform.dao.impl.pagination.*;
import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.dao.impl.pagination.QuestionDtoDaoWithoutAnswersImpl;
import com.javamentor.qa.platform.dao.impl.pagination.QuestionPageDtoDaoAllImpl;
import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.dto.question.QuestionCreateDto;
import com.javamentor.qa.platform.models.dto.question.QuestionDto;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;
import com.javamentor.qa.platform.models.entity.question.CommentQuestion;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.QuestionViewed;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.QuestionDtoService;
import com.javamentor.qa.platform.service.abstracts.model.BookMarksService;
import com.javamentor.qa.platform.service.abstracts.model.CommentQuestionService;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import com.javamentor.qa.platform.service.abstracts.model.QuestionViewedService;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import com.javamentor.qa.platform.service.abstracts.model.VoteForQuestionService;
import com.javamentor.qa.platform.webapp.converter.QuestionConverter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;



import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Api(value = "QuestionResource controller", tags = "Контроллер QuestionResource")
@RestController
@RequestMapping("api/user/question")
public class QuestionResourceController {
    private final BookMarksService bookMarksService;
    private final QuestionService questionService;
    private final QuestionDtoService questionDtoService;
    private final QuestionViewedService questionViewedService;
    private final UserService userService;
    private final CommentQuestionService commentQuestionService;
    private final QuestionConverter questionConverter;
    private final VoteForQuestionService voteForQuestionService;

    @Autowired
    public QuestionResourceController(BookMarksService bookMarksService, QuestionViewedService questionViewedService,
                                      QuestionService questionService, UserService userService, CommentQuestionService commentQuestionService,
                                      QuestionConverter questionConverter, QuestionDtoService questionDtoService, VoteForQuestionService voteForQuestionService) {
        this.bookMarksService = bookMarksService;
        this.questionService = questionService;
        this.questionDtoService = questionDtoService;
        this.questionViewedService = questionViewedService;
        this.userService = userService;
        this.commentQuestionService = commentQuestionService;
        this.questionConverter = questionConverter;
        this.voteForQuestionService = voteForQuestionService;
    }

    @PostMapping("/{id}/view")
    public ResponseEntity addView(@PathVariable("id") long id, @AuthenticationPrincipal User user) {
        Optional<Question> question = questionService.getById(id);
        if (question.isEmpty()) {
            return new ResponseEntity<>("Incorrect question id", HttpStatus.NOT_FOUND);
        }
        if (!questionViewedService.isViewed(user.getId(), id)) {
            questionViewedService.persist(new QuestionViewed(user, question.get(), LocalDateTime.now()));
            return new ResponseEntity<>("View was saved", HttpStatus.OK);
        }
        return new ResponseEntity<>("Already viewed", HttpStatus.OK);
    }

    @ApiOperation(value = "Добавляет новый Question и возвращает QuestionDto")
    @PostMapping
    public ResponseEntity<QuestionDto> addQuestion(@RequestBody @Valid QuestionCreateDto questionToCreate,
                                                   @AuthenticationPrincipal User user) {
        Question questionEntity = questionConverter.questionCreateDtoToQuestion(questionToCreate, user);

        Question question = questionService.save(questionEntity);

        QuestionDto questionDto = questionConverter.questionToQuestionDto(question, user);
        return new ResponseEntity<>(questionDto, HttpStatus.OK);
    }

    @ApiOperation(value = "Добавляет комментарий к вопросу")
    @PostMapping("/{id}/comment")
    public ResponseEntity<HttpStatus> addComment(@PathVariable("id") long id, @RequestBody String text) {
        Question question = questionService.getById(id).orElse(null);
        if (question == null || text == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        CommentQuestion comment = new CommentQuestion(text, question.getUser());
        comment.setQuestion(question);
        commentQuestionService.persist(comment);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Получение QuestionDto по Question id", tags = {"Получение QuestionDto"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "QuestionDto успешно получено"),
            @ApiResponse(code = 400, message = "Вопрос с таким ID не найден"),
            @ApiResponse(code = 401, message = "Вы не авторизованы для просмотра ресурса"),
            @ApiResponse(code = 403, message = "Доступ к ресурсу, к которому вы пытались обратиться, запрещен")})

    public ResponseEntity<?> getQuestionDtoById(@PathVariable Long id) {

        if (questionDtoService.getQuestionDtoById(id).isEmpty()) {
            return new ResponseEntity<>("Вопроса с ID " + id + " не существует!", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(questionDtoService.getQuestionDtoById(id), HttpStatus.OK);

    }


    @GetMapping("/tag/{id}")
    @ApiOperation(value = "Получение QuestionDto по TagId", tags = {"Получение QuestionDto"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "QuestionDto успешно получено"),
            @ApiResponse(code = 400, message = "QuestionDto с таким TagId не найден"),
            @ApiResponse(code = 401, message = "Вы не авторизованы для просмотра ресурса"),
            @ApiResponse(code = 403, message = "Доступ к ресурсу, к которому вы пытались обратиться, запрещен")})
    public ResponseEntity<?> getQuestionDtoByTagId(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer items,
            @PathVariable Long id) {
        Map<String, Object> props = new HashMap<>();
        props.put("id", id);
        PaginationData data = new PaginationData(page, items, QuestionDtoDaoWithoutAnswersImpl.class.getSimpleName(), props);

        if (questionDtoService.getPageDto(data).getItemsOnPage() == 0) {
            return new ResponseEntity<>("Тега с ID " + id + " не существует!", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(questionDtoService.getPageDto(data), HttpStatus.OK);
    }



    @PostMapping("/{id}/bookmark")
    @ApiOperation(value = "Добавление вопроcа в закладки текущего аутентифицированного пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Вопрос успешно добавлен"),
            @ApiResponse(code = 400, message = "Вопрос с таким ID не найден"),
            @ApiResponse(code = 401, message = "Вы не авторизованы для просмотра ресурса"),
            @ApiResponse(code = 403, message = "Доступ к ресурсу, к которому вы пытались обратиться, запрещен")})
    public ResponseEntity<String> addQuestionToBookmarks(@PathVariable("id") Long id,
                                                         @AuthenticationPrincipal User user) {
        bookMarksService.addBookMarks(user, id);
        return ResponseEntity.ok("Вопрос успешно добавлен в закладки");
    }

    @GetMapping("")
    @ApiOperation(value = "Получение всех QuestionDto с пагинацией")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = QuestionDto.class),
            @ApiResponse(code = 400, message = "QuestionDto не найдены")
    })
    public ResponseEntity<PageDto<QuestionDto>> getAllQuestions(@RequestParam(defaultValue = "1") Integer page,
                                                                @RequestParam(required = false, defaultValue = "10") Integer items,
                                                                @RequestParam(required = false) List<String> trackedTag,
                                                                @RequestParam(required = false) List<String> ignoredTag) {
        Map<String, Object> props = new HashMap<>();
        props.put("trackedTag", trackedTag);
        props.put("ignoredTag", ignoredTag);
        PaginationData data = new PaginationData(page, items,
                QuestionPageDtoDaoAllImpl.class.getSimpleName(), props);
        return new ResponseEntity<>(questionDtoService.getPageDto(data), HttpStatus.OK);
    }

    @ApiOperation(value = "Получение всех QuestionDto, на которые нет ответов")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Вопросы успешно получены"),
            @ApiResponse(code = 400, message = "Некорректный запрос"),
            @ApiResponse(code = 401, message = "Вы не авторизованы для просмотра ресурса"),
            @ApiResponse(code = 403, message = "Доступ к ресурсу, к которому вы пытались обратиться, запрещен"),
            @ApiResponse(code = 404, message = "Ресурс, к которому вы пытались обратиться, не найден")})
    @GetMapping("/noAnswer")
    public ResponseEntity<PageDto<QuestionDto>> getAllQuestionsWithoutAnswers(@RequestParam(defaultValue = "1") Integer page,
                                                                              @RequestParam(required = false, defaultValue = "10") Integer items,
                                                                              @RequestParam(required = false) List<Long> trackedTag,
                                                                              @RequestParam(required = false) List<Long> ignoredTag) {
        Map<String, Object> props = new HashMap<>();
        props.put("trackedTags", trackedTag);
        props.put("ignoredTags", ignoredTag);

        PaginationData data = new PaginationData(page, items, QuestionDtoDaoWithoutAnswersImpl.class.getSimpleName(), props);
        return new ResponseEntity<>(questionDtoService.getPageDto(data), HttpStatus.OK);
    }

    @GetMapping("/new")
    @ApiOperation(value = "Получение всех QuestionDto с пагинацией, отсортированные по дате добавления")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = PageDto.class),
            @ApiResponse(code = 400, message = "QuestionDto не найдены")
    })
    public ResponseEntity<PageDto<QuestionDto>> getAllQuestionDtoByCreatingDate(
              @RequestParam(required =  true, defaultValue = "1") Integer page
            , @RequestParam(required = false, defaultValue = "10") Integer itemsOnPage
            , @RequestParam(required = false) List<String> ignoredTag
            , @RequestParam(required = false) List<String> trackedTag) {

        Map<String, Object> properties = new HashMap<>();
        properties.put("trackedTag", trackedTag);
        properties.put("ignoredTag", ignoredTag);

        PaginationData data = new PaginationData(
            page, itemsOnPage, QuestionPageDtoDaoByPersistDateImpl.class.getSimpleName(), properties);
        return new ResponseEntity<>(questionDtoService.getPageDto(data), HttpStatus.OK);
    }

    @PostMapping("/{questionId}/upvote")
    @ApiOperation(value = "Проголосовать за вопрос")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Голос к вопросу принят"),
            @ApiResponse(code = 400, message = "Вопрос с таким ID не найден"),
            @ApiResponse(code = 401, message = "Вы не авторизованы для просмотра ресурса"),
            @ApiResponse(code = 403, message = "Доступ к ресурсу, к которому вы пытались обратиться, запрещен")})
    public ResponseEntity<String> upVoteForQuestion(@PathVariable("questionId") Long id,
                                                    @AuthenticationPrincipal User user) {
        if (!questionService.existsById(id)) {
            return new ResponseEntity<>("Такого вопроса не существует", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("" + voteForQuestionService.upVote(id, user.getId()), HttpStatus.OK);
    }

    @PostMapping("/{questionId}/downVote")
    @ApiOperation(value = "Проголосовать за вопрос")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Голос к вопросу принят"),
            @ApiResponse(code = 400, message = "Вопрос с таким ID не найден"),
            @ApiResponse(code = 401, message = "Вы не авторизованы для просмотра ресурса"),
            @ApiResponse(code = 403, message = "Доступ к ресурсу, к которому вы пытались обратиться, запрещен")})
    public ResponseEntity<String> downVoteForQuestion(@PathVariable("questionId") Long id,
                                                      @AuthenticationPrincipal User user) {
        if (!questionService.existsById(id)) {
            return new ResponseEntity<>("Такого вопроса не существует", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("" + voteForQuestionService.downVote(id, user.getId()), HttpStatus.OK);
    }
}
