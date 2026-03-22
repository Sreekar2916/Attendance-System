package com.company.attendance.repository;

import com.company.attendance.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;


public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    Optional<Attendance> findByUserIdAndCheckOutIsNull(Long userId);

    List<Attendance> findAllByOrderByCheckInDesc();

    List<Attendance> findByCheckInBetween(LocalDateTime start, LocalDateTime end);

}