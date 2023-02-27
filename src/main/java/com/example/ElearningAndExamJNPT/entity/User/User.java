package com.example.ElearningAndExamJNPT.entity.User;

import com.example.ElearningAndExamJNPT.entity.BaseEntity;
import com.example.ElearningAndExamJNPT.entity.Grammar;
import com.example.ElearningAndExamJNPT.entity.Vocabulary;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "username"
        }),
        @UniqueConstraint(columnNames = {
                "email"
        })
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 20)
    private String firstname;
    @NotBlank
    @Size(min = 3, max = 20)
    private String lastname;
    @NotBlank
    @Size(min = 3, max = 50)
    private String username;
    @Enumerated(EnumType.STRING)
    private GenderType gender;
    @NaturalId
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @JsonIgnore
    @NotBlank
    @Size(min = 6, max = 100)
    private String password;

    @Lob
    private String avatar;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name="user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    Set<Role> roles = new HashSet<>();

    public User(@NotBlank @Size(min = 3, max = 20) String firstname,
                @NotBlank @Size(min = 3, max = 20) String lastname,
                @NotBlank @Size(min = 3, max = 20) String username,
                GenderType gender,
                @NotBlank @Size(max = 50) @Email String email,
                @NotBlank @Size(min = 6, max = 100) String encode,
                String avatar) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.gender = gender;
        this.email = email;
        this.password = encode;
        this.avatar = avatar;
    }

//    @OneToMany(mappedBy = "createdBy")
//    private List<Grammar> grammars = new ArrayList<>();

}
