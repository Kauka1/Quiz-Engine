package engine.presentation;

import engine.business.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

/**
 * Controller and frontend for the application
 */
@RestController
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private ResponseService responseService;

    @Autowired
    private UserService userService;

    @Autowired
    private CompletedService completedService;

    @PostMapping("/api/quizzes")
    public Question createQuestion(@RequestBody @Valid Question question){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Optional<User> user = userService.getUserThroughEmail(email);
        question.setUser(user.get());

        questionService.createQuestion(question);
        return question;
    }

    @GetMapping("/api/quizzes/{id}")
    public ResponseEntity<Question> getQuestion(@PathVariable int id){
        try {
            Question question = questionService.getQuestion(id);
            if (question == null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            return new ResponseEntity<>(question, HttpStatus.OK);

        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Returns a random question for the frontend
    @GetMapping("/api/quizzes/random")
    public ResponseEntity<Question> getRandomQuestion(){
        Question question = questionService.getRandomQuestion();
        return ResponseEntity.ok(question);
    }

    @GetMapping("/api/quizzes")
    public Page<Question> getAllQuestions(@RequestParam int page){
        return questionService.getAllQuestions(page);
    }

    @PostMapping("/api/quizzes/{id}/solve")
    public ResponseEntity<Response> postAnswer(@PathVariable int id, @RequestBody(required = false) Answer answer){
        try {
            Question question = questionService.getQuestion(id);
            if (question == null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            Boolean correct = questionService.postAnswer(question, answer);
            if (correct) {
                //Getting the user information to pass into the solved variable
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                String loginName = authentication.getName();
                Optional<User> user = userService.getUserThroughEmail(loginName);

                completedService.addCompleted(question, user.orElseThrow());

                return new ResponseEntity<>(responseService.getResponse(0), HttpStatus.OK);
            }
            else
                return new ResponseEntity<>(responseService.getResponse(1), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/api/register")
    public ResponseEntity<Void> registerUser(@RequestBody @Valid User user){
        if (userService.emailExists(user))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        userService.createUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/api/quizzes/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable int id){
        if (!questionService.questionExists(id))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        if (!questionService.deleteQuestion(id)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/api/quizzes/deleteAll")
    public void deleteAllQuestions(){
        questionService.deleteAllQuestions();
    }


    @GetMapping("/api/quizzes/userget")
    public String getEmailName(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginName = authentication.getName();
        System.out.println(loginName);
        return loginName;
    }

    @GetMapping("/api/quizzes/completed")
    public ResponseEntity<Page<Completed>> getCompleted(@RequestParam int page){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String loginName = authentication.getName();

            Optional<User> user = userService.getUserThroughEmail(loginName);

            return new ResponseEntity<>(completedService.getCompletedUser(user.orElseThrow(), page), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

    }



}
