package engine.business.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * User class that works with spring security for authentication
 */
//Disabled while the security portion is disabled
@Entity
public class User {

    //Used for primary key in table
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(updatable = false, nullable = false)
    @JsonIgnore
    private int id;

    //Used to identify the creator of a quiz
    @Email
    @NotBlank
    @Pattern(regexp = ".*\\..*")
    private String email;

    //password to login for user
    @Size(min = 5)
    private String password;

    //Determines what user has permission for
    private String roles;

    //Links users to completed questions list to give results
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Completed> completed;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Question> questions;

    public User() {
        roles = "USER";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    //sets password
    public void setPassword(String password) {
        this.password = password;
    }

    @JsonIgnore
    public String getRoles() {
        return roles;
    }

    @JsonProperty
    public void setRoles(String roles) {
        this.roles = roles;
    }

    @JsonIgnore
    public List<Completed> getCompleted() {
        return completed;
    }

    public void setCompleted(List<Completed> completed) {
        this.completed = completed;
    }

    @JsonIgnore
    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
