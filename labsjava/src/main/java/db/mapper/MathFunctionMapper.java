package db.mapper;

import db.dto.MathFunctionDTO;
import db.entity.MathFunctionEntity;
import org.springframework.stereotype.Repository;

@Repository
public class MathFunctionMapper {
    public MathFunctionEntity toEntity(MathFunctionDTO dto) {
        if (dto == null) {
            return null;
        }

        MathFunctionEntity entity = new MathFunctionEntity();
        entity.setId(dto.getId());
        entity.setFunctionType(dto.getFunctionType());
        entity.setCount(dto.getCount());
        entity.setXFrom(dto.getXFrom());
        entity.setXTo(dto.getXTo());

        return entity;
    }
    public MathFunctionDTO toDTO(MathFunctionEntity entity) {
        if (entity == null) {
            return null;
        }

        MathFunctionDTO dto = new MathFunctionDTO();
        dto.setId(entity.getId());
        dto.setFunctionType(entity.getFunctionType());
        dto.setCount(entity.getCount());
        dto.setXFrom(entity.getXFrom());
        dto.setXTo(entity.getXTo());

        return dto;
    }
}
