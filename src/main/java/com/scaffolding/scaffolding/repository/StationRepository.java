package com.scaffolding.scaffolding.repository;


import com.scaffolding.scaffolding.model.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StationRepository extends JpaRepository<Station,Integer>{

}