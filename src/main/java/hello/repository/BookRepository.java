package hello.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.querydsl.core.types.Predicate;
import hello.entity.Book;

@Repository
public interface BookRepository
        extends JpaRepository<Book, Integer>, QuerydslPredicateExecutor<Book> {

    Page<Book> findAll(Predicate predicate, Pageable pageable);
}
