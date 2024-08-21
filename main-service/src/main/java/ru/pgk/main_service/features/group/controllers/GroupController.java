package ru.pgk.main_service.features.group.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.pgk.main_service.common.dto.PageDto;
import ru.pgk.main_service.features.group.dto.GroupDto;
import ru.pgk.main_service.features.group.mappers.GroupMapper;
import ru.pgk.main_service.features.group.services.GroupService;

@RestController
@RequestMapping("groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    private final GroupMapper groupMapper;

    @GetMapping
    private PageDto<GroupDto> getAll(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0", required = false) Integer offset
    ) {
        Page<GroupDto> groupDtoPage = groupService.getAll(name, offset).map(groupMapper::toDto);
        return PageDto.fromPage(groupDtoPage);
    }
}
