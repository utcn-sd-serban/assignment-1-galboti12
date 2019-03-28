package ro.utcn.sd.boti.stackoverflow.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author")
    private User author;

    private String text;
    private String time;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "question_id")
    private Question question;

    public Answer(User user, Question question, String text, String time){
        this.author = user;
        this.question = question;
        this.text = text;
        this.time = time;
    }

    public String toString(){
        return new String("id = " + id + ", autor = " + author.getUsername() + ", text = " + text);
    }
}
