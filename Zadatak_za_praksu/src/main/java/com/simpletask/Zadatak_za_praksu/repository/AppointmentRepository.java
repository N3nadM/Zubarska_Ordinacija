package com.simpletask.Zadatak_za_praksu.repository;

import com.simpletask.Zadatak_za_praksu.domain.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("SELECT a FROM Appointment a WHERE CAST(a.startDate AS date) = CAST(:date AS date) ORDER BY a.startDate")
    List<Appointment> findAppointmentByStartDate(@Param("date") Date date);

    List<Appointment> findAllByUserId(Long id);

}