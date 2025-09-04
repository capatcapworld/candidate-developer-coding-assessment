package dk.et.pm.cdca.service;

import dk.et.pm.cdca.domain.OrderLine;
import dk.et.pm.cdca.repository.RentCollectionRepository;
import dk.et.pm.cdca.repository.OrderLineRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderLineService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final RentCollectionRepository rentCollectionRepository;
    private final OrderLineRepository orderLineRepository;

    public List<OrderLine> getOrderLines(UUID rentCollectionId) {
        return orderLineRepository.findAllByRentCollectionId(rentCollectionId);
    }

    /**
     * Books all order lines for a tenancy which have not already been booked, and have a booking date before today.
     *
     * The initial version only had  filter(line -> line.getRentCollectionId() == elem.getId()) where '==' compares on addresses.
     * The filtering is removed and replaced with sql.
     */
    @Transactional
    public void bookAllOrderLinesForTenancy(UUID tenancyId) {
        var linesToBook = new ArrayList<OrderLine>();
        var today = LocalDate.now();

        try {
            var rents = rentCollectionRepository.findAllByTenancyId(tenancyId);
            for (var elem : rents) {
                linesToBook.addAll(orderLineRepository.findAllByRentCollectionIdAndBookedAndBookingDateBefore(elem.getId(), false, today));
            }

            for (OrderLine bookedLine : linesToBook) {
                bookedLine.setBooked(true);
                orderLineRepository.save(bookedLine);
            }

            if (!linesToBook.isEmpty()) {
                bookOrderLinesOnAccountingSystem(linesToBook);
            }
        } catch (Exception e) {
            log.error("Some error occurred during booking for tenancyId: {}", tenancyId, e);
            throw e;
        }
    }

    public void bookOrderLinesOnAccountingSystem(List<OrderLine> orderLines) {
        // A placeholder method which would normally contain logic for sending order lines to a third party accounting
        // system. No code needs to be written in this method for this task.
    }

}
