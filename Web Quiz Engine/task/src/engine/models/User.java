package engine.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Column
    @Pattern(regexp = "[A-Za-z0-9._%-+]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}",
            message = "Username should be correct email")
    @NotNull
    private String email;

    @Column
    @Size(min = 5)
    @NotNull
    private String password;

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany
    @JoinColumn(name = "user_id")
    private Set<Question> questions = Set.of();


    @OneToMany
    @JoinColumn(name = "userId")
    private Set<SolvedQuestion> solvedQuestions = Set.of();

    public User(String email, String password, Long id, Set<Question> questions) {
        this.email = email;
        this.password = password;
        this.id = id;
        this.questions = questions;
    }

    public User() { }

    public String getEmail() {
        return email;
    }

    public void setEmail(String username) {
        this.email = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }

    public Set<SolvedQuestion> getSolvedQuestions() {
        return solvedQuestions;
    }

    public void setSolvedQuestions(Set<SolvedQuestion> solvedQuestions) {
        this.solvedQuestions = solvedQuestions;
    }
}
