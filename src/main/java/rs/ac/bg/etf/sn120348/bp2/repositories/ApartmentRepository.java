package rs.ac.bg.etf.sn120348.bp2.repositories;

import org.springframework.data.repository.CrudRepository;
import rs.ac.bg.etf.sn120348.bp2.entities.ApartmentEntity;

public interface ApartmentRepository extends CrudRepository<ApartmentEntity, Long> {
    ApartmentEntity findById(int id);
}
