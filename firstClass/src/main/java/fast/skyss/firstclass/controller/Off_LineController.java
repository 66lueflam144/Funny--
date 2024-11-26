package fast.skyss.firstclass.controller;

import fast.skyss.firstclass.service.dataService.Sto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/off_line")
public class Off_LineController {

    @Autowired
    private Sto sto;

    @GetMapping("/dean-on-line")
    public String login() {
        return "dean/dean-on-line";
    }

    @GetMapping("/re-connect")
    @PreAuthorize("hasRole('DEAN')")
    public String reconnect(Model model) {
        model.addAttribute("users", sto.getAllguys());
        return "dean/re-connect";
    }


    @PostMapping("users/{userId}/delete")
    public String letde(@PathVariable Long userId) {
        sto.deleteAjerk(userId);
        System.out.println("YES!");
        return "redirect:/off_line/re-connect";
    }


    @GetMapping("/users/{userId}/posts")
    public String viewUserPosts(@PathVariable Long userId, Model model) {
        model.addAttribute("posts", sto.getposts(userId));
        model.addAttribute("userId", userId);
        return "dean/user-posts";
    }

    @PostMapping("/posts/{postId}/visible")
    public String visORnot(@PathVariable Long postId, @RequestParam Long userId) {
        sto.visiableornot(postId);
        return "redirect:/off_line/users/" + userId + "/posts";
    }

    @PostMapping("/posts/{postId}/delete")
    public String deletePost(@PathVariable Long postId, @RequestParam Long userId) {
        sto.deletePost(postId);
        return "redirect:/off_line/users/" + userId + "/posts";
    }


}
