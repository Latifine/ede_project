package fact.it.beerservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "beer")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Beer {
    private String id;
    private String name;
    private String brewer;
    private Double alcoholPercentage;
}