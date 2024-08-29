package org.example.ondemandtutor.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.ondemandtutor.dto.request.SubjectRequest;
import org.example.ondemandtutor.dto.response.SubjectResponse;
import org.example.ondemandtutor.mapper.SubjectMapper;
import org.example.ondemandtutor.pojo.Subject;
import org.example.ondemandtutor.repository.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SubjectService {
    SubjectRepository subjectRepository;
    SubjectMapper subjectMapper;

    public List<SubjectResponse> getAllSubjects() {
        return subjectMapper.toSubjectResponseList(subjectRepository.findAll());
    }

    public SubjectResponse getSubjectById(Long id) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Subject not found"));
        return subjectMapper.toSubjectResponse(subject);
    }

    public SubjectResponse createSubject(SubjectRequest subjectRequest) {
        return subjectMapper.toSubjectResponse(subjectRepository.save(subjectMapper.toSubject(subjectRequest)));
    }

    public SubjectResponse updateSubject(Long id, SubjectRequest subjectRequest) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Subject not found"));
        subjectMapper.updateSubjectFromRequest(subjectRequest, subject);
        return subjectMapper.toSubjectResponse(subjectRepository.save(subject));
    }

    public void deleteSubject(Long id) {
        subjectRepository.deleteById(id);
    }

}
