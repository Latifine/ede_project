package fact.it.beerservice;

import fact.it.beerservice.dto.BeerRequest;
import fact.it.beerservice.dto.BeerResponse;
import fact.it.beerservice.model.Beer;
import fact.it.beerservice.repository.BeerRepository;
import fact.it.beerservice.service.BeerService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class BeerServiceApplicationTests {

    @InjectMocks
    private BeerService beerService;

    @Mock
    private BeerRepository beerRepository;

    @Test
    public void testCreateBeer() {
        //Arrange
        BeerRequest beerRequest = new BeerRequest();
        beerRequest.setName("Stella");
        beerRequest.setBrewer("AB-InBev");
        beerRequest.setAlcoholPercentage(5.2);

        //Act
        beerService.createBeer(beerRequest);

        //Assert
        verify(beerRepository, times(1)).save(any(Beer.class));
    }

    @Test
    public void testGetAllBeers() {
        //Arrange
        Beer beer = new Beer();
        beer.setId("1");
        beer.setName("Cara");
        beer.setBrewer("Colruyt");
        beer.setAlcoholPercentage(4.9);

        when(beerRepository.findAll()).thenReturn(Arrays.asList(beer));

        //Act
        List<BeerResponse> beers = beerService.getAllBeers();

        //Assert
        assertEquals(1, beers.size());
        assertEquals(4.9, beers.get(0).getAlcoholPercentage());
        assertEquals("Cara", beers.get(0).getName());
        assertEquals("Colruyt", beers.get(0).getBrewer());

        verify(beerRepository, times(1)).findAll();
    }

    @Test
    public void testGetBeers() {
        //Arrange
        Beer beer = new Beer();
        beer.setId("1");
        beer.setName("Cara");
        beer.setBrewer("Colruyt");
        beer.setAlcoholPercentage(4.9);

        when(beerRepository.findByNameIn(Arrays.asList("Cara"))).thenReturn(Arrays.asList(beer));

        //Act
        List<BeerResponse> beers = beerService.getBeers(Arrays.asList("Cara"));

        //Assert
        assertEquals(1, beers.size());
        assertEquals(4.9, beers.get(0).getAlcoholPercentage());
        assertEquals("Cara", beers.get(0).getName());
        assertEquals("Colruyt", beers.get(0).getBrewer());

        verify(beerRepository, times(1)).findByNameIn(Arrays.asList(beer.getName()));
    }

    @Test
    public void testDeleteBeer() {
        // Arrange
        Beer beer = new Beer();
        beer.setId("1");
        beer.setName("Cara");
        beer.setBrewer("Colruyt");
        beer.setAlcoholPercentage(4.9);

        when(beerRepository.existsById("1")).thenReturn(true);

        // Act
        beerService.deleteBeer("1");

        // Assert
        verify(beerRepository, times(1)).existsById("1");
        verify(beerRepository, times(1)).deleteById("1");
    }



}
