package fact.it.beerservice.service;


import fact.it.beerservice.dto.BeerRequest;
import fact.it.beerservice.dto.BeerResponse;
import fact.it.beerservice.model.Beer;
import fact.it.beerservice.repository.BeerRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BeerService {

    private final BeerRepository beerRepository;

    @PostConstruct
    public void loadData(){
        if(beerRepository.count()<= 0){
            Beer beer1 = Beer.builder()
                    .name("Jupiler")
                    .brewer("AB InBev")
                    .alcoholPercentage(5.2)
                    .build();
            Beer beer2 = Beer.builder()
                    .name("Duvel")
                    .brewer("Moortgat")
                    .alcoholPercentage(8.5)
                    .build();
            Beer beer3 = Beer.builder()
                    .name("Maes")
                    .brewer("Alken-Maes")
                    .alcoholPercentage(5.2)
                    .build();
            Beer beer4 = Beer.builder()
                    .name("Primus")
                    .brewer("Haacht")
                    .alcoholPercentage(5.2)
                    .build();

            beerRepository.save(beer1);
            beerRepository.save(beer2);
            beerRepository.save(beer3);
            beerRepository.save(beer4);
        }
    }

    public void createBeer(BeerRequest beerRequest){
        Beer beer = Beer.builder()
                .name(beerRequest.getName())
                .brewer(beerRequest.getBrewer())
                .alcoholPercentage(beerRequest.getAlcoholPercentage())
                .build();

        beerRepository.save(beer);
    }

    private BeerResponse mapToBeerResponse(Beer beer) {
        return BeerResponse.builder()
                .id(beer.getId())
                .name(beer.getName())
                .brewer(beer.getBrewer())
                .alcoholPercentage(beer.getAlcoholPercentage())
                .build();
    }

    public List<BeerResponse> getAllBeers() {
        List<Beer> beers = beerRepository.findAll();

        return beers.stream().map(this::mapToBeerResponse).toList();
    }

    public void deleteBeer(String beerId){
        if(beerRepository.existsById(beerId)){
            beerRepository.deleteById(beerId);
        }
    }

    public List<BeerResponse> getBeers(List<String> beerName) {
        List<Beer> beers = beerRepository.findByNameIn(beerName);

        return beers.stream().map(this::mapToBeerResponse).toList();
    }

    public BeerResponse getBeer(String name) {
        Beer beer = beerRepository.findByName(name);
        return mapToBeerResponse(beer);
    }
}
