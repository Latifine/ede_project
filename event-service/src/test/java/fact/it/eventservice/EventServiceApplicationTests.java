package fact.it.eventservice;

import fact.it.eventservice.dto.BarResponse;
import fact.it.eventservice.dto.EventRequest;
import fact.it.eventservice.dto.EventResponse;
import fact.it.eventservice.model.Bar;
import fact.it.eventservice.model.Event;
import fact.it.eventservice.repository.EventRepository;
import fact.it.eventservice.service.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceApplicationTests {

    @InjectMocks
    private EventService eventService;

    @Mock
    private EventRepository eventRepository;

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
        ReflectionTestUtils.setField(eventService, "barServiceBaseUrl", "localhost:8081");
    }

    @Test
    public void testCreateEvent() {
        // Arrange
        EventRequest eventRequest = new EventRequest();
        eventRequest.setEventName("Rockfuif");
        eventRequest.setEventDate(LocalDate.of(2024, 8, 24));
        eventRequest.setBarName("Chiro");

        BarResponse barResponse = new BarResponse();
        barResponse.setId(1L);
        barResponse.setName("Chiro");
        barResponse.setCity("Klein-Vorst");

        Event event = new Event();
        event.setId(1L);
        event.setEventName("Rockfuif");
        event.setEventDate(LocalDate.of(2024, 8, 24));

        Bar bar = new Bar();
        bar.setId(1L);
        bar.setName("Chiro");
        bar.setCity("Klein-Vorst");
        event.setBar(bar);

        when(eventRepository.save(any(Event.class))).thenReturn(event);

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(eq("http://localhost:8081/api/bar"), any(Function.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(BarResponse.class)).thenReturn(Mono.just(barResponse));

        // Act
        eventService.createEvent(eventRequest);

        // Assert
        verify(eventRepository, times(1)).save(any(Event.class));
    }

    @Test
    public void testGetAllBars() {
        //Arrange
        Event event = new Event();
        event.setId(1L);
        event.setEventName("Rockfuif");
        event.setEventDate(LocalDate.of(2024,8,24));

        Bar bar = new Bar();
        bar.setCity("Klein-Vorst");
        bar.setName("Chiro");
        bar.setId(1L);

        event.setBar(bar);

        when(eventRepository.findAll()).thenReturn(Arrays.asList(event));

        //Act
        List<EventResponse> events = eventService.getAllEvents();

        //Assert
        assertEquals(1, events.size());
        assertEquals("Rockfuif", events.get(0).getEventName());
        assertEquals(LocalDate.of(2024,8,24), events.get(0).getEventDate());
        assertEquals(bar, events.get(0).getBar());


        verify(eventRepository, times(1)).findAll();
    }





}
