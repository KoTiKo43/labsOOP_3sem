import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Entity
@Table(schema = "labs", name = "functions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FunctionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "x_from", nullable = false)
    private double xFrom;

    @Column(name = "x_to", nullable = false)
    private double xTo;

    @Column(nullable = false)
    private int count;

    @OneToMany(mappedBy = "functionEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PointEntity> points;
}
