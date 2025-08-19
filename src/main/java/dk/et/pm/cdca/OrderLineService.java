package dk.et.pm.cdca;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderLineService {

    private final RentCollectionRepository rentCollectionRepository;
    private final OrderLineRepository orderLineRepository;

    public List<OrderLine> getOrderLines(UUID rentCollectionId) {
        return orderLineRepository.findAllByRentCollectionId(rentCollectionId);
    }

    /**
     * Books all order lines for a tenancy which have not already been booked, and have a booking date before today.
     */
    public void bookAllOrderLinesForTenancy(UUID tenancyId) {
        var linesToBook = new ArrayList<OrderLine>();
        var rents = rentCollectionRepository.findAll();

        for (var elem : rents) {
            var someLines = orderLineRepository.findAll().stream()
                    .filter(line -> line.getRentCollectionId() == elem.getId()).toList();
            linesToBook.addAll(someLines);
        }

        try {
            bookOrderLinesOnAccountingSystem(linesToBook);
            for (OrderLine bookedLine : linesToBook) {
                bookedLine.setBooked(true);
                orderLineRepository.save(bookedLine);
            }
        } catch (Exception e) {
        }
    }

    public void bookOrderLinesOnAccountingSystem(List<OrderLine> orderLines) {
        // A placeholder method which would normally contain logic for sending order lines to a third party accounting
        // system. No code needs to be written in this method for this task.
    }

}
