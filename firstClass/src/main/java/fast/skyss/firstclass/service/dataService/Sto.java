package fast.skyss.firstclass.service.dataService;

import fast.skyss.firstclass.entity.DTO.PostDTO;
import fast.skyss.firstclass.entity.basicEntity.Post_ci;
import fast.skyss.firstclass.entity.basicEntity.User_x;
import fast.skyss.firstclass.repository.PostRepository;
import fast.skyss.firstclass.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class Sto {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    public List<User_x> getAllguys() {
        return userRepository.findAll();
    }

    @Transactional
    public void deleteAjerk(Long userId) {
        try {
            postRepository.deleteAllByUserId(userId);
            userRepository.deleteById(userId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete user: " + e.getMessage());
        }
    }

    public List<PostDTO> getposts(Long id) {
        return postRepository.findByUserId(id).stream()
                .map(this::convertToPostDTO)
                .collect(Collectors.toList());
    }

    private PostDTO convertToPostDTO(Post_ci post) {
        PostDTO dto = new PostDTO();
        dto.setId(post.getId());
        dto.setContent(post.getContent());
        dto.setAuthorUsername(post.getUser().getUsername());
        dto.setAuthorId(post.getUser().getId());
        dto.setCreateTime(post.getCreateTime());
        dto.setAttachmentName(post.getAttachmentName());
        dto.setIsPublic(post.isPublic());
        return dto;
    }

    public void visiableornot(Long postId) {
        Post_ci post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        post.setPublic(!post.isPublic());
        postRepository.save(post);
    }

    @Transactional
    public void deletePost(Long postId) {
        try {
            Post_ci post = postRepository.findById(postId)
                    .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));
            postRepository.delete(post);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete post: " + e.getMessage());
        }
    }
}
