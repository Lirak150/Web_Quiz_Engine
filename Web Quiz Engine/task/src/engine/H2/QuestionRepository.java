package engine.H2;

import engine.models.Question;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuestionRepository extends PagingAndSortingRepository<Question, Integer> {


    @Override
    <S extends Question> S save(S entity);

    @Override
    Optional<Question> findById(Integer id);

    @Override
    Iterable<Question> findAll();
}
