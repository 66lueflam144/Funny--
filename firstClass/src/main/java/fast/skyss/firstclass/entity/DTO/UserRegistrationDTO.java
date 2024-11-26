package fast.skyss.firstclass.entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserRegistrationDTO {
    private String username;
    private String password;
    private String email;
    private MultipartFile avatar;
}
