package engine.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Table(name = "questions")
@Entity
public class Question {

    @NotEmpty(message = "Question should have a title")
    @Column
    private String title;


    @NotEmpty(message = "Question should have a text")
    @Column
    private String text;

    @Size(min = 2, message = "Question should have at least 2 options")
    @NotNull(message = "Question should have options")


    @ElementCollection
    @CollectionTable(name = "question_options", joinColumns = @JoinColumn(name = "question_id"))
    private List<String> options;

    @ElementCollection
    @CollectionTable(name = "question_answers", joinColumns = @JoinColumn(name = "question_id"))
    private Set<Integer> answer = Set.of();

    @Id
    @GeneratedValue
    private int id;

    public Question(String title, String text, List<String> options, Set<Integer> answer) {
        this.title = title;
        this.text = text;
        this.options = options;
        this.answer = answer;
    }

    public Question() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    @JsonIgnore()
    public Set<Integer> getAnswer() {
        return answer;
    }

    @JsonProperty
    public void setAnswer(Set<Integer> answer) {
        this.answer = answer == null ? Set.of() : answer;
    }

    @JsonProperty
    public int getId() {
        return id;
    }

    @JsonIgnore
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return Objects.equals(question.id, this.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, text, options, answer);
    }
}
