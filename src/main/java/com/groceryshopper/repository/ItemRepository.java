package com.groceryshopper.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.groceryshopper.model.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {

}
