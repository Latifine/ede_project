package fact.it.barservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "bar")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String city;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "bar_beer",
            joinColumns = @JoinColumn(name = "bar_id"),
            inverseJoinColumns = @JoinColumn(name = "beer_id")
    )
    private List<Beer> beerList;
}
