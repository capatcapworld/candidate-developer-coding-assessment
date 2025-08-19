package dk.et.pm.cdca;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderLineServiceTest {

    @Spy
    @InjectMocks
    private OrderLineService orderLineService;

    @Mock
    private RentCollectionRepository rentCollectionRepository;

    @Mock
    private OrderLineRepository orderLineRepository;

    //@formatter:off
    private final List<Tenancy> tenancies = List.of(
            createTenancy("10000000-0000-8000-8000-000000000000", "Gitte" ),
            createTenancy("20000000-0000-8000-8000-000000000000", "Jon"   ),
            createTenancy("30000000-0000-8000-8000-000000000000", "Marc"  ),
            createTenancy("40000000-0000-8000-8000-000000000000", "Marcus")
    );

    private final List<RentCollection> rentCollections = List.of(
            createRentCollection("11000000-0000-8000-8000-000000000000", "10000000-0000-8000-8000-000000000000", "2025-01-01"),
            createRentCollection("12000000-0000-8000-8000-000000000000", "10000000-0000-8000-8000-000000000000", "2025-02-01"),
            createRentCollection("13000000-0000-8000-8000-000000000000", "10000000-0000-8000-8000-000000000000", "2025-03-01"),
            createRentCollection("22000000-0000-8000-8000-000000000000", "20000000-0000-8000-8000-000000000000", "2025-02-01"),
            createRentCollection("23000000-0000-8000-8000-000000000000", "20000000-0000-8000-8000-000000000000", "2025-03-01"),
            createRentCollection("33000000-0000-8000-8000-000000000000", "30000000-0000-8000-8000-000000000000", "2025-03-01")
    );

    private final List<OrderLine> orderLines = List.of(
            createOrderLine("11100000-0000-8000-8000-000000000000", "11000000-0000-8000-8000-000000000000", "Rent"   , 10000, "2025-01-01", true ),
            createOrderLine("11200000-0000-8000-8000-000000000000", "11000000-0000-8000-8000-000000000000", "Water"  ,  1000, "2025-01-01", true ),
            createOrderLine("11300000-0000-8000-8000-000000000000", "11000000-0000-8000-8000-000000000000", "Heating",   100, "2025-01-01", true ),
            createOrderLine("12100000-0000-8000-8000-000000000000", "12000000-0000-8000-8000-000000000000", "Rent"   , 10000, "2025-02-01", true ),
            createOrderLine("12200000-0000-8000-8000-000000000000", "12000000-0000-8000-8000-000000000000", "Water"  ,  1000, "2025-02-01", true ),
            createOrderLine("12300000-0000-8000-8000-000000000000", "12000000-0000-8000-8000-000000000000", "Heating",   100, "2025-02-01", true ),
            createOrderLine("13100000-0000-8000-8000-000000000000", "13000000-0000-8000-8000-000000000000", "Rent"   , 10000, "2025-03-01", false),
            createOrderLine("13200000-0000-8000-8000-000000000000", "13000000-0000-8000-8000-000000000000", "Water"  ,  1000, "2025-03-01", false),
            createOrderLine("13300000-0000-8000-8000-000000000000", "13000000-0000-8000-8000-000000000000", "Heating",   100, "2025-03-01", false),
            createOrderLine("22100000-0000-8000-8000-000000000000", "22000000-0000-8000-8000-000000000000", "Rent"   , 10000, "2025-02-01", true ),
            createOrderLine("22200000-0000-8000-8000-000000000000", "22000000-0000-8000-8000-000000000000", "Water"  ,  1000, "2025-02-01", true ),
            createOrderLine("23100000-0000-8000-8000-000000000000", "23000000-0000-8000-8000-000000000000", "Rent"   , 10000, "2025-03-01", false),
            createOrderLine("23200000-0000-8000-8000-000000000000", "23000000-0000-8000-8000-000000000000", "Water"  ,  1000, "2025-03-01", false),
            createOrderLine("33100000-0000-8000-8000-000000000000", "33000000-0000-8000-8000-000000000000", "Rent"   , 10000, "2025-03-01", false)
    );
    //@formatter:on

    private static Tenancy createTenancy(String tenancyId, String name) {
        return new Tenancy(UUID.fromString(tenancyId), name);
    }

    private static RentCollection createRentCollection(String rentCollectionId, String tenancyId, String date) {
        return new RentCollection(UUID.fromString(rentCollectionId), UUID.fromString(tenancyId), LocalDate.parse(date));
    }

    private static OrderLine createOrderLine(String orderLineId, String rentCollectionId, String name, int amount, String bookingDate, boolean booked) {
        return new OrderLine(UUID.fromString(orderLineId), UUID.fromString(rentCollectionId), name, new BigDecimal(amount), LocalDate.parse(bookingDate), booked);
    }

    @BeforeEach
    public void beforeEach() {
        when(rentCollectionRepository.findAll()).thenReturn(rentCollections);
        when(orderLineRepository.findAll()).thenReturn(orderLines);
    }

    @Test
    public void bookAllOrderLinesForTenancyTest() {
        orderLineService.bookAllOrderLinesForTenancy(null);
        verify(orderLineService, times(1)).bookOrderLinesOnAccountingSystem(any());
    }

}
