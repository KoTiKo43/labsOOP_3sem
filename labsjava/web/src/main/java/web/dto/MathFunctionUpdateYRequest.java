package web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MathFunctionUpdateYRequest {
    @JsonProperty("point_index")
    private int pointIndex;

    private double value;
}
