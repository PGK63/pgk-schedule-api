package ru.pgk.pgk.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@AllArgsConstructor
public class PageDto<T> {
    private List<T> content;
    private Integer totalPage;
    private Boolean last;
    private Boolean first;

    public static <T>PageDto<T> fromPage(Page<T> page) {
        return new PageDto<>(page.getContent(), page.getTotalPages(), page.isLast(), page.isFirst());
    }
}
