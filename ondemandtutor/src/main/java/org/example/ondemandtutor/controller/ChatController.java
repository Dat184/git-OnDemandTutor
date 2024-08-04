package org.example.ondemandtutor.controller;

import org.example.ondemandtutor.pojo.Chat;
import org.example.ondemandtutor.pojo.ResponseObject;
import org.example.ondemandtutor.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private ChatRepository chatRepository;

    @GetMapping("")
    public List<Chat> getAllChats() {
        return chatRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> findById(@PathVariable long id) {
        Optional<Chat> foundChat = chatRepository.findById(id);
        return foundChat.isPresent() ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Query chat successfully", foundChat)
                ) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("failed", "Cannot find Chat with id = " + id, "")
                );
    }

    @GetMapping("/student/{studentId}")
    public List<Chat> findByStudentId(@PathVariable long studentId) {
        return chatRepository.findByStudentId(studentId);
    }

    @GetMapping("/tutor/{tutorId}")
    public List<Chat> findByTutorId(@PathVariable long tutorId) {
        return chatRepository.findByTutorId(tutorId);
    }

    @PostMapping("/insert")
    public ResponseEntity<ResponseObject> insertChat(@RequestBody Chat newChat) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Insert chat successfully", chatRepository.save(newChat))
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateChat(@PathVariable long id, @RequestBody Chat newChat) {
        Chat updatedChat = chatRepository.findById(id)
                .map(chat -> {
                    chat.setCreatedAt(newChat.getCreatedAt());
                    chat.setStudent(newChat.getStudent());
                    chat.setTutor(newChat.getTutor());
                    return chatRepository.save(chat);
                }).orElseGet(() -> {
                    newChat.setId(id);
                    return chatRepository.save(newChat);
                });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Update chat successfully", updatedChat)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteChat(@PathVariable long id) {
        boolean exists = chatRepository.existsById(id);
        if (exists) {
            chatRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete chat successfully", "")
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find Chat with id = " + id, "")
            );
        }
    }
}
