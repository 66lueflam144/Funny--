package fast.skyss.firstclass.entity.basicEntity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
@Table(name = "user_x")
public class User_x {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] avatarData;

    private String avatarType;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Post_ci> posts;

    @Column(nullable = false)
    private String role;

    public String getRole() {
        if (this.username.equals("dean") || this.email.contains("dean")) {
            return "DEAN";
        }
        return "USER";
    }

}
