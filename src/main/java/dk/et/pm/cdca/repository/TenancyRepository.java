package dk.et.pm.cdca.repository;

import dk.et.pm.cdca.domain.Tenancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TenancyRepository extends JpaRepository<Tenancy, UUID> {
}
