package rs.ac.bg.etf.sn120348.bp2.repositories;

import org.springframework.data.repository.CrudRepository;
import rs.ac.bg.etf.sn120348.bp2.entities.RoomEntity;

import java.util.List;

public interface RoomRepository extends CrudRepository<RoomEntity, Long> {
    @Override
    List<RoomEntity> findAll();

    RoomEntity findById(int id);
    List<RoomEntity> findRoomEntitiesByApartmentId(int apartmentId);
}
