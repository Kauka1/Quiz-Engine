package engine.business.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * This class keeps track of which questions are completed by which user in the H2 database
 */
@Entity
public class Completed {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn
    private Question question;

    private LocalDateTime completedAt;

    @ManyToOne
    @JoinColumn
    private User user;

    public Completed(Question question, LocalDateTime completedAt, User user) {
        this.question = question;
        this.completedAt = completedAt;
        this.user = user;
    }

    public Completed() {
        completedAt = LocalDateTime.now();
    }

    @JsonIgnore
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonProperty("id")
    public long getQuestion() {
        return question.getId();
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @JsonProperty("completedAt")
    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
