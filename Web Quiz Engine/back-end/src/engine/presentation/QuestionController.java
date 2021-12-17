package engine.presentation;

import engine.business.models.Answer;
import engine.business.models.Question;
import engine.business.models.Response;
import engine.business.models.User;
import engine.business.services.CompletedService;
import engine.business.services.QuestionService;
import engine.business.services.ResponseService;
import engine.business.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

/**
 * Controller and backend for the application
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000/")
public class QuestionController {

    //injects question services
    @Autowired
    private QuestionService questionService;

    //injects response service
    @Autowired
    private ResponseService responseService;

    //injects user services
    @Autowired
    private UserService userService;

    //injects completed services
    @Autowired
    private CompletedService completedService;

    //Adds a singular question to the database
    @PostMapping("/api/quizzes")
    public ResponseEntity<Question> createQuestion(@RequestBody @Valid Question question){
        System.out.println("quiz post attempted");
        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //String email = authentication.getName();

        //Optional<User> user = userService.getUserThroughEmail(email);
        Optional<User> user = userService.getUser();
        //question.setUser(user.get());

        questionService.createQuestion(question);
        return new ResponseEntity<>(question, HttpStatus.OK);
    }

    //returns a specific question by id
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

    //returns all questions in the form of a page, for testing purposes
    @GetMapping("/api/quizzes")
    public Page<Question> getAllQuestions(@RequestParam int page){
        return questionService.getAllQuestions(page);
    }

    //Checks if a submitted answer is correct
    @PostMapping("/api/quizzes/{id}/solve")
    public ResponseEntity<Response> postAnswer(@PathVariable int id, @RequestBody(required = false) Answer answer){
        try {
            Question question = questionService.getQuestion(id);
            if (question == null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            boolean[] correct = questionService.postAnswer(question, answer);
            boolean right = true;
            for (boolean b: correct){
                if (!b)
                    right = false;
            }

            if (right) {
                //Getting the user information to pass into the solved variable
                //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                //String loginName = authentication.getName();
                //Optional<User> user = userService.getUserThroughEmail(loginName);
                Optional<User> user = userService.getUser();

                //completedService.addCompleted(question, user.orElseThrow());

                return new ResponseEntity<>(responseService.getResponse(0), HttpStatus.OK);
            }
            else {
                responseService.getResponse(1).setCorrect(correct);
                return new ResponseEntity<>(responseService.getResponse(1), HttpStatus.OK);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    //This function is disabled until the front-end security is confirmed to work
    //For now, sends in a CORS error on firefox
    /*
    @PostMapping("/api/register")
    public ResponseEntity<Void> registerUser(@RequestBody @Valid User user){
        if (userService.emailExists(user))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        userService.createUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

     */

    //Deletes a question from the database
    @DeleteMapping("/api/quizzes/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable int id){
        if (!questionService.questionExists(id))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        //The id feature is disabled. Uncomment when id and security is working again
        /*
        if (questionService.deleteQuestion(id)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

         */

        questionService.deleteQuestion(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //Deletes questions from the database
    @DeleteMapping("/api/quizzes/deleteAll")
    public void deleteAllQuestions(){
        questionService.deleteAllQuestions();
    }


    //This piece of code is disabled until the frontend security is working again, fix the CORS error on firefox
    /*
    @GetMapping("/api/quizzes/userget")
    public String getEmailName(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginName = authentication.getName();
        System.out.println(loginName);
        return loginName;
    }

    //This piece of code is disabled until the frontend security is working again, fix the CORS error on firefox
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

     */



}

