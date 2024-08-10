package fact.it.barservice.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BarResponse {
    private Long id;
    private String name;
    private String city;
    private List<BeerDto> beerDtoList;
}