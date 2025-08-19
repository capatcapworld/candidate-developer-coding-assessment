package dk.et.pm.cdca;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TenancyService {

    private final TenancyRepository tenancyRepository;

    public List<Tenancy> getTenancies() {
        return tenancyRepository.findAll();
    }

    public Tenancy getTenancy(UUID tenancyId) {
        return tenancyRepository.findById(tenancyId).orElseThrow();
    }

}
