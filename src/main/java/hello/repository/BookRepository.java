package hello.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hello.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

}
