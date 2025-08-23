package dk.et.pm.cdca;

import dk.et.pm.cdca.domain.OrderLine;
import dk.et.pm.cdca.domain.RentCollection;
import dk.et.pm.cdca.domain.Tenancy;
import dk.et.pm.cdca.repository.OrderLineRepository;
import dk.et.pm.cdca.repository.RentCollectionRepository;
import dk.et.pm.cdca.service.OrderLineService;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderLineServiceTest {

    String tenancyIdGitteStr  = "10000000-0000-8000-8000-000000000000";
    String tenancyIdJonStr    = "20000000-0000-8000-8000-000000000000";
    String tenancyIdMarcStr   = "30000000-0000-8000-8000-000000000000";
    String tenancyIdMarcusStr = "40000000-0000-8000-8000-000000000000";

    UUID tenancyIdGitteUUID  = UUID.fromString(tenancyIdGitteStr);
    UUID tenancyIdJonUUID    = UUID.fromString(tenancyIdJonStr);
    UUID tenancyIdMarcUUID   = UUID.fromString(tenancyIdMarcStr);
    UUID tenancyIdMarcusUUID = UUID.fromString(tenancyIdMarcusStr);

    @Spy
    @InjectMocks
    private OrderLineService orderLineService;

    @Mock
    private RentCollectionRepository rentCollectionRepository;

    @Mock
    private OrderLineRepository orderLineRepository;

    //@formatter:off
    private final List<Tenancy> tenancies = List.of(
            createTenancy(tenancyIdGitteStr, "Gitte" ),
            createTenancy(tenancyIdJonStr, "Jon"   ),
            createTenancy(tenancyIdMarcStr, "Marc"  ),
            createTenancy(tenancyIdMarcusStr, "Marcus")
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
    }

    @Test
    public void bookAllOrderLinesForTenancyTest() {
        when(orderLineRepository.findAll()).thenReturn(orderLines);
        when(rentCollectionRepository.findAllByTenancyId(tenancyIdGitteUUID)).thenReturn(rentCollections.stream().filter((rent) -> rent.getTenancyId().equals(tenancyIdGitteUUID)).toList());
        when(rentCollectionRepository.findAllByTenancyId(tenancyIdJonUUID)).thenReturn(rentCollections.stream().filter((rent) -> rent.getTenancyId().equals(tenancyIdJonUUID)).toList());
        when(rentCollectionRepository.findAllByTenancyId(tenancyIdMarcUUID)).thenReturn(rentCollections.stream().filter((rent) -> rent.getTenancyId().equals(tenancyIdMarcUUID)).toList());
        when(rentCollectionRepository.findAllByTenancyId(tenancyIdMarcusUUID)).thenReturn(rentCollections.stream().filter((rent) -> rent.getTenancyId().equals(tenancyIdMarcusUUID)).toList());

        orderLineService.bookAllOrderLinesForTenancy(tenancyIdGitteUUID);
        orderLineService.bookAllOrderLinesForTenancy(tenancyIdJonUUID);
        orderLineService.bookAllOrderLinesForTenancy(tenancyIdMarcUUID);
        orderLineService.bookAllOrderLinesForTenancy(tenancyIdMarcusUUID);

        verify(orderLineService, times(3)).bookOrderLinesOnAccountingSystem(any());
        verify(orderLineRepository, times(6)).save(any());
    }

    @Test
    public void shouldNotBookOrSaveWhenRentCollectionRepositoryThrowsException() {
        when(rentCollectionRepository.findAllByTenancyId(tenancyIdGitteUUID)).thenThrow(new DataAccessResourceFailureException("Database Exception") );

        // Assert that the RunTimeException is thrown
        assertThrows(DataAccessResourceFailureException.class, () -> orderLineService.bookAllOrderLinesForTenancy(tenancyIdGitteUUID));

        verify(orderLineService, never()).bookOrderLinesOnAccountingSystem(any());
        verify(orderLineRepository, never()).save(any());
    }

    @Test
    public void shouldNotBookOrSaveWhenOrderLineRepositoryThrowsException() {
        when(rentCollectionRepository.findAllByTenancyId(tenancyIdGitteUUID)).thenReturn(rentCollections.stream().filter((rent) -> rent.getTenancyId().equals(tenancyIdGitteUUID)).toList());
        when(orderLineRepository.findAll()).thenThrow(new ConstraintViolationException("Database Exception", new SQLException("SQL Exception"),"ConstraintName"));

        // Assert that the ConstraintViolationException is thrown
        assertThrows(ConstraintViolationException.class, () -> orderLineService.bookAllOrderLinesForTenancy(tenancyIdGitteUUID));

        verify(orderLineService, never()).bookOrderLinesOnAccountingSystem(any());
        verify(orderLineRepository, never()).save(any());
    }


    @Test
    public void shouldNotBookOrSaveWhenOrderLineRepositorySaveThrowsException() {
        when(orderLineRepository.findAll()).thenReturn(orderLines);
        when(rentCollectionRepository.findAllByTenancyId(tenancyIdGitteUUID)).thenReturn(rentCollections.stream().filter((rent) -> rent.getTenancyId().equals(tenancyIdGitteUUID)).toList());
        when(orderLineRepository.save(any())).thenThrow(new DataIntegrityViolationException("DataIntegrity Exception"));

        // Assert that the ConstraintViolationException is thrown
        assertThrows(DataIntegrityViolationException.class, () -> orderLineService.bookAllOrderLinesForTenancy(tenancyIdGitteUUID));

        verify(orderLineService, never()).bookOrderLinesOnAccountingSystem(any());
        verify(orderLineRepository, atMostOnce()).save(any());
    }

}
