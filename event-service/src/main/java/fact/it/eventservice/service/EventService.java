package fact.it.eventservice.service;

import fact.it.eventservice.dto.BarResponse;
import fact.it.eventservice.dto.EventRequest;
import fact.it.eventservice.dto.EventResponse;
import fact.it.eventservice.model.Bar;
import fact.it.eventservice.model.Event;
import fact.it.eventservice.repository.EventRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final WebClient webClient;

    @Value("${BAR_SERVICE_BASEURL:localhost:8081}")
    private String barServiceBaseUrl;

    @PostConstruct
    public void loadData() {
        if(eventRepository.count() <= 0) {
            Bar bar = new Bar();
            bar.setName("De Flagrant");
            bar.setCity("Klein-Vorst");

            Event event = new Event();
            event.setEventDate(LocalDate.of(2024, 8, 24));
            event.setEventName("Dance with the Duvel");
            event.setBar(bar);

            eventRepository.save(event);
        }
    }

    public void createEvent(EventRequest eventRequest) {
        Event event = new Event();
        event.setEventName(eventRequest.getEventName());
        event.setEventDate(eventRequest.getEventDate());

        BarResponse bar = webClient.get()
                .uri("http://" + barServiceBaseUrl + "/api/bar",
                        uriBuilder -> uriBuilder.queryParam("name", eventRequest.getBarName()).build())
                .retrieve()
                .bodyToMono(BarResponse.class)
                .block();

        assert bar != null;
        event.setBar(mapBarResponseToEntity(bar));
        eventRepository.save(event);
    }

    private Bar mapBarResponseToEntity(BarResponse barResponse) {
        Bar bar = new Bar();
        bar.setName(barResponse.getName());
        bar.setCity(barResponse.getCity());
        return bar;
    }

    public List<EventResponse> getAllEvents() {
        List<Event> events = eventRepository.findAll();

        return events.stream()
                .map(event -> new EventResponse(
                        event.getEventName(),
                        event.getEventDate(),
                        event.getBar()
                ))
                .collect(Collectors.toList());
    }
}
