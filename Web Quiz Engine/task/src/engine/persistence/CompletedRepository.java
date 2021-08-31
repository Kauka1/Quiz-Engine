package engine.persistence;

import engine.business.Completed;
import engine.business.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Completed Repo to be able to link up the services in the business layer with the database
 */
@Repository
public interface CompletedRepository extends CrudRepository<Completed, Integer>,
        PagingAndSortingRepository<Completed, Integer> {
    Page<Completed> findAllByUser(User user, Pageable pageable);
}
