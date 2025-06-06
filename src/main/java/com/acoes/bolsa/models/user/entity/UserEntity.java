package com.acoes.bolsa.models.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;


@Data
@Entity
@Table(name = "usuario")
public class UserEntity {
   @Id
   @GeneratedValue(strategy = GenerationType.UUID)
   @Column(name = "id", unique = true)
   private UUID id;
   
   @Column(name = "username")
   @Pattern(regexp = "\\S+", message = "O campo [username] não deve conter espaço")
   private String username;
   
   @Column(unique = true, name = "email")
   @Email(message = "O campo[email] deve ser preenchido corretamente")
   private String email;
   
   @Column(name = "password")
   @Length(min = 8, max = 100, message = "O campo deve ter entre 8 à 100 caracteres")
   private String password;
}
