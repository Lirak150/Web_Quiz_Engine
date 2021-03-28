package engine.H2;

import engine.models.SolvedQuestion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolvedQuestionRepository extends PagingAndSortingRepository<SolvedQuestion, Long> {

    Page<SolvedQuestion> findAllByUserIdEquals(long userId, Pageable pageable);
}
