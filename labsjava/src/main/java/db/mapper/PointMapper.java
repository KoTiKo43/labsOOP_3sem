package db.mapper;

import db.dto.PointDTO;
import db.entity.PointEntity;
import org.springframework.stereotype.Repository;

@Repository
public class PointMapper {
    public PointEntity toEntity(PointDTO dto) {
        if (dto == null) {
            return null;
        }

        PointEntity entity = new PointEntity();
        entity.setId(dto.getId());
        entity.setXValue(dto.getXValue());
        entity.setYValue(dto.getYValue());

        return entity;
    }

    public PointDTO toDTO(PointEntity entity) {
        if (entity == null) {
            return null;
        }

        PointDTO dto = new PointDTO();
        dto.setId(entity.getId());
        dto.setFunctionId(entity.getFunction().getId());
        dto.setXValue(entity.getXValue());
        dto.setYValue(entity.getYValue());

        return dto;
    }
}
