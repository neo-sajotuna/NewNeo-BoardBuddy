package com.sparta.newneoboardbuddy.domain.card.repository;

import com.sparta.newneoboardbuddy.domain.card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
}
