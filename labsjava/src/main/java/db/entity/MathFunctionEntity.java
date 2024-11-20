package db.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(schema = "mathfunction", name = "t_function")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MathFunctionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "c_function_type")
    @NotNull
    private String functionType;

    @Column(name = "c_count")
    @NotNull
    private Integer count;

    @Column(name = "c_x_from")
    @NotNull
    private Double xFrom;

    @Column(name = "c_x_to")
    @NotNull
    private Double xTo;

    @OneToMany(mappedBy = "function", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PointEntity> points;
}
