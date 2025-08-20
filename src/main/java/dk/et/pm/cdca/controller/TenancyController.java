package dk.et.pm.cdca.controller;

import dk.et.pm.cdca.domain.Tenancy;
import dk.et.pm.cdca.service.TenancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tenancies")
public class TenancyController {

    private final TenancyService tenancyService;

    @GetMapping
    public List<Tenancy> getTenancies() {
        return tenancyService.getTenancies();
    }

    @GetMapping("/{tenancyId}")
    public Tenancy getTenancy(@PathVariable UUID tenancyId) {
        return tenancyService.getTenancy(tenancyId);
    }

}
