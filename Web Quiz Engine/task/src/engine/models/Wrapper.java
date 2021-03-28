package engine.models;

import java.util.Set;

public class Wrapper<T> {

    private Set<T> answer = Set.of();

    public Wrapper() {
    }

    public Wrapper(Set<T> answer) {
        this.answer = answer;
    }

    public Set<T> getAnswer() {
        return answer;
    }

    public void setAnswer(Set<T> answer) {
        this.answer = answer;
    }
}