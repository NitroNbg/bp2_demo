package rs.ac.bg.etf.sn120348.bp2.repositories;

import org.springframework.data.repository.CrudRepository;
import rs.ac.bg.etf.sn120348.bp2.entities.ReservationEntity;

import java.util.List;

public interface ReservationRepository extends CrudRepository<ReservationEntity, Long> {
    List<ReservationEntity> findByUserId(int id);
}
