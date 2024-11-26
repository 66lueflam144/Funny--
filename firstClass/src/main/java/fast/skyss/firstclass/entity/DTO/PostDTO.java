package fast.skyss.firstclass.entity.DTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
public class PostDTO {
    private Long id;
    private String content;
    private String authorUsername;
    private Long authorId;
    private LocalDateTime createTime;
    private String attachmentName;
    private boolean isPublic;

    public boolean getIsPublic() {
        return isPublic;
    }




    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public void setIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }



}
