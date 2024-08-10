package fact.it.barservice.controller;

import fact.it.barservice.dto.BarRequest;
import fact.it.barservice.dto.BarResponse;
import fact.it.barservice.service.BarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bar")
@RequiredArgsConstructor
public class BarController {
    private final BarService barService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createBar(@RequestBody BarRequest barRequest) {
        barService.createBar(barRequest);
        return "Bar Created!";
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<BarResponse> getAllBars() {
        return barService.getAllBars();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public BarResponse getBar(@RequestParam String name) {
        return barService.getBar(name);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateBar(@PathVariable("id") Long id, @RequestBody BarRequest barRequest) {
        boolean updated = barService.updateBar(id, barRequest);

        if (updated) {
            return ResponseEntity.ok("Bar updated!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bar does not exist");
        }
    }
}
