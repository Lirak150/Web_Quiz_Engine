package engine.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class SolvedQuestion {

    @Column
    @JsonProperty("id")
    private int questionId;

    @Column
    @JsonIgnore
    private long userId;

    @Column
    private LocalDateTime completedAt;

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    public SolvedQuestion(int questionId, LocalDateTime completedAt, long userId) {
        this.questionId = questionId;
        this.completedAt = completedAt;
        this.userId = userId;
    }

    public SolvedQuestion() {
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime dateTime) {
        this.completedAt = dateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
