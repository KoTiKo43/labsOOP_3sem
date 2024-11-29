package db.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Table(schema = "mathfunction", name = "t_function")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "points")
public class MathFunctionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "c_function_type")
    private String functionType;

    @Column(name = "c_count")
    @Min(2)
    private Integer count;

    @Column(name = "c_x_from")
    private Double xFrom;

    @Column(name = "c_x_to")
    private Double xTo;

    @OneToMany(mappedBy = "function", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<PointEntity> points;
}
