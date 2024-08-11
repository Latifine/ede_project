package fact.it.beerservice.repository;

import fact.it.beerservice.model.Beer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BeerRepository extends MongoRepository<Beer, String> {
    Beer findByName(String name);
    List<Beer> findByNameIn(List<String> beerName);
}
