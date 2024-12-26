package web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@NoArgsConstructor
public class MathFunctionDiffRequest {
    @JsonProperty("fabric_type")
    @Nullable
    private String fabricType;
}
