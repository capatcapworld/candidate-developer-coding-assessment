package dk.et.pm.cdca;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RentCollectionService {

    private final RentCollectionRepository rentCollectionRepository;

    public List<RentCollection> getRentCollections(UUID tenancyId) {
        return rentCollectionRepository.findAllByTenancyId(tenancyId);
    }

    public RentCollection getRentCollection(UUID rentCollectionId) {
        return rentCollectionRepository.findById(rentCollectionId).orElseThrow();
    }

}
