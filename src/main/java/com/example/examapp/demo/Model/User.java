package com.example.examapp.demo.Model;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "USER")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "E_MAIL")
    private String Email;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String  lastName;

}
