package web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.LinkedList;

@Data
@NoArgsConstructor
public class MathFunctionCreateRequest {
    private int count;

    @JsonProperty("math_func")
    @Nullable
    private String mathFunc;

    @JsonProperty("x_from")
    @Nullable
    private Integer xFrom;

    @JsonProperty("x_to")
    @Nullable
    private Integer xTo;

    @JsonProperty("fabric_type")
    @Nullable
    private String fabricType;

    @Nullable
    private LinkedList<PointDto> points;
}
