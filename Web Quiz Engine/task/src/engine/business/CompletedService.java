package engine.business;

import engine.persistence.CompletedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service to get the completed object information from the database
 */
@Service
public class CompletedService {

    @Autowired
    private CompletedRepository completedRepository;

    /**
     * Gets a paginated response of all the correct solutions a given user has done
     * @param user User to find info on
     * @param page Number of pages for the paginated response
     * @return A paginated response
     */
    public Page<Completed> getCompletedUser(User user, int page){
        Pageable paging = PageRequest.of(page, 10, Sort.by("completedAt").descending());
        Page<Completed> pagedResult = completedRepository.findAllByUser(user, paging);

        System.out.println(completedRepository.count());

        return pagedResult;
    }

    /**
     * Adds a completed object to the database
     * @param question Completed member of a Completed object
     * @param user User member of the Completed object
     */
    public void addCompleted(Question question, User user){
        Completed completed = new Completed(question, LocalDateTime.now(), user);
        completedRepository.save(completed);
    }
}
