package com.olms.controller;

import com.olms.dto.BooksDTO;
import com.olms.entity.Role;
import com.olms.entity.UserInfo;
import com.olms.service.CustomUserDetails;
import com.olms.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;
    private List<BooksDTO> booksList;


    @GetMapping("/users")
    public String getUsers(Model model, HttpServletRequest request) {
        List<UserInfo> users = userService.getUsers();

        Principal principal2 = request.getUserPrincipal();
        UserInfo userInfo = ((CustomUserDetails) ((UsernamePasswordAuthenticationToken) principal2).getPrincipal()).users;
        if (userInfo.getUserType().equals(Role.ADMIN.name())) {

            model.addAttribute("users", userService.getUsers());
            model.addAttribute("userInfo", new UserInfo());
            return "admin-operations";
        } else if (userInfo.getUserType().equals(Role.LIBRARIAN.name())) {
            model.addAttribute("users", users);
            model.addAttribute("booksInfo", new BooksDTO());
            return "lib-operation";
        } else {

            model.addAttribute("booksInfo", new BooksDTO());
            model.addAttribute("booksList", booksList);
            return "search-book";
        }
    }

    @GetMapping("/add-users")
    public String getUser(Model model, HttpServletRequest request) {
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("userInfo", new UserInfo());
        return "add-users";
    }


    @PostMapping("/")
    public RedirectView createUser(RedirectAttributes redirectAttributes, @ModelAttribute UserInfo userInfo) {
        String message = userService.createUser(userInfo);
        RedirectView redirectView = new RedirectView("/add-users", true);
        redirectAttributes.addFlashAttribute("userMessage", message);
        return redirectView;
    }

    @GetMapping("/{id}")
    public String getUser(Model model, @PathVariable("id") Integer id) {
        UserInfo userInfo = userService.getUser(id);
        model.addAttribute("userInfo", userInfo);
        return "edit";
    }

    @PostMapping("/{id}")
    public RedirectView updateUser(RedirectAttributes redirectAttributes, @PathVariable("id") Integer id, @ModelAttribute UserInfo userInfo) {
        userService.updateUser(id, userInfo);
        String message = (userInfo.isStatus() ? "Updated " : "Deleted ") + " user <b>" + userInfo.getFirstName() + " " + userInfo.getLastName() + "</b> ✨.";
        RedirectView redirectView = new RedirectView("/add-users", true);
        redirectAttributes.addFlashAttribute("userMessage", message);
        return redirectView;
    }

}
