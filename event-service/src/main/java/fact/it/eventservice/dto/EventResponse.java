package fact.it.eventservice.dto;

import fact.it.eventservice.model.Bar;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventResponse {
    private String eventName;
    private LocalDate eventDate;
    private Bar bar;
}
