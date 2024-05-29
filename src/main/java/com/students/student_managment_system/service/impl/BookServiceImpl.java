package com.students.student_managment_system.service.impl;

import com.students.student_managment_system.repository.BookRepository;
import com.students.student_managment_system.repository.UserRepository;
import com.students.student_managment_system.service.BookService;
import com.students.student_managment_system.dto.BookDto;
import com.students.student_managment_system.entity.Book;
import com.students.student_managment_system.entity.User;
import com.students.student_managment_system.mapper.BookMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class BookServiceImpl implements BookService {
    private BookRepository bookRepository;
//    private UserService userService;
    private UserRepository userRepository;

    public BookServiceImpl(BookRepository bookRepository,  UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<BookDto> getAllBook() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(book -> BookMapper.mapToBookDto(book))
                .collect(Collectors.toList());
    }

    @Override
    public boolean createBook(BookDto bookDto) {
        bookRepository.save(BookMapper.mapToBook(bookDto));
        return false;
    }

    @Override
    public boolean updateBook(BookDto bookDto) {
        bookRepository.save(BookMapper.mapToBook(bookDto));
        return true;
    }

    @Override
    public boolean updateBookUserByBookId(long bookId, String email) {
        User user = userRepository.findByEmail(email);

        Book book = bookRepository.findById(bookId).get();
        user.addBook(book);

        bookRepository.save(book);
        return true;
    }

    @Override
    public String getBookIsbnById(long id) {
        return bookRepository.getBookIsbnById(id);
    }

    @Override
    public String getBookOwnerEmailById(long id) {
        return bookRepository.findBookOwnerEmailById(id);
    }


    @Override
    public boolean returnBook(long id) {
        Book book = bookRepository.findById(id).get();
        book.setUser(null);
        bookRepository.save(book);
        return true;
    }


    @Override
    public List<BookDto> findBookByTitle(String title) {
        List<Book> books= bookRepository.searchBook(title);
        return books.stream()
                .map(book -> BookMapper.mapToBookDto(book))
                .collect(Collectors.toList());
    }
}