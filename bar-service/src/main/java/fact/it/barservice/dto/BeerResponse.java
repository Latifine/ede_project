package fact.it.barservice.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BeerResponse {
    private String id;
    private String name;
    private String brewer;
    private Double alcoholPercentage;
}