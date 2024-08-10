package fact.it.barservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BarRequest {
    private String name;
    private String city;
    private List<String> beerNames;
}

