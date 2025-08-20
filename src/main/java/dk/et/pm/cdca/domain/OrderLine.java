package dk.et.pm.cdca.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Entity
public class OrderLine {

    @Id
    private UUID id;
    private UUID rentCollectionId;
    private String name;
    private BigDecimal amount;
    private LocalDate bookingDate;
    private boolean booked;

}
