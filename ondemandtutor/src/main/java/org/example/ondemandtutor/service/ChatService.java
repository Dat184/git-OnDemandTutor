package org.example.ondemandtutor.service;

import lombok.RequiredArgsConstructor;
import org.example.ondemandtutor.pojo.Chat;
import org.example.ondemandtutor.pojo.Student;
import org.example.ondemandtutor.pojo.Tutor;
import org.example.ondemandtutor.repository.ChatRepository;
import org.example.ondemandtutor.repository.StudentRepository;
import org.example.ondemandtutor.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ChatService {
    @Autowired
    private final ChatRepository chatRepository;
    @Autowired
    private final TutorRepository tutorRepository;
    @Autowired
    private final StudentRepository studentRepository;

    // Tạo chat giữa tutor và student
    public Chat createChat(Long tutorId, Long studentId) {
        Tutor tutor = tutorRepository.findById(tutorId)
                .orElseThrow(() -> new IllegalArgumentException("Tutor not found"));
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));

        // Kiểm tra chat đã tồn tại chưa
        Optional<Chat> existingChat = chatRepository.findByTutorAndStudent(tutor, student);
        if (existingChat.isPresent()) {
            return existingChat.get();
        }
        // Tạo phòng chat mới
        Chat chat = new Chat();
        chat.setTutor(tutor);
        chat.setStudent(student);
        Chat savedChat = chatRepository.save(chat);
        // Trả về phòng chat mới tạo
        return savedChat;
    }


    public Chat getChatById(Long chatId) {
        return chatRepository.findById(chatId)
                .orElseThrow(() -> new IllegalArgumentException("Chat not found"));
    }

    public void deleteChat(Long chatId) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new IllegalArgumentException("Chat not found"));
        chatRepository.delete(chat);
    }

    // Kiểm tra chat đã tồn tại chưa
    public boolean chatExists(Long tutorId, Long studentId) {
        Tutor tutor = tutorRepository.findById(tutorId)
                .orElseThrow(() -> new IllegalArgumentException("Tutor not found"));
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));

        return chatRepository.findByTutorAndStudent(tutor, student).isPresent();
    }
}