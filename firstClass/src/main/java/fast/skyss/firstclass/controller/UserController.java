package fast.skyss.firstclass.controller;

import fast.skyss.firstclass.entity.DTO.PostDTO;
import fast.skyss.firstclass.entity.DTO.UserRegistrationDTO;
import fast.skyss.firstclass.entity.basicEntity.User_x;
import fast.skyss.firstclass.service.dataService.PostService;
import fast.skyss.firstclass.service.dataService.User_Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/outtime/api/users")
public class UserController {
    @Autowired
    private User_Service userService;
    @Autowired
    private PostService postService;
//
    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ModelAndView registerUser(@ModelAttribute UserRegistrationDTO dto) {
        try {
            User_x user = userService.registerUser(dto);
            log.info("registered user: {}", user.getUsername());
            return new ModelAndView("redirect:/outtime/login");
        } catch (Exception e) {
            ModelAndView mav = new ModelAndView("outtime/register");
            mav.addObject("error", e.getMessage());
            return mav;
        }
    }

//    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public String registerUser(@ModelAttribute UserRegistrationDTO dto, Model model) {
//        try {
//            userService.registerUser(dto);
//            return "redirect:/outtime/login";
//        } catch (Exception e) {
//            model.addAttribute("errorMessage", e.getMessage());
//            return "outtime/register";
//        }
//    }


    @GetMapping("/profile")
    public String getProfile(Principal principal, Model model) {
        User_x user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        List<PostDTO> userPosts = postService.getUserPosts(user.getId());
        model.addAttribute("userPosts", userPosts);
        return "profile";  // 视图名称保持不变
    }

    @GetMapping("/avatar/{userId}")
    public ResponseEntity<?> getUserAvatar(@PathVariable Long userId) {
        User_x user = userService.findById(userId);
        if (user != null && user.getAvatarData() != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(user.getAvatarType()))
                    .body(user.getAvatarData());
        }
        return ResponseEntity.notFound().build();
    }
}
