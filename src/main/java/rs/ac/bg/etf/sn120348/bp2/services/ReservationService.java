package rs.ac.bg.etf.sn120348.bp2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.etf.sn120348.bp2.entities.ReservationEntity;
import rs.ac.bg.etf.sn120348.bp2.repositories.ReservationRepository;

import java.util.List;

@Service("reservationService")
public class ReservationService {

    @Autowired
    ReservationRepository reservationRepository;

    public List<ReservationEntity> findReservationsByUserId(int id) {
        return reservationRepository.findByUserId(id);
    }

    public void insertOrUpdateReservation(ReservationEntity reservationEntity) {
        reservationRepository.save(reservationEntity);
    }
}
