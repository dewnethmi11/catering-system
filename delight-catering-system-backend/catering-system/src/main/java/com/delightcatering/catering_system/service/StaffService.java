package com.delightcatering.catering_system.service;

import com.delightcatering.catering_system.entity.Staff;
import com.delightcatering.catering_system.entity.StaffRole;
import com.delightcatering.catering_system.entity.EmploymentStatus;
import com.delightcatering.catering_system.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class StaffService {

    @Autowired
    private StaffRepository staffRepository;

    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }

    public Optional<Staff> getStaffById(Long id) {
        return staffRepository.findById(id);
    }

    public Staff createStaff(Staff staff) {
        return staffRepository.save(staff);
    }

    public Staff updateStaff(Long id, Staff staffDetails) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Staff not found with id: " + id));

        staff.setStaffRole(staffDetails.getStaffRole());
        staff.setHourlyRate(staffDetails.getHourlyRate());
        staff.setSkills(staffDetails.getSkills());
        staff.setQualifications(staffDetails.getQualifications());
        staff.setExperienceYears(staffDetails.getExperienceYears());
        staff.setEmergencyContactName(staffDetails.getEmergencyContactName());
        staff.setEmergencyContactPhone(staffDetails.getEmergencyContactPhone());

        return staffRepository.save(staff);
    }

    public void deleteStaff(Long id) {
        if (!staffRepository.existsById(id)) {
            throw new RuntimeException("Staff not found with id: " + id);
        }
        staffRepository.deleteById(id);
    }

    public List<Staff> getStaffByRole(StaffRole role) {
        return staffRepository.findByStaffRole(role);
    }

    public List<Staff> getAvailableStaff() {
        return staffRepository.findByAvailableTrue();
    }

    public List<Staff> getActiveStaff() {
        return staffRepository.findByEmploymentStatusAndAvailableTrue(EmploymentStatus.ACTIVE);
    }

    public Staff updateEmploymentStatus(Long id, EmploymentStatus status) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Staff not found with id: " + id));
        staff.setEmploymentStatus(status);
        return staffRepository.save(staff);
    }

    public Staff toggleAvailability(Long id) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Staff not found with id: " + id));
        staff.setAvailable(!staff.isAvailable());
        return staffRepository.save(staff);
    }
}