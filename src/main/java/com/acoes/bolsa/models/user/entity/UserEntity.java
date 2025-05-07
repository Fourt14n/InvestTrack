package com.acoes.bolsa.models.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import lombok.Data;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;


@Data
@Entity
public class UserEntity {
   @Id
   @GeneratedValue(strategy = GenerationType.UUID)
   private Long id;

   @Pattern(regexp = "\\S+", message = "O campo [username] não deve conter espaço")
   private String username;
   @Email(message = "O campo[email] deve ser preenchido corretamente")
   private String email;
   @Length(min = 8, max = 100, message = "O campo deve ter entre 8 à 100 caracteres")
   private String password;
}
