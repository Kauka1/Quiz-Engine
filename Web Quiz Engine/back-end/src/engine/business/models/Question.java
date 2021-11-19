package engine.business.models;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Question objects hold the question prompt and the solution information. Integrated with the h2 database
 */
@Entity
public class Question {
    //primary key
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private int id;

    //Title of the question on the table
    @NotBlank(message = "Title must not be blank")
    private String title;

    //The question body itself that asks the question
    @NotBlank(message = "Text must not be blank")
    private String text;

    //The 4 options of the question, the not null prevents blank options
    @NotNull
    @Size(min = 2, message = "Not enough options")
    @ElementCollection(targetClass = String.class)
    private List<String> options;

    //integer array that lets backend to identify which options are the correct answer
    //example: 0 in array means the first option is a right answer. Can be blank
    private Integer[] answer;

    //joins the question table to users
    @ManyToOne
    @JoinColumn(name = "UserID")
    private User user;

    //checks which of the questions are deleted
    @OneToMany(mappedBy = "question" ,cascade = CascadeType.ALL)
    private List<Completed> completed;

    //Question constructor for all parameters
    public Question(String title, String text, List<String> options, Integer[] answer) {
        this.title = title;
        this.text = text;
        this.options = options;
        this.answer = answer;
    }

    //question constructor
    public Question() {
        if (this.answer == null)
            this.answer = new Integer[]{};
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public List<String> getOptions() {
        return options;
    }

    @JsonIgnore
    public Integer[] getAnswer() {
        return answer;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    @JsonProperty
    public void setAnswer(Integer[] answer) {
        this.answer = answer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @JsonIgnore
    public List<Completed> getCompleted() {
        return completed;
    }

    public void setCompleted(List<Completed> completed) {
        this.completed = completed;
    }


}
