package engine.persistence;

import engine.business.Question;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;

/**
 * Question repo to link up the question service and the database
 */
@Repository
public interface QuestionRepository extends CrudRepository<Question, Integer>,
        PagingAndSortingRepository<Question, Integer> {

}
