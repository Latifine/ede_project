package fact.it.beerservice.repository;

import fact.it.beerservice.model.Beer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BeerRepository extends MongoRepository<Beer, String> {
    List<Beer> findByNameIn(List<String> beerName);
}
