package engine;

import engine.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api")
public class WebQuizApi {
    private final WebQuizHelperQuestion questionHelper;
    private final WebQuizHelperUser userHelper;

    @Autowired
    public WebQuizApi(WebQuizHelperQuestion questionHelper, WebQuizHelperUser userHelper) {
        this.questionHelper = questionHelper;
        this.userHelper = userHelper;
    }

    @PostMapping(path = "/register")
    public void registerUser(@Valid @RequestBody UserDto userDto, HttpServletResponse response) {
        System.out.println(userDto.getEmail() + " " + userDto.getPassword());
        if (userHelper.existsUser(userDto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with this email is already existed");
        }
        userHelper.registerUser(userDto);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @GetMapping(path = "/quizzes/{id}", produces = "application/json")
    public Question getQuestionAsJSON(@PathVariable int id, HttpServletResponse response) {
        Optional<Question> question = this.questionHelper.getQuestion(id);
        if (question.isPresent()) {
            response.setStatus(HttpServletResponse.SC_OK);
            return question.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no such id");
        }
    }

    @GetMapping(path = "/quizzes", produces = "application/json")
    public Page<Question> getAllQuestions(@RequestParam(defaultValue = "0", name = "page") int pageNumber,
                                          @RequestParam(defaultValue = "10") int pageSize,
                                          @RequestParam(defaultValue = "id") String sortBy,
                                          HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_OK);
        return questionHelper.getAllQuestions(pageNumber, pageSize, sortBy);
    }

    @PostMapping(path = "/quizzes", produces = "application/json")
    public Question addQuestion(@RequestBody @Valid Question question, Principal principal, HttpServletResponse response) {
        User user = userHelper.getCurrentUser(principal);
        Question q = questionHelper.addQuestion(question);
        userHelper.addQuestionToUser(user, q);
        response.setStatus(HttpServletResponse.SC_OK);
        return question;
    }

    @DeleteMapping(path = "/quizzes/{id}")
    public void deleteQuestion(@PathVariable int id, Principal principal, HttpServletResponse response) {
        if (!questionHelper.existsQuestion(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        User user = userHelper.getCurrentUser(principal);
        if (!userHelper.isOwnerOfQuestion(user, id)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to delete this question");

        }
        questionHelper.deleteQuestion(id);
        userHelper.deleteQuestion(user, id);
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @PostMapping(path = "/quizzes/{id}/solve", produces = "application/json")
    public Answer solveQuestion(@RequestBody @Valid Wrapper<Integer> answer,
                                @PathVariable int id,
                                Principal principal,
                                HttpServletResponse response) {
        Optional<Question> question = questionHelper.getQuestion(id);
        if (question.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no such id");
        }
        response.setStatus(HttpServletResponse.SC_OK);
        Answer ans = questionHelper.checkAnswer(answer.getAnswer(), question.get());
        if (ans.equals(Answer.TRUE_ANSWER)) {
            User user = userHelper.getCurrentUser(principal);
            SolvedQuestion sQ = questionHelper.addSolvedQuestion(user, question.get());
            userHelper.addSolvedQuestionToUser(user, sQ);
        }
        return ans;
    }

    @GetMapping(path = "/quizzes/completed")
    public Page<SolvedQuestion> getSolvedQuestions(@RequestParam(defaultValue = "0", name = "page") int pageNumber,
                                                   @RequestParam(defaultValue = "10") int pageSize,
                                                   @RequestParam(defaultValue = "completedAt") String sortBy,
                                                   Principal principal, HttpServletResponse response) {
        User user = userHelper.getCurrentUser(principal);
        response.setStatus(HttpServletResponse.SC_OK);
        return questionHelper.getAllSolvedQuestions(user, pageNumber, pageSize, sortBy);
    }
}
