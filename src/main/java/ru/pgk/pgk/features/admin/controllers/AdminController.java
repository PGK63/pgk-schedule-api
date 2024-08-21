package ru.pgk.pgk.features.admin.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.pgk.pgk.features.admin.dto.AdminDto;
import ru.pgk.pgk.features.admin.dto.param.CreateAdminParams;
import ru.pgk.pgk.features.admin.entities.AdminEntity;
import ru.pgk.pgk.features.admin.mappers.AdminMapper;
import ru.pgk.pgk.features.admin.services.AdminService;

import java.util.List;

@RestController
@RequestMapping("admins")
@RequiredArgsConstructor
@SecurityRequirements(
        value = {
                @SecurityRequirement(name = "bearerAuth"),
                @SecurityRequirement(name = "X-API-KEY")
        }
)
public class AdminController {

    private final AdminService adminService;

    private final AdminMapper adminMapper;

    @GetMapping
    private List<AdminDto> getAll() {
        return adminMapper.toDto(adminService.getAll());
    }

    @PostMapping
    private AdminDto add(
            @RequestBody CreateAdminParams params
    ) {
        AdminEntity admin = adminService.add(params);
        return adminMapper.toDto(admin);
    }

    @DeleteMapping("{id}")
    private void deleteById(
            @PathVariable Integer id
    ) {
        adminService.delete(id);
    }
}
