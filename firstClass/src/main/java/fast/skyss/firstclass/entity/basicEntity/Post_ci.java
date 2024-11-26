package fast.skyss.firstclass.entity.basicEntity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "post_ci")
public class Post_ci {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] attachmentData;

    private String attachmentName;
    private String attachmentType;

    @Column(nullable = false)
    private int attachmentCount = 0;

    @Column(nullable = false)
    private LocalDateTime createTime;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean isPublic = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User_x user;

    @PrePersist
    protected void onCreate() {
        createTime = LocalDateTime.now();
    }


    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }
}

