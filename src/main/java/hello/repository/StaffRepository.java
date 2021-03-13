package hello.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import hello.entity.Staff;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Integer> {

}
