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
     */
    @Transactional
    public void bookAllOrderLinesForTenancy(UUID tenancyId) {
        var linesToBook = new ArrayList<OrderLine>();
        var rents = rentCollectionRepository.findAllByTenancyId(tenancyId);
        var today = LocalDate.now();

        for (var elem : rents) {
            var someLines = orderLineRepository.findAll().stream() // orderLineRepository.findAll() could be replaced with orderLineRepository.findAllByRentCollectionId() and then omit it in the filter
                                               .filter(line -> line.getRentCollectionId().equals(elem.getId())
                                               && !line.isBooked()
                                               && line.getBookingDate().isBefore(today))
                                               .toList();
            linesToBook.addAll(someLines);
        }

        try {
            if (!linesToBook.isEmpty()) {
                bookOrderLinesOnAccountingSystem(linesToBook);
            }
            for (OrderLine bookedLine : linesToBook) {
                bookedLine.setBooked(true);
                orderLineRepository.save(bookedLine);
            }
        } catch (Exception e) {
            log.error("Some error occured during booking for tenancyId: {}", tenancyId, e);
            throw e;
        }
    }

    public void bookOrderLinesOnAccountingSystem(List<OrderLine> orderLines) {
        // A placeholder method which would normally contain logic for sending order lines to a third party accounting
        // system. No code needs to be written in this method for this task.
    }

}
