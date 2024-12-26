package web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedList;

@Getter
@Setter
@ToString(callSuper = true)
public class MathFunctionResponse {
    @JsonProperty("math_func_id")
    private int id;

    private LinkedList<PointDto> points;
}
