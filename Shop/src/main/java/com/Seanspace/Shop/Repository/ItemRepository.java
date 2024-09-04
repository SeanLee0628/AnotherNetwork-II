package com.Seanspace.Shop.Repository;

import com.Seanspace.Shop.Entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
