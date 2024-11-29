package db.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(schema = "mathfunction", name = "t_point")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "function")
public class PointEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "function_id", referencedColumnName = "id")
    @JsonBackReference
    private MathFunctionEntity function;

    @Column(name = "c_x_value")
    private Double xValue;

    @Column(name = "c_y_value")
    private Double yValue;
}
