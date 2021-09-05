package engine.business.services;

import engine.business.models.Answer;
import engine.business.models.Question;
import engine.persistence.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Service that allows the program to get information from the database
 */
@Service
public class QuestionService {
    @Autowired
    QuestionRepository questionRepository;

    public void createQuestion(Question question){
        questionRepository.save(question);
    }

    public Question getQuestion(int id){
        return questionRepository.findById(id).orElse(null);
    }

    public Page<Question> getAllQuestions(int page){
        Pageable paging = PageRequest.of(page, 10);
        Page<Question> pagedResult = questionRepository.findAll(paging);

        //code to find all without paging. return the list variable
        //List<Question> list = new ArrayList<>();
        //questionRepository.findAll().forEach(question -> list.add(question));

        return pagedResult;
    }

    //TODO: Error exception incase list is 0
    public Question getRandomQuestion(){
        List<Question> questions = new ArrayList<>();
        questionRepository.findAll().iterator().forEachRemaining(questions::add);
        int max = questions.size() - 1;
        int min = 0;
        return questions.get((int) Math.floor(Math.random()*((max)-min+1)+min));
    }

    public boolean[] postAnswer(Question question, Answer answer){
        if (answer == null){
            answer = new Answer();
        }

        Set<Integer> set1 = new HashSet<>(List.of(question.getAnswer()));
        Set<Integer> set2 = new HashSet<>(List.of(answer.getAnswer()));

        boolean[] correct = new boolean[4];
        for (int i = 0; i < 4; i++){
            if (set1.contains(i) && set2.contains(i)){
                correct[i] = true;
            } else if (!set1.contains(i) && !set2.contains(i)){
                correct[i] = true;
            }
        }

        return correct;
    }

    public boolean questionExists(int id){
        Optional<Question> question = questionRepository.findById(id);
        return question.isPresent();
    }

    public boolean deleteQuestion(int id){
        Optional<Question> question = questionRepository.findById(id);
        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //String loginName = authentication.getName();


        //System.out.println((question.get().getUser().getEmail()));

        //String questionName = question.get().getUser().getEmail();

        /*
        if (loginName.equals(questionName)){
            questionRepository.delete(question.get());
            return true;
        }
        else {
            return false;
        }

         */
        questionRepository.delete(question.get());
        return true;
    }

    public boolean deleteAllQuestions(){
        questionRepository.deleteAll();
        return true;
    }

}
