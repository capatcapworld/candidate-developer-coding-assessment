package dk.et.pm.cdca;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TenancyRepository extends JpaRepository<Tenancy, UUID> {
}
