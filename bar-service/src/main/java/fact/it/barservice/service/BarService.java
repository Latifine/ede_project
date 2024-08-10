package fact.it.barservice.service;

import fact.it.barservice.dto.BarRequest;
import fact.it.barservice.dto.BarResponse;
import fact.it.barservice.dto.BeerDto;
import fact.it.barservice.dto.BeerResponse;
import fact.it.barservice.model.Bar;
import fact.it.barservice.model.Beer;
import fact.it.barservice.repository.BarRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BarService {
    private final BarRepository barRepository;
    private final WebClient webClient;
    @Value("${BEER_SERVICE_BASEURL:localhost:8080}")
    private String beerServiceBaseUrl;

    @PostConstruct
    public void loadData() {
        if(barRepository.count() <= 0) {
            Bar bar = new Bar();
            bar.setName("Flagrant");
            bar.setCity("Klein-Vorst");

            Bar bar2 = new Bar();
            bar2.setName("Het Land Van Kafka");
            bar2.setCity("Tessenderlo");

            barRepository.save(bar);
            barRepository.save(bar2);
        }
    }

    public void createBar(BarRequest barRequest){
        Bar bar = new Bar();
        bar.setName(barRequest.getName());
        bar.setCity(barRequest.getCity());

        List<String> beerNames = barRequest.getBeerNames().stream().toList();
        String commaSeparatedNames = String.join(",", beerNames);

        // Build URI
        String uri = "http://" + beerServiceBaseUrl + "/api/beer";
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("beerName", commaSeparatedNames);

        // Print the URI
        System.out.println("WebClient URI: " + uriBuilder.toUriString());

        List<BeerResponse> beers = webClient.get()
                .uri(uriBuilder.toUriString())
                .retrieve()
                .bodyToFlux(BeerResponse.class)
                .collectList()
                .block();

        assert beers != null;
        List<Beer> beerList = beers.stream()
                .map(this::mapBeerResponseToEntity)
                .toList();

        bar.setBeerList(beerList);
        barRepository.save(bar);
    }

    private Beer mapBeerResponseToEntity(BeerResponse beerResponse) {
        Beer beer = new Beer();
        beer.setName(beerResponse.getName());
        beer.setBrewer(beerResponse.getBrewer());
        beer.setAlcoholPercentage(beerResponse.getAlcoholPercentage());
        return beer;
    }

    @Transactional
    public boolean updateBar(Long id, BarRequest barRequest) {
        Optional<Bar> optionalBar = barRepository.findById(id);

        if (optionalBar.isPresent()) {
            Bar existingBar = optionalBar.get();
            existingBar.setName(barRequest.getName());
            existingBar.setCity(barRequest.getCity());
            barRepository.save(existingBar);
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public List<BarResponse> getAllBars() {
        List<Bar> bars = barRepository.findAll();

        return bars.stream().map(this::mapToBarResponse).toList();
    }

    private BarResponse mapToBarResponse(Bar bar) {
        List<BeerDto> beerDtoList = bar.getBeerList().stream()
                .map(this::mapBeerToDto)
                .toList();

        return BarResponse.builder()
                .id(bar.getId())
                .name(bar.getName())
                .city(bar.getCity())
                .beerDtoList(beerDtoList)
                .build();
    }

    private BeerDto mapBeerToDto(Beer beer) {
        return new BeerDto(beer.getName(), beer.getBrewer(), beer.getAlcoholPercentage());
    }

    @Transactional
    public BarResponse getBar(String name) {
        Bar bar = barRepository.findByName(name);

        if (bar != null) {
            return mapToBarMatchResponse(bar);
        } else {
            return new BarResponse();
        }
    }

    private BarResponse mapToBarMatchResponse(Bar bar) {
        return BarResponse.builder()
                .id(bar.getId())
                .name(bar.getName())
                .city(bar.getCity())
                .build();
    }







}
