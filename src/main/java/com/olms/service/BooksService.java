package com.olms.service;

import com.olms.dto.BooksDTO;
import com.olms.entity.BooksEntity;
import com.olms.entity.UserInfo;
import com.olms.exceptions.NotFoundException;
import com.olms.repository.BooksRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class BooksService {
    private final BooksRepository booksRepository;

    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<BooksDTO> getBooksList() {

        List<BooksEntity> list = booksRepository.findAllByStatusOrderByIdDesc(true);
        List<BooksDTO> dtoList = new ArrayList<>(list.size());
        BooksDTO booksDTO = new BooksDTO();
        for (BooksEntity booksEntity : list) {
            booksDTO = new BooksDTO();
            booksDTO.setTitleOfTheBook(booksEntity.getTitleOfTheBook());
            booksDTO.setAuthor(booksEntity.getAuthor());
            booksDTO.setPublicationYear(booksEntity.getPublicationYear());
            booksDTO.setPublishedPlace(booksEntity.getPublishedPlace());
            booksDTO.setLocationInTheLibrary(booksEntity.getLocationInTheLibrary());
            booksDTO.setNumberings(booksEntity.getNumberings());
            booksDTO.setId(booksEntity.getId());
            dtoList.add(booksDTO);
        }
        return dtoList;
    }

    @GetMapping("/add-book")
    public String getBook(Model model, HttpServletRequest request) {
        model.addAttribute("booksEntity", booksRepository.findAll());
        model.addAttribute("userInfo", new UserInfo());
        model.addAttribute("booksEntity", new BooksDTO());
        return "add-book";
    }

    public BooksDTO getBookById(long id) {
        BooksEntity booksEntity = getBookFromDB(id);  //through dependency injection
        BooksDTO booksDTO = new BooksDTO();
        booksDTO.setTitleOfTheBook(booksEntity.getTitleOfTheBook());
        booksDTO.setAuthor(booksEntity.getAuthor());
        booksDTO.setPublicationYear(booksEntity.getPublicationYear());
        booksDTO.setPublishedPlace(booksEntity.getPublishedPlace());
        booksDTO.setLocationInTheLibrary(booksEntity.getLocationInTheLibrary());
        booksDTO.setNumberings(booksEntity.getNumberings());
        booksDTO.setId(booksEntity.getId());
        return booksDTO;
    }

    public void createBook(BooksDTO booksDTO) {

        BooksEntity booksEntity = new BooksEntity();
        booksEntity.setTitleOfTheBook(booksDTO.getTitleOfTheBook());
        booksEntity.setAuthor(booksDTO.getAuthor());
        booksEntity.setPublishedPlace(booksDTO.getPublishedPlace());
        booksEntity.setPublicationYear(booksDTO.getPublicationYear());
        booksEntity.setLocationInTheLibrary(booksDTO.getLocationInTheLibrary());
        booksEntity.setNumberings(booksDTO.getNumberings());
        booksEntity.setCurrentStatus(booksDTO.getCurrentStatus());
        booksEntity.setCurrentStatus("available");
        booksEntity.setStatus(Boolean.TRUE);
        booksRepository.save(booksEntity);

    }


    public List<BooksDTO> getBooksByNameTitle(String title, String author) {
        List<BooksEntity> booksEntities = booksRepository.findByTitleOfTheBookOrAuthor(title, author);
        List<BooksDTO> list = new ArrayList<BooksDTO>();
        BooksDTO booksDTO;
        for (BooksEntity booksEntity : booksEntities) {
            if (booksEntity.getNumberings() != 0 && booksEntity.isStatus()) {
                booksDTO = new BooksDTO();
                booksDTO.setTitleOfTheBook(booksEntity.getTitleOfTheBook());
                booksDTO.setAuthor(booksEntity.getAuthor());
                booksDTO.setPublicationYear(booksEntity.getPublicationYear());
                booksDTO.setLocationInTheLibrary(booksEntity.getLocationInTheLibrary());
                booksDTO.setId(booksEntity.getId());
                booksDTO.setNumberings(booksEntity.getNumberings());
                list.add(booksDTO);
            }
        }
        return list;
    }


    public BooksEntity updateBooks(long id, BooksDTO booksDTO) {

        BooksEntity booksEntity = getBookFromDB(id);


        booksEntity.setTitleOfTheBook(booksDTO.getTitleOfTheBook());
        booksEntity.setAuthor(booksDTO.getAuthor());
        booksEntity.setPublishedPlace(booksDTO.getPublishedPlace());
        booksEntity.setPublicationYear(booksDTO.getPublicationYear());
        booksEntity.setLocationInTheLibrary(booksDTO.getLocationInTheLibrary());
        booksEntity.setNumberings(booksDTO.getNumberings());
        booksEntity.setCurrentStatus(booksDTO.getCurrentStatus());
        booksEntity.setStatus(booksDTO.isStatus());
        return booksRepository.save(booksEntity);
    }

    public BooksEntity getBookFromDB(long id) {
        return booksRepository.findByIdAndStatus(id, true).orElseThrow(NotFoundException::new);
    }

    public Page<BooksEntity> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {

        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.booksRepository.findAll(pageable);
    }


}