package fact.it.beerservice.controller;

import fact.it.beerservice.dto.BeerRequest;
import fact.it.beerservice.dto.BeerResponse;
import fact.it.beerservice.service.BeerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/beer")
@RequiredArgsConstructor
public class BeerController {
    private final BeerService beerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createBeer(@RequestBody BeerRequest beerRequest) {
        beerService.createBeer(beerRequest);
        return "Beer Created!";
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<BeerResponse> getAllBeers() {
        return beerService.getAllBeers();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BeerResponse> getBeers(@RequestParam List<String> beerName) {
        return beerService.getBeers(beerName);
    }

    @DeleteMapping("/{beerId}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteBeer(@PathVariable String beerId){
        beerService.deleteBeer(beerId);
        return "Beer deleted!";
    }


}
