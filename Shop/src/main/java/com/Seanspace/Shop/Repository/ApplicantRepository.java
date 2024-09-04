package com.Seanspace.Shop.Repository;

import com.Seanspace.Shop.Entity.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ApplicantRepository extends CrudRepository<Applicant, Long> {
    Optional<Applicant> findByName(String name);
}
