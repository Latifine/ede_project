package fact.it.barservice.repository;
import fact.it.barservice.model.Bar;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface BarRepository extends JpaRepository<Bar, Long> {
    Bar findByName(String name);
}