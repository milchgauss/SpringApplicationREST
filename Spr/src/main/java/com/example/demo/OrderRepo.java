package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

interface OrderRepo extends JpaRepository<Order,Long> {
}
