package engine;

import engine.H2.UserRepository;
import engine.models.Question;
import engine.models.SolvedQuestion;
import engine.models.User;
import engine.models.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.Optional;
import java.util.Set;

@Service
public class WebQuizHelperUser {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public WebQuizHelperUser(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean existsUser(String email) {
        return userRepository.existsByEmail(email);
    }

    public Optional<User> getUser(String email) {
        return userRepository.findByEmail(email);
    }

    public void registerUser(UserDto userDto) {
        User user = new User();
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        userRepository.save(user);
    }

    public User getCurrentUser(Principal principal) {
        Optional<User> userOptional = getUser(principal.getName());
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Fatal error");
        }
    }

    public boolean isOwnerOfQuestion(User user, int id) {
        Set<Question> questionSet = user.getQuestions();
        for (Question q :
                questionSet) {
            if (q.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public void deleteQuestion(User user, int id) {
        Set<Question> questionSet = user.getQuestions();
        questionSet.removeIf(q -> q.getId() == id);
        updateUser(user);
    }

    private void updateUser(User user) {
        userRepository.save(user);
    }

    public void addQuestionToUser(User user, Question q) {
        user.getQuestions().add(q);
        updateUser(user);
    }

    public void addSolvedQuestionToUser(User user, SolvedQuestion sQ) {
        user.getSolvedQuestions().add(sQ);
        updateUser(user);
    }
}
