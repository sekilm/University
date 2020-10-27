package ro.ubb.relations.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by radu.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Book {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String author;


    @ManyToOne
    @JoinTable(name = "pb", joinColumns = @JoinColumn(name = "bid"),
            inverseJoinColumns = @JoinColumn(name = "pid"))
    private Publisher publisher;

}
