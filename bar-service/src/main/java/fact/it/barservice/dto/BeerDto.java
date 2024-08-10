package fact.it.barservice.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BeerDto {
    private String name;
    private String brewer;
    private Double alcoholPercentage;
}
