package dk.et.pm.cdca.repository;

import dk.et.pm.cdca.domain.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface OrderLineRepository extends JpaRepository<OrderLine, UUID> {

    List<OrderLine> findAllByRentCollectionIdAndBookedAndBookingDateBefore(UUID rentCollectionId, Boolean booked, LocalDate bookingDate);
    List<OrderLine> findAllByRentCollectionId(UUID rentCollectionId);

}
