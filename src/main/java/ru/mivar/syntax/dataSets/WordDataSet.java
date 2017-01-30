package ru.mivar.syntax.dataSets;


import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "words")
public class WordDataSet {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
}
