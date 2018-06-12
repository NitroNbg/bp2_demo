package rs.ac.bg.etf.sn120348.bp2.bookings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import rs.ac.bg.etf.sn120348.bp2.entities.AccountUserEntity;
import rs.ac.bg.etf.sn120348.bp2.entities.AddressEntity;
import rs.ac.bg.etf.sn120348.bp2.entities.ApartmentEntity;
import rs.ac.bg.etf.sn120348.bp2.entities.RoomEntity;
import rs.ac.bg.etf.sn120348.bp2.services.AccountUserService;
import rs.ac.bg.etf.sn120348.bp2.services.AddressService;
import rs.ac.bg.etf.sn120348.bp2.services.ApartmentService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
public class ApartmentsController {

    private final Logger logger = LoggerFactory.getLogger(ApartmentsController.class);

    @Autowired
    AddressService addressService;

    @Autowired
    AccountUserService accountUserService;

    @Autowired
    ApartmentService apartmentService;

    @GetMapping("/buyer/apartment_lookup")
    public String getBuyerApartmentLookup(Model model) {
        List<AddressEntity> allAddressList = addressService.getAllAddresses();
        List<String> cities = new ArrayList<>();
        List<String> countries = new ArrayList<>();
        extractCitiesAndCountriesFromAddressList(cities, countries, allAddressList);
        cities.add(0, "*");
        countries.add(0, "*");
        model.addAttribute("cities", cities);
        model.addAttribute("countries", countries);
        model.addAttribute("form", new ApartmentLookupForm());
        return "/buyer/apartment_lookup";
    }

    @GetMapping("/seller/apartment_lookup")
    public String getSellerApartmentLookup(Model model) {
        List<AddressEntity> allAddressList = addressService.getAllAddresses();
        List<String> cities = new ArrayList<>();
        List<String> countries = new ArrayList<>();
        extractCitiesAndCountriesFromAddressList(cities, countries, allAddressList);
        cities.add(0, "*");
        countries.add(0, "*");
        model.addAttribute("cities", cities);
        model.addAttribute("countries", countries);
        model.addAttribute("form", new ApartmentLookupForm());
        return "/seller/apartment_lookup";
    }

    @PostMapping("/buyer/apartment_lookup")
    public String postBuyerApartmentLookup(@ModelAttribute("form") ApartmentLookupForm form, Model model) {
        List<AddressEntity> allAddressList = addressService.getAllAddresses();
        List<String> cities = new ArrayList<>();
        List<String> countries = new ArrayList<>();
        extractCitiesAndCountriesFromAddressList(cities, countries, allAddressList);
        cities.add(0, "*");
        countries.add(0, "*");
        List<ApartmentEntity> apartmentList = new ArrayList<>();
        List<AddressEntity> addressEntities;
        if (!form.getCity().equals("*") && !form.getCountry().equals("*")) {
            addressEntities = addressService.lookupByCityAndCountry(form.getCity(), form.getCountry());
        }
        else if (form.getCity().equals("*")) {
            addressEntities = addressService.lookupByCountry(form.getCountry());
        }
        else {
            addressEntities = addressService.lookupByCity(form.getCity());
        }
        for (AddressEntity addressEntity : addressEntities) {
            Collection<ApartmentEntity> apartmentEntities = addressEntity.getApartmentsById();
            for (ApartmentEntity apartment : apartmentEntities) {
                if (!(apartmentList.contains(apartment))) {
                    apartmentList.add(apartment);
                }
            }
        }
        model.addAttribute("cities", cities);
        model.addAttribute("countries", countries);
        model.addAttribute("apartment_list", apartmentList);
        return "/buyer/apartment_lookup";
    }

    @PostMapping("/seller/apartment_lookup")
    public String postSellerApartmentLookup(@ModelAttribute("form") ApartmentLookupForm form, Model model) {
        List<AddressEntity> allAddressList = addressService.getAllAddresses();
        List<String> cities = new ArrayList<>();
        List<String> countries = new ArrayList<>();
        extractCitiesAndCountriesFromAddressList(cities, countries, allAddressList);
        cities.add(0, "*");
        countries.add(0, "*");
        List<ApartmentEntity> apartmentList = new ArrayList<>();
        List<AddressEntity> addressEntities;
        if (!form.getCity().equals("*") && !form.getCountry().equals("*")) {
            addressEntities = addressService.lookupByCityAndCountry(form.getCity(), form.getCountry());
        }
        else if (form.getCity().equals("*")) {
            addressEntities = addressService.lookupByCountry(form.getCountry());
        }
        else {
            addressEntities = addressService.lookupByCity(form.getCity());
        }
        for (AddressEntity addressEntity : addressEntities) {
            Collection<ApartmentEntity> apartmentEntities = addressEntity.getApartmentsById();
            for (ApartmentEntity apartment : apartmentEntities) {
                if (!(apartmentList.contains(apartment))) {
                    apartmentList.add(apartment);
                }
            }
        }
        model.addAttribute("cities", cities);
        model.addAttribute("countries", countries);
        model.addAttribute("apartment_list", apartmentList);
        return "/seller/apartment_lookup";
    }

    @GetMapping("/seller/apartment_overview")
    public String getApartmentOverview(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        }
        AccountUserEntity user = accountUserService.findUserByUsername(authentication.getName());
        if (user == null) {
            logger.error("Cannot find user with username {}", authentication.getName());
        }

        List<ApartmentEntity> list = new ArrayList<>();
        Collection<ApartmentEntity> apartmentList = user.getApartmentsById();
        for (ApartmentEntity apartment : apartmentList) {
            list.add(apartment);
        }
        model.addAttribute("apartment_list", list);
        return "/seller/apartment_overview";
    }

    @GetMapping("/seller/edit_apartment/{id}")
    public String getEditApartment(@PathVariable int id, Model model) {
        ApartmentEntity apartmentEntity = apartmentService.findApartmentById(id);

        ApartmentEditForm form = new ApartmentEditForm();
        form.setId(apartmentEntity.getId());
        form.setDescription(apartmentEntity.getDescription());
        form.setName(apartmentEntity.getName());
        model.addAttribute("form", form);

        Collection<RoomEntity> rooms = apartmentEntity.getRoomsById();
        List<RoomEntity> roomList = new ArrayList<>(rooms);
        model.addAttribute("room_list", roomList);

        return "/seller/edit_apartment";
    }

    @PostMapping("/seller/edit_apartment/{id}")
    public String postEditApartment(@PathVariable int id, @ModelAttribute("form") ApartmentEditForm form, Model model) {
        ApartmentEntity apartmentEntity = apartmentService.findApartmentById(id);

        apartmentEntity.setName(form.getName());
        apartmentEntity.setDescription(form.getDescription());
        apartmentService.updateApartment(apartmentEntity);

        Collection<RoomEntity> rooms = apartmentEntity.getRoomsById();
        List<RoomEntity> roomList = new ArrayList<>(rooms);
        model.addAttribute("room_list", roomList);

        return "/seller/edit_apartment";
    }

    @GetMapping("/buyer/view_apartment/{id}")
    public String getViewApartment(@PathVariable int id, Model model) {
        ApartmentEntity apartmentEntity = apartmentService.findApartmentById(id);
        model.addAttribute("apartment", apartmentEntity);

        List<RoomEntity> roomList = new ArrayList<>(apartmentEntity.getRoomsById());
        if (!roomList.isEmpty()) {
            model.addAttribute("room_list", roomList);
        }
        return "/buyer/view_apartment";
    }

    private void extractCitiesAndCountriesFromAddressList(List<String> cities, List<String> countries, List<AddressEntity> addressEntityList) {
        for (AddressEntity address : addressEntityList) {
            if (!(cities.contains(address.getCity()))) {
                cities.add(address.getCity());
            }
            if (!(countries.contains(address.getCountry()))) {
                countries.add(address.getCountry());
            }
        }
    }
}
