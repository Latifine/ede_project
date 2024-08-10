package fact.it.eventservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventRequest {
    private String barName;
    private String eventName;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate eventDate;
}
