package com.acoes.bolsa.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private String name;
private String email;
  
}