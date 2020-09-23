package com.simpletask.Zadatak_za_praksu.service;

import com.simpletask.Zadatak_za_praksu.domain.Appointment;
import com.simpletask.Zadatak_za_praksu.domain.User;
import com.simpletask.Zadatak_za_praksu.dto.AppointmentTimeRequestDTO;
import com.simpletask.Zadatak_za_praksu.dto.MakeAppointmentDTO;
import com.simpletask.Zadatak_za_praksu.repository.AppointmentRepository;
import com.simpletask.Zadatak_za_praksu.repository.UserRepository;
import com.simpletask.Zadatak_za_praksu.security.Konstante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    public List<String> getAvailableAppointmentsByDate(AppointmentTimeRequestDTO appointmentTimeRequestDTO) {
        List<Appointment> appointments = appointmentRepository.findAppointmentByStartDate(appointmentTimeRequestDTO.getDate());
        Date startInerval = generateDateTime(appointmentTimeRequestDTO.getDate().getTime(), Konstante.STARTING_TIME, 0);
        Date endInterval = generateDateTime(appointmentTimeRequestDTO.getDate().getTime(), Konstante.ENDING_TIME, 0);

        HashSet<Date> availableDates = new HashSet<>();

        while (startInerval.compareTo(endInterval) < 0) {
            availableDates.add(startInerval);
            startInerval = getNextAvailableDate(startInerval, 30);
        }

        for (Appointment a : appointments) {
            Date dateForCheck = getClearDate(a.getStartDate());
            if (availableDates.contains(dateForCheck)) {
                availableDates.remove(dateForCheck);
                if (a.getDuration() > 30) {
                    availableDates.remove(getNextAvailableDate(dateForCheck, 30));
                    availableDates.remove(getNextAvailableDate(dateForCheck, -30));
                }
            }
        }
        List<String> datesAsStrings = new ArrayList<>();
        for (Date d : availableDates) {
            datesAsStrings.add(d.toString());
        }
        return datesAsStrings;
    }

    private Date getClearDate(Date startDate) {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        cal.setTime(startDate);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    private Date generateDateTime(long time, Integer startingTime, Integer minutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(time));
        cal.set(Calendar.HOUR_OF_DAY, startingTime);
        cal.set(Calendar.MINUTE, minutes);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    private Date getNextAvailableDate(Date date, Integer step) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, step);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }


    public Appointment makeAppointment(MakeAppointmentDTO makeAppointmentDTO) throws Exception {
        User user = userRepository.findByUsername(makeAppointmentDTO.getUsername());
        if (user == null)
            throw new Exception("User not found");

        Date dateTime = new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy").parse(makeAppointmentDTO.getSelectedDateTime());

        Appointment appointment = new Appointment();
        appointment.setStartDate(dateTime);
        appointment.setDuration(makeAppointmentDTO.getDuration());
        appointment.setUser(user);

        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getAllMineReserved(Long id) throws Exception{
        User user = userRepository.findById(id).get();
        if (user == null)
            throw new Exception("User not found");

        List<Appointment> myAppointments = appointmentRepository.findAllByUserId(user.getId());

        return myAppointments;
    }

    public Appointment cancelAppointment(Long id) throws Exception{
        Appointment appointment = appointmentRepository.findById(id).get();

        if(checkIfLeft24h(appointment.getStartDate()) == true){
            appointmentRepository.delete(appointment);
        }else
            throw new Exception("24h before appointment starting has passed");

        return appointment;
    }

    public boolean checkIfLeft24h(Date startDate){
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        cal.add(Calendar.HOUR, -24);

        if(cal.getTime().compareTo(new Date()) >= 0) return true;
        else return false;
    }

    public List<Appointment> getAllMyAppointmentsForDentist(Long id) throws Exception{
        User user = userRepository.findById(id).get();
        if (user == null)
            throw new Exception("User not found");

        List<Appointment> myAppointments = appointmentRepository.findAll();

        return myAppointments;
    }
}
