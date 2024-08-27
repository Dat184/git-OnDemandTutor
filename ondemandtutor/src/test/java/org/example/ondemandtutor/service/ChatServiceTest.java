package org.example.ondemandtutor.service;

import org.example.ondemandtutor.pojo.Chat;
import org.example.ondemandtutor.pojo.Student;
import org.example.ondemandtutor.pojo.Tutor;
import org.example.ondemandtutor.repository.ChatRepository;
import org.example.ondemandtutor.repository.StudentRepository;
import org.example.ondemandtutor.repository.TutorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ChatServiceTest {

    private ChatService chatService;
    private ChatRepository chatRepository;
    private TutorRepository tutorRepository;
    private StudentRepository studentRepository;

    private Tutor tutor;
    private Student student;
    private Chat chat;

    @BeforeEach
    void setUp() {
        chatRepository = Mockito.mock(ChatRepository.class);
        tutorRepository = Mockito.mock(TutorRepository.class);
        studentRepository = Mockito.mock(StudentRepository.class);

        chatService = new ChatService(chatRepository, tutorRepository, studentRepository);

        tutor = new Tutor();
        tutor.setId(1L);

        student = new Student();
        student.setId(1L);

        chat = new Chat();
        chat.setTutor(tutor);
        chat.setStudent(student);
    }

    @Test
    void createChat_existingChat() {
        when(tutorRepository.findById(1L)).thenReturn(Optional.of(tutor));
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(chatRepository.findByTutorAndStudent(tutor, student)).thenReturn(Optional.of(chat));

        Chat resultChat = chatService.createChat(1L, 1L);

        assertNotNull(resultChat);
        assertEquals(chat, resultChat);

        verify(tutorRepository, times(1)).findById(1L);
        verify(studentRepository, times(1)).findById(1L);
        verify(chatRepository, times(1)).findByTutorAndStudent(tutor, student);
        verify(chatRepository, never()).save(any(Chat.class));
    }

    @Test
    void createChat_newChat() {
        when(tutorRepository.findById(1L)).thenReturn(Optional.of(tutor));
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(chatRepository.findByTutorAndStudent(tutor, student)).thenReturn(Optional.empty());
        when(chatRepository.save(any(Chat.class))).thenReturn(chat);

        Chat resultChat = chatService.createChat(1L, 1L);

        assertNotNull(resultChat);
        assertEquals(chat, resultChat);

        verify(tutorRepository, times(1)).findById(1L);
        verify(studentRepository, times(1)).findById(1L);
        verify(chatRepository, times(1)).findByTutorAndStudent(tutor, student);
        verify(chatRepository, times(1)).save(any(Chat.class));
    }

    @Test
    void createChat_tutorNotFound() {
        when(tutorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> chatService.createChat(1L, 1L));

        verify(tutorRepository, times(1)).findById(1L);
        verify(studentRepository, never()).findById(anyLong());
        verify(chatRepository, never()).findByTutorAndStudent(any(Tutor.class), any(Student.class));
        verify(chatRepository, never()).save(any(Chat.class));
    }

    @Test
    void createChat_studentNotFound() {
        when(tutorRepository.findById(1L)).thenReturn(Optional.of(tutor));
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> chatService.createChat(1L, 1L));

        verify(tutorRepository, times(1)).findById(1L);
        verify(studentRepository, times(1)).findById(1L);
        verify(chatRepository, never()).findByTutorAndStudent(any(Tutor.class), any(Student.class));
        verify(chatRepository, never()).save(any(Chat.class));
    }

    @Test
    void deleteChatById() {
        when(chatRepository.findById(1L)).thenReturn(Optional.of(chat));

        chatService.deleteChatById(1L);

        verify(chatRepository, times(1)).findById(1L);
        verify(chatRepository, times(1)).delete(chat);
    }

    @Test
    void deleteChatById_notFound() {
        when(chatRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> chatService.deleteChatById(1L));

        verify(chatRepository, times(1)).findById(1L);
        verify(chatRepository, never()).delete(any(Chat.class));
    }
}
