package fast.skyss.firstclass.controller;

import fast.skyss.firstclass.entity.DTO.PostDTO;
import fast.skyss.firstclass.entity.basicEntity.User_x;
import fast.skyss.firstclass.service.dataService.PostService;
import fast.skyss.firstclass.service.dataService.User_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;
    private final User_Service userService;

    @Autowired
    public PostController(PostService postService, User_Service userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @PostMapping
    public String createPost(@RequestParam String content,
                             @RequestParam(required = false) MultipartFile attachment,
                             @RequestParam(defaultValue = "true") boolean isPublic,
                             Principal principal) throws IOException {
        User_x user = userService.findByUsername(principal.getName());
        postService.createPost(content, attachment, user, isPublic);
        return "redirect:/api/posts/public";
    }



    @GetMapping("/public")
    public String getPublicPosts(Model model, Principal principal) {
        List<PostDTO> posts;
        if (userService.isDean(principal.getName())) {
            posts = postService.getAllPosts();
        }
        else {
            posts  = postService.getPublicPosts();
        }
        model.addAttribute("posts", posts);
        return "public-posts";
    }


    @GetMapping("/users/{userId}/posts")
    public ModelAndView getUserPosts(@PathVariable Long userId) {
        List<PostDTO> userPosts = postService.getUserPosts(userId);
        ModelAndView modelAndView = new ModelAndView("user-posts");
        modelAndView.addObject("posts", userPosts);
        return modelAndView;
    }



    @GetMapping("/{postId}/attachment")
    public ResponseEntity<byte[]> downloadAttachment(@PathVariable Long postId) {
        byte[] attachment = postService.getAttachment(postId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment")
                .body(attachment);
    }

    @GetMapping("/search")
    public String searchPosts(@RequestParam(required = false) String keyword, Model model) {
        List<PostDTO> searchResults;

        if (keyword != null && !keyword.trim().isEmpty()) {
            searchResults = postService.searchPostsByKeyword(keyword);
        } else {
            searchResults = postService.getPublicPosts();
        }

        model.addAttribute("posts", searchResults);
        model.addAttribute("searchKeyword", keyword);
        return "notokay/imok";
    }



}
