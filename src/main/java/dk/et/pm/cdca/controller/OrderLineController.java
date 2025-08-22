package dk.et.pm.cdca.controller;

import dk.et.pm.cdca.domain.OrderLine;
import dk.et.pm.cdca.service.OrderLineService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order-lines")
public class OrderLineController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final OrderLineService orderLineService;

    @GetMapping
    public List<OrderLine> getOrderLines(@RequestParam UUID rentCollectionId) {
        return orderLineService.getOrderLines(rentCollectionId);
    }

    @PutMapping
    public ResponseEntity<String> bookAllOrderLinesForTenancy(@RequestParam UUID tenancyId) {
        log.info("bookAllOrderLinesForTenancy called with tenancyId: " + tenancyId);
        orderLineService.bookAllOrderLinesForTenancy(tenancyId);
        return ResponseEntity.ok("All available order lines booked successfully for tenancyId: " + tenancyId);
    }

}
