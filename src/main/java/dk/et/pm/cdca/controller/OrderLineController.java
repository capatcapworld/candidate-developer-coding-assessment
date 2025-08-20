package dk.et.pm.cdca.controller;

import dk.et.pm.cdca.domain.OrderLine;
import dk.et.pm.cdca.service.OrderLineService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order-lines")
public class OrderLineController {

    private final OrderLineService orderLineService;

    @GetMapping
    public List<OrderLine> getOrderLines(@RequestParam UUID rentCollectionId) {
        return orderLineService.getOrderLines(rentCollectionId);
    }

}
