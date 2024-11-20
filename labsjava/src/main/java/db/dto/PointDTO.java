package db.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PointDTO {
    private Integer id;
    private Integer functionId;
    private Double xValue;
    private Double yValue;
}
