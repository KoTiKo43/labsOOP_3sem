package db.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(schema = "mathfunction", name = "t_point")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PointEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "function_id", referencedColumnName = "id", nullable = false)
    private MathFunctionEntity function;

    @Column(name = "c_x_value")
    private Double xValue;

    @Column(name = "c_y_value")
    private Double yValue;
}
