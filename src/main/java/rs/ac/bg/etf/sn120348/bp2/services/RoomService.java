package rs.ac.bg.etf.sn120348.bp2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.etf.sn120348.bp2.entities.ReservationEntity;
import rs.ac.bg.etf.sn120348.bp2.entities.RoomEntity;
import rs.ac.bg.etf.sn120348.bp2.repositories.RoomRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service("roomService")
public class RoomService {

    @Autowired
    RoomRepository roomRepository;

    public List<ReservationEntity> findReservationsByRoomId(int id) {
        return new ArrayList(roomRepository.findById(id).getReservationsById());
    }

    public List<RoomEntity> findAvailableRooms(int apartmentId, java.sql.Date startDate, java.sql.Date endDate) {
        List<RoomEntity> allRooms = roomRepository.findRoomEntitiesByApartmentId(apartmentId);
        List<RoomEntity> availableRooms = new ArrayList<>(allRooms);
        for (RoomEntity roomEntity : allRooms) {
            if (roomEntity.getLocked() > 0) {
                availableRooms.remove(roomEntity);
            }
            else {
                Collection<ReservationEntity> previousReservations = roomEntity.getReservationsById();
                boolean overlapping = false;
                if (previousReservations != null && !previousReservations.isEmpty()) {
                    for (ReservationEntity reservation : previousReservations) {
                        if (overlapping || overlappingReservations(reservation.getStartDate(), reservation.getEndDate(), startDate, endDate)) {
                            overlapping = true;
                        }
                    }
                }
                if (overlapping) {
                    availableRooms.remove(roomEntity);
                }
            }

        }
        return availableRooms;
    }

    private static boolean overlappingReservations(Timestamp startDate1, Timestamp endDate1, java.sql.Date startDate2, java.sql.Date endDate2) {
        Timestamp timestampStart2 = new Timestamp(startDate2.getTime());
        Timestamp timestampEnd2 = new Timestamp(endDate2.getTime());
        return ((startDate1.before(timestampStart2) && endDate1.after(timestampStart2))
                ||
                (startDate1.after(timestampStart2) && startDate1.before(timestampEnd2)));
    }
}
