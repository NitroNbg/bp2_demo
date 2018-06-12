package rs.ac.bg.etf.sn120348.bp2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.etf.sn120348.bp2.entities.ApartmentEntity;
import rs.ac.bg.etf.sn120348.bp2.repositories.ApartmentRepository;

@Service("apartmentService")
public class ApartmentService {

    @Autowired
    ApartmentRepository apartmentRepository;

    public ApartmentEntity findApartmentById(int id) {
        return apartmentRepository.findById(id);
    }

    public void updateApartment(ApartmentEntity apartment) {
        apartmentRepository.save(apartment);
    }
}
