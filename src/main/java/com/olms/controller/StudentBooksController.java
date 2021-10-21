package com.olms.controller;

import com.olms.dto.StudentDTO;
import com.olms.entity.StudentBooksEntity;
import com.olms.service.StudentBooksService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class StudentBooksController {

    private final StudentBooksService studentBooksService;


    @PostMapping("/student-book")
    public RedirectView createUser(RedirectAttributes redirectAttributes, @ModelAttribute StudentDTO studentDTO,
                                   HttpServletRequest request) {

        String message = studentBooksService.createUserBooks(studentDTO);
        RedirectView redirectView = null;
        redirectView = new RedirectView("/issue-book", true);
        redirectAttributes.addFlashAttribute("userMessage", message);
        return redirectView;
    }

    @GetMapping("/student")
    public String updateUser(Model model) {
        model.addAttribute("studentInfo", new StudentDTO());
        return "student";
    }

    @GetMapping("/receive-book")
    public String getUser(Model model) {
        model.addAttribute("studentDTO", new StudentDTO());
        model.addAttribute("booksList", new ArrayList<StudentDTO>());
        return "receive-book";
    }

    @GetMapping("/search-students")
    public String searchStudents(Model model, @ModelAttribute StudentDTO studentDTO) {
        model.addAttribute("booksList", studentBooksService.searchStudents(studentDTO.getStudentNumber()));
        return "receive-book";
    }


    @GetMapping("/open-search-book")
    public String openSearchBook(
            Model model) {
        model.addAttribute("listBooks", new ArrayList<StudentBooksEntity>());
        model.addAttribute("studentDTO", new StudentDTO());
        return "books-history";
    }

    @GetMapping("/get-book")
    public String searchBook(@ModelAttribute StudentDTO studentDTO, Model model) {
        model.addAttribute("studentDTO", studentDTO);
        return findPaginated(1, "studentNumber", "asc", model, studentDTO);

    }

    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model, StudentDTO studentDTO) {

        int pageSize = 5;

        Page<StudentBooksEntity> page = studentBooksService.findPaginated(pageNo, pageSize, sortField, sortDir,
                studentDTO);
        List<StudentBooksEntity> listBooks = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listBooks", listBooks);
        model.addAttribute("studentDTO", new StudentDTO());
        return "books-history";
    }


    @GetMapping("/update-student/{id}/{bookId}")
    public RedirectView updateStudent(RedirectAttributes redirectAttributes, @ModelAttribute StudentDTO studentDTO,
                                      @PathVariable long id, @PathVariable long bookId) {
        studentBooksService.updateStudentsBooks(id, bookId);
        String message = "Updated successfully";
        RedirectView redirectView = new RedirectView("/receive-book", true);
        redirectAttributes.addFlashAttribute("userMessage", message);
        return redirectView;
    }


}
