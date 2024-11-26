package fast.skyss.firstclass.entity.basicEntity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "off_line")
public class Off_line {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String dean;

    @Column(nullable = false)
    private String system_off;

}
