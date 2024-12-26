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
public class MathFunctionApplyResponse {
    @JsonProperty("result")
    private double result;
}
