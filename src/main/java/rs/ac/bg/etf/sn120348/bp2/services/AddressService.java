package rs.ac.bg.etf.sn120348.bp2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.etf.sn120348.bp2.entities.AddressEntity;
import rs.ac.bg.etf.sn120348.bp2.repositories.AddressRepository;

import java.util.List;

@Service("addressService")
public class AddressService {

    @Autowired
    AddressRepository addressRepository;

    public List<AddressEntity> getAllAddresses() {
        return addressRepository.findAll();
    }

    public List<AddressEntity> lookupByCity(String city) {
        return addressRepository.findAddressEntitiesByCity(city);
    }

    public List<AddressEntity> lookupByCountry(String country) {
        return addressRepository.findAddressEntitiesByCountry(country);
    }

    public List<AddressEntity> lookupByCityAndCountry(String city, String country) {
        return addressRepository.findAddressEntitiesByCountryAndCity(country, city);
    }
}
