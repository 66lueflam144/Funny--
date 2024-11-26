package fast.skyss.firstclass.service.dataService;


import fast.skyss.firstclass.entity.DTO.PostDTO;
import fast.skyss.firstclass.entity.basicEntity.Post_ci;
import fast.skyss.firstclass.entity.basicEntity.User_x;
import fast.skyss.firstclass.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public Post_ci createPost(String content, MultipartFile attachment, User_x user, Boolean isPublic) throws IOException {
        if (content.length() > 1000) {
            throw new RuntimeException("Content exceeds maximum length of 1000 characters");
        }
        Post_ci post = new Post_ci();
        post.setContent(content);
        post.setUser(user);
        post.setPublic(isPublic);

        if (attachment != null && !attachment.isEmpty()) {
            post.setAttachmentData(attachment.getBytes());
            post.setAttachmentName(attachment.getOriginalFilename());
            post.setAttachmentType(attachment.getContentType());
        }

        return postRepository.save(post);
    }

    public List<PostDTO> getAllPosts() {
        return postRepository.findAllByOrderByCreateTimeDesc()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public byte[] getAttachment(Long postId) {
        Post_ci post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        return post.getAttachmentData();
    }

    public List<PostDTO> getUserPosts(Long userId) {
        return postRepository.findByUserId(userId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private PostDTO convertToDTO(Post_ci post) {
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



    public List<PostDTO> getPublicPosts() {
        List<Post_ci> publicPosts = postRepository.findByIsPublicTrueOrderByCreateTimeDesc();
        return publicPosts.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    public List<PostDTO> searchPostsByKeyword(String keyword) {
        List<Post_ci> posts = postRepository.searchByKeyword(keyword);
        return posts.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

}
