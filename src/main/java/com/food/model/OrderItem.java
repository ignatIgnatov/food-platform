package com.food.model;

import jakarta.persistence.*;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne private Food food;

  private int quantity;
  private Long totalPrice;

  private List<String> ingredients;
}
