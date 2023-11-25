package com.uzum.mappers;

import com.uzum.dto.ComissionDto;
import com.uzum.models.Comission;

public class ComissionMapper {
    public static ComissionDto toDto(Comission comission) {
        return ComissionDto.builder()
                .to(comission.getTo())
                .from(comission.getFrom())
                .commission(comission.getCommission())
                .build();
    }
}
