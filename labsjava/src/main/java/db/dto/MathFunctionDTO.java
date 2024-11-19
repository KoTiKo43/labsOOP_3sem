package db.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MathFunctionDTO {
    private Long id;
    private String functionType;
    private Integer count;
    private Double xFrom;
    private Double xTo;
}
