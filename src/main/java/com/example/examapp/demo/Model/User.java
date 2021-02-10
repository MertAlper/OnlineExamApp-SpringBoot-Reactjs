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

    @Column(name = "UserName", nullable = false)
    private String username;

    @Column(name = "Email", nullable = false)
    private String Email;

    @Column(name = "FirstName", nullable = false)
    private String firstName;

    @Column(name = "LastName", nullable = false)
    private String  lastName;


}
