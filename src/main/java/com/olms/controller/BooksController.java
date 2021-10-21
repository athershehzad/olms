package com.olms.controller;

import com.olms.dto.BooksDTO;
import com.olms.dto.StudentDTO;
import com.olms.entity.BooksEntity;
import com.olms.entity.BooksRole;
import com.olms.entity.UserInfo;
import com.olms.service.BooksService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class BooksController {

    private final BooksService booksService;
    private List<BooksDTO> booksList;

    @PostMapping
    public void createBook(@RequestBody BooksDTO booksDTO) {
        booksService.createBook(booksDTO);

    }


    @GetMapping("/edit-book/{id}")
    public String editBook(Model model, @PathVariable("id") long id) {
        BooksDTO booksDTO = booksService.getBookById(id);
        model.addAttribute("booksDTO", booksDTO);
        return "edit-book";
    }

    @PostMapping("/update-book/{id}")
    public RedirectView updateBook(RedirectAttributes redirectAttributes, @PathVariable("id") long id, @ModelAttribute BooksDTO booksDTO) {
        booksService.updateBooks(id, booksDTO);
        String message = (booksDTO.isStatus() ? "Updated " : "Deleted ") + " user <b>" + booksDTO.getAuthor() + " " + booksDTO.getTitleOfTheBook() + "</b> ✨.";
        RedirectView redirectView = new RedirectView("/add-book", true);
        redirectAttributes.addFlashAttribute("userMessage", message);
        return redirectView;
    }

    //using get api

    @GetMapping("/books-all")
    public String getAllBooks(Model model) {

        model.addAttribute("booksList", booksService.getBooksList());
        model.addAttribute("booksDTO", new BooksDTO());
        model.addAttribute("userInfo", new UserInfo());
        return "add-book";

    }

    @GetMapping("/books")
    public String getBooksByNameTitle(@ModelAttribute BooksDTO booksEntity, Model model) {

        List<BooksDTO> booksList = booksService.getBooksByNameTitle(booksEntity.getSearch(), booksEntity.getSearch());
        model.addAttribute("booksList", booksList);
        model.addAttribute("booksInfo", new BooksDTO());
        return "issue-book";

    }

    @GetMapping("/search-books")
    public String searchBooks(@ModelAttribute BooksDTO booksEntity, Model model) {

        List<BooksDTO> booksList = booksService.getBooksByNameTitle(booksEntity.getSearch(), booksEntity.getSearch());
        model.addAttribute("booksList", booksList);
        model.addAttribute("booksInfo", new BooksDTO());
        this.setBooksList(booksList);
        return "search-book";

    }

    @GetMapping("/issue-book")
    public String getUser(Model model) {
        model.addAttribute("booksInfo", new BooksDTO());
        model.addAttribute("booksList", new ArrayList<BooksDTO>());

        return "issue-book";
    }

    @GetMapping("/assign-books")
    public String updateUser(Model model, @RequestParam Map<String, String> reqParam) {

        long bookId = Long.parseLong(reqParam.get("id"));
        BooksRole booksRole = BooksRole.valueOf(reqParam.get("booksRole"));
        BooksEntity entity = booksService.getBookFromDB(bookId);

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setLocationInTheLibrary(entity.getLocationInTheLibrary());
        studentDTO.setTitleOfTheBook(entity.getTitleOfTheBook());
        studentDTO.setNumbering(entity.getNumberings());
        studentDTO.setPublicationYear(entity.getPublicationYear());
        studentDTO.setAuthor(entity.getAuthor());
        studentDTO.setBookId(entity.getId());
        studentDTO.setBooksRole(booksRole);
        model.addAttribute("studentInfo", studentDTO);
        return "student";
    }


    @GetMapping("/add-book")
    public String getBook(Model model) {

        model.addAttribute("userInfo", new UserInfo());
        return "redirect:/re";
    }


    public List<BooksDTO> getBooksList() {
        return booksList;
    }

    public void setBooksList(List<BooksDTO> booksList) {
        this.booksList = booksList;
    }


    @PostMapping("/save-book")
    public String saveBook(@ModelAttribute BooksDTO booksDTO) {
        // save books to database
        booksService.createBook(booksDTO);
        return "redirect:/re";
    }


    @GetMapping("/re")
    public String viewHomePage(Model model) {

        return findPaginated(1, "author", "asc", model);
    }


    @GetMapping("/b/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        int pageSize = 5;

        Page<BooksEntity> page = booksService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<BooksEntity> booksList = page.getContent();
        List<BooksEntity> filterList = booksList.stream().filter(p->p.isStatus() == true).collect(Collectors.toList());

        for(BooksEntity  entity: filterList) {
            System.out.println(entity.getTitleOfTheBook());
        }

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("booksList", filterList);
        model.addAttribute("booksDTO", new BooksDTO());
        model.addAttribute("booksEntity", new BooksEntity());
        model.addAttribute("userInfo", new UserInfo());
        return "add-book";
    }
}
