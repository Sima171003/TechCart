package com.TechCart.cart;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findAllByCartCartId(Long cartId);

    Optional<CartItem> findByCartCartIdAndProductPId(Long cartId, Long pId);
}