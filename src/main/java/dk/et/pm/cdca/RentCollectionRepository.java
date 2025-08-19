package dk.et.pm.cdca;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RentCollectionRepository extends JpaRepository<RentCollection, UUID> {

    List<RentCollection> findAllByTenancyId(UUID tenancyId);

}
