package com.delightcatering.catering_system.repository;

import com.delightcatering.catering_system.entity.Staff;
import com.delightcatering.catering_system.entity.StaffRole;
import com.delightcatering.catering_system.entity.EmploymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {
    List<Staff> findByStaffRole(StaffRole staffRole);
    List<Staff> findByEmploymentStatus(EmploymentStatus status);
    List<Staff> findByAvailableTrue();
    List<Staff> findByEmploymentStatusAndAvailableTrue(EmploymentStatus status);}