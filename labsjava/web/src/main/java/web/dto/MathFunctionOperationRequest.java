package web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MathFunctionOperationRequest extends MathFunctionDiffRequest {
    @JsonProperty("operand_id_1")
    private int operandId1;

    @JsonProperty("operand_id_2")
    private int operandId2;

    private String operation;
}
