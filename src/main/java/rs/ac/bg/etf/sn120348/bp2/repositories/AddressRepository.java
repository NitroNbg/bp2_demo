package rs.ac.bg.etf.sn120348.bp2.repositories;

import org.springframework.data.repository.CrudRepository;
import rs.ac.bg.etf.sn120348.bp2.entities.AddressEntity;

import java.util.List;

public interface AddressRepository extends CrudRepository<AddressEntity, Long> {
    List<AddressEntity> findAll();
    List<AddressEntity> findAddressEntitiesByCountry(String country);
    List<AddressEntity> findAddressEntitiesByCity(String city);
    List<AddressEntity> findAddressEntitiesByCountryAndCity(String country, String city);
}
