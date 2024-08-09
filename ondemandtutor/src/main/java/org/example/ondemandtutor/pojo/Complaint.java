package org.example.ondemandtutor.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@NoArgsConstructor
@ToString
@Table(name = "complaint")
public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "complaint_type", nullable = false)
    private String complaintType;
    private String content;

    private String response;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    public Complaint(User user, String complaintType, String content, String response, Status status) {
        this.user = user;
        this.complaintType = complaintType;
        this.content = content;
        this.response = response;
        this.status = status;
    }
}