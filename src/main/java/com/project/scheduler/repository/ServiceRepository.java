package com.project.scheduler.repository;

import com.project.scheduler.entity.ServiceOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceOffer, Integer> {
}
