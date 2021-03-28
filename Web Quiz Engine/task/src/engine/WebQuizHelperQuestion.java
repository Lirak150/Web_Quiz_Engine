package engine;

import engine.H2.QuestionRepository;
import engine.H2.SolvedQuestionRepository;
import engine.models.Answer;
import engine.models.Question;
import engine.models.SolvedQuestion;
import engine.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Service
public class WebQuizHelperQuestion {

    private final QuestionRepository questionRepository;
    private final SolvedQuestionRepository sQRepository;

    @Autowired
    public WebQuizHelperQuestion(QuestionRepository questionRepository, SolvedQuestionRepository sQRepository) {
        this.questionRepository = questionRepository;
        this.sQRepository = sQRepository;
    }

    public Question addQuestion(Question question) {
        return questionRepository.save(question);
    }

    public void deleteQuestion(int id) {
        questionRepository.deleteById(id);
    }

    public boolean existsQuestion(int id) {
        return questionRepository.existsById(id);
    }

    public Optional<Question> getQuestion(int id) {
        return questionRepository.findById(id);
    }

    public Page<Question> getAllQuestions(int pageNumber, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return questionRepository.findAll(pageable);
    }

    public Answer checkAnswer(Set<Integer> answer, Question question) {
        if (answer.equals(question.getAnswer())) {
            return Answer.TRUE_ANSWER;
        } else {
            return Answer.FALSE_ANSWER;
        }
    }

    public SolvedQuestion addSolvedQuestion(User user, Question question) {
        SolvedQuestion sQ = new SolvedQuestion();
        sQ.setCompletedAt(LocalDateTime.now());
        sQ.setQuestionId(question.getId());
        sQ.setUserId(user.getId());
        return sQRepository.save(sQ);
    }

    public Page<SolvedQuestion> getAllSolvedQuestions(User user, int pageNumber, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
        return sQRepository.findAllByUserIdEquals(user.getId(), pageable);
    }
}
