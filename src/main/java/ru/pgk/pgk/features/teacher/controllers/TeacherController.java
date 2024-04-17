package ru.pgk.pgk.features.teacher.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.pgk.pgk.common.dto.PageDto;
import ru.pgk.pgk.common.exceptions.ForbiddenException;
import ru.pgk.pgk.features.teacher.dto.TeacherDetailsDto;
import ru.pgk.pgk.features.teacher.dto.params.AddOrUpdateTeacherParams;
import ru.pgk.pgk.features.teacher.mappers.TeacherDetailsMapper;
import ru.pgk.pgk.features.teacher.service.TeacherService;
import ru.pgk.pgk.features.teacher.service.queries.TeacherQueriesService;
import ru.pgk.pgk.security.apiKey.GlobalSecurityRequirement;
import ru.pgk.pgk.security.jwt.JwtEntity;

@RestController
@RequestMapping("teachers")
@GlobalSecurityRequirement
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;
    private final TeacherQueriesService teacherQueriesService;

    private final TeacherDetailsMapper teacherDetailsMapper;

    @GetMapping
    private PageDto<TeacherDetailsDto> getAll(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") Integer offset
    ) {
        Page<TeacherDetailsDto> teachers = teacherQueriesService.getAll(name, offset).map(teacherDetailsMapper::toDto);
        return PageDto.fromPage(teachers);
    }

    @PostMapping
    @SecurityRequirements(
            value = {
                    @SecurityRequirement(name = "bearerAuth"),
                    @SecurityRequirement(name = "X-API-KEY")
            }
    )
    private TeacherDetailsDto add(
            @RequestBody AddOrUpdateTeacherParams params
    ) {
//        if(jwtEntity == null || jwtEntity.getAdminType() == null)
//            throw new ForbiddenException();

        return teacherDetailsMapper.toDto(teacherService.add(params));
    }

    @PutMapping("{id}")
    @SecurityRequirements(
            value = {
                    @SecurityRequirement(name = "bearerAuth"),
                    @SecurityRequirement(name = "X-API-KEY")
            }
    )
    private TeacherDetailsDto update(
            @PathVariable Integer id,
            @RequestBody AddOrUpdateTeacherParams params
//            @AuthenticationPrincipal JwtEntity jwtEntity
    ) {
//        if(jwtEntity == null || jwtEntity.getAdminType() == null)
//            throw new ForbiddenException();

        return teacherDetailsMapper.toDto(teacherService.update(id, params));
    }

    @DeleteMapping("{id}")
    @SecurityRequirements(
            value = {
                    @SecurityRequirement(name = "bearerAuth"),
                    @SecurityRequirement(name = "X-API-KEY")
            }
    )
    private void deleteById(
            @PathVariable Integer id,
            @AuthenticationPrincipal JwtEntity jwtEntity
    ) {
        if(jwtEntity == null || jwtEntity.getAdminType() == null)
            throw new ForbiddenException();

        teacherService.deleteById(id);
    }
}
