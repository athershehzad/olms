package com.olms.controller;

import com.olms.entity.BookFineEntity;
import com.olms.service.BookFineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class BookFineController {


    private final BookFineService fineService;

    @GetMapping("/add-fine")
    public String getFines(Model model, HttpServletRequest request) {
        model.addAttribute("fines", fineService.getBookFine());
        model.addAttribute("fineEntity", new BookFineEntity());
        return "add-fine";
    }

    @PostMapping("/save-fine")
    public RedirectView createFine(RedirectAttributes redirectAttributes, @ModelAttribute BookFineEntity bookFineEntity) {
        fineService.createFine(bookFineEntity);
        String message = "Created fine <b>" + bookFineEntity.getFineAmount();
        RedirectView redirectView = new RedirectView("/add-fine", true);
        redirectAttributes.addFlashAttribute("userMessage", message);
        return redirectView;
    }

    @GetMapping("/fine/{id}")
    public String getFine(Model model, @PathVariable("id") Integer id) {
        BookFineEntity fineEntity = fineService.getFine(id);
        model.addAttribute("fineEntity", fineEntity);
        return "edit-fine";
    }

    @PostMapping("/edit-fine/{id}")
    public RedirectView updateFine(RedirectAttributes redirectAttributes, @PathVariable("id") Integer id, @ModelAttribute BookFineEntity bookFineEntity) {
        fineService.updateFine(id, bookFineEntity);
        String message = (bookFineEntity.isStatus() ? "Updated " : "Deleted ") + " user <b>" + bookFineEntity.getFineAmount();
        RedirectView redirectView = new RedirectView("/add-fine", true);
        redirectAttributes.addFlashAttribute("userMessage", message);
        return redirectView;
    }

}
