package fact.it.barservice;

import fact.it.barservice.dto.BarRequest;
import fact.it.barservice.dto.BarResponse;
import fact.it.barservice.dto.BeerResponse;
import fact.it.barservice.model.Bar;
import fact.it.barservice.model.Beer;
import fact.it.barservice.repository.BarRepository;
import fact.it.barservice.service.BarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BarServiceApplicationTests {

    @InjectMocks
    private BarService barService;

    @Mock
    private BarRepository barRepository;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(barService, "beerServiceBaseUrl", "localhost:8080");
    }

    @Test
    public void testCreateBar() {
        // Arrange
        BarRequest barRequest = new BarRequest();
        barRequest.setName("Tizzel");
        barRequest.setCity("Tessenderlo-Ham");
        barRequest.setBeerNames(Arrays.asList("Cara"));

        BeerResponse beerResponse = new BeerResponse();
        beerResponse.setId("1");
        beerResponse.setName("Cara");
        beerResponse.setBrewer("Colruyt");
        beerResponse.setAlcoholPercentage(4.9);

        Bar bar = new Bar();
        bar.setId(1L);
        bar.setName("Tizzel");
        bar.setCity("Tessenderlo-Ham");
        Beer beer = new Beer();
        beer.setName("Cara");
        beer.setBrewer("Colruyt");
        beer.setAlcoholPercentage(4.9);
        beer.setId(1L);
        bar.setBeerList(Arrays.asList(beer));

        when(barRepository.save(any(Bar.class))).thenReturn(bar);

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        // Correct the URI stubbing
        when(requestHeadersUriSpec.uri(eq("http://localhost:8080/api/beer?beerName=Cara"))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToFlux(BeerResponse.class)).thenReturn(Flux.just(beerResponse));

        // Act
        barService.createBar(barRequest);

        // Assert
        verify(barRepository, times(1)).save(any(Bar.class));
    }

    @Test
    public void testGetAllBars() {
        //Arrange
        Bar bar = new Bar();
        bar.setId(1L);
        bar.setName("Tizzel");
        bar.setCity("Tessenderlo-Ham");

        Beer beer = new Beer();
        beer.setName("Cara");
        beer.setBrewer("Colruyt");
        beer.setAlcoholPercentage(4.9);
        beer.setId(1L);

        bar.setBeerList(Arrays.asList(beer));

        when(barRepository.findAll()).thenReturn(Arrays.asList(bar));

        //Act
        List<BarResponse> bars = barService.getAllBars();

        //Assert
        assertEquals(1, bars.size());
        assertEquals("Tizzel", bars.get(0).getName());
        assertEquals("Tessenderlo-Ham", bars.get(0).getCity());


        verify(barRepository, times(1)).findAll();
    }

    @Test
    public void testGetBar() {
        //Arrange
        Bar bar = new Bar();
        bar.setId(1L);
        bar.setName("Tizzel");
        bar.setCity("Tessenderlo-Ham");

        Beer beer = new Beer();
        beer.setName("Cara");
        beer.setBrewer("Colruyt");
        beer.setAlcoholPercentage(4.9);
        beer.setId(1L);

        bar.setBeerList(Arrays.asList(beer));

        when(barRepository.findByName("Tizzel")).thenReturn(bar);

        //Act
        BarResponse barResponse = barService.getBar("Tizzel");

        //Assert
        assertEquals("Tizzel", barResponse.getName());
        assertEquals("Tessenderlo-Ham", barResponse.getCity());

        verify(barRepository, times(1)).findByName(barResponse.getName());
    }

    @Test
    public void testUpdateBar() {
        // Arrange
        Long barId = 1L;
        BarRequest barRequest = new BarRequest();
        barRequest.setName("Tizzel");
        barRequest.setCity("Tessenderlo-Ham");
        barRequest.setBeerNames(Arrays.asList("Cara"));

        Bar existingBar = new Bar();
        existingBar.setId(barId);
        existingBar.setName("De Stip");
        existingBar.setCity("Ham");

        Beer beer = new Beer();
        beer.setName("Cara");
        beer.setBrewer("Colruyt");
        beer.setAlcoholPercentage(4.9);
        beer.setId(1L);

        existingBar.setBeerList(Arrays.asList(beer));


        when(barRepository.findById(barId)).thenReturn(Optional.of(existingBar));
        when(barRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0)); // Mock save to return the saved entity

        // Act
        boolean updated = barService.updateBar(barId, barRequest);

        // Assert
        assertTrue(updated);

        assertEquals("Tizzel", existingBar.getName());
        assertEquals("Tessenderlo-Ham", existingBar.getCity());

        verify(barRepository, times(1)).findById(barId);
        verify(barRepository, times(1)).save(existingBar);
    }





}
