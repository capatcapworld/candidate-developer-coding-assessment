package dk.et.pm.cdca.controller;

import dk.et.pm.cdca.domain.RentCollection;
import dk.et.pm.cdca.service.RentCollectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rent-collections")
public class RentCollectionController {

    private final RentCollectionService rentCollectionService;

    @GetMapping
    public List<RentCollection> getRentCollections(@RequestParam UUID tenancyId) {
        return rentCollectionService.getRentCollections(tenancyId);
    }

    @GetMapping("/{rentCollectionId}")
    public RentCollection getRentCollection(@PathVariable UUID rentCollectionId) {
        return rentCollectionService.getRentCollection(rentCollectionId);
    }

}
