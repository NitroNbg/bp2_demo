package rs.ac.bg.etf.sn120348.bp2.bookings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import rs.ac.bg.etf.sn120348.bp2.entities.AccountUserEntity;
import rs.ac.bg.etf.sn120348.bp2.entities.ApartmentEntity;
import rs.ac.bg.etf.sn120348.bp2.entities.ReservationEntity;
import rs.ac.bg.etf.sn120348.bp2.entities.RoomEntity;
import rs.ac.bg.etf.sn120348.bp2.services.AccountUserService;
import rs.ac.bg.etf.sn120348.bp2.services.ApartmentService;
import rs.ac.bg.etf.sn120348.bp2.services.ReservationService;
import rs.ac.bg.etf.sn120348.bp2.services.RoomService;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
public class BookingDashboardController {

    @Autowired
    RoomService roomService;

    @Autowired
    AccountUserService accountUserService;

    @Autowired
    ApartmentService apartmentService;

    @Autowired
    ReservationService reservationService;

    @GetMapping("/seller/view_reservation/{id}")
    public String getViewReservations(@PathVariable int id, Model model) {
        List<ReservationEntity> reservationList = roomService.findReservationsByRoomId(id);
        model.addAttribute("reservation_list", reservationList);
        return "/seller/view_reservations";
    }

    @GetMapping("/seller/view_reservation")
    public String getViewReservationsForLoggedInUser(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        AccountUserEntity user = accountUserService.findUserByUsername(username);
        Collection<ApartmentEntity> apartmentEntityCollection = user.getApartmentsById();
        List<ReservationEntity> reservationList = new ArrayList<>();
        for (ApartmentEntity apartment : apartmentEntityCollection) {
            Collection<RoomEntity> roomsInApartment = apartment.getRoomsById();
            for (RoomEntity room : roomsInApartment) {
                reservationList.addAll(roomService.findReservationsByRoomId(room.getId()));
            }
        }
        model.addAttribute("reservation_list", reservationList);
        return "/seller/view_reservations";
    }

    @GetMapping("/buyer/booking_overview")
    public String getViewReservationsForBuyer(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        AccountUserEntity user = accountUserService.findUserByUsername(username);
        List<ReservationEntity> reservations = reservationService.findReservationsByUserId(user.getId());
        if (reservations != null && !reservations.isEmpty()) {
            model.addAttribute("reservation_list", reservations);
        }
        return "/buyer/booking_overview";
    }

    @GetMapping("/buyer/book_apartment/{id}")
    public String getBookApartment(@PathVariable int id, Model model) {
        SearchRoomForm form = new SearchRoomForm();
        form.setId(id);
        model.addAttribute("form", form);
        return "/buyer/book_apartment";
    }

    @PostMapping("/buyer/search_rooms")
    public String postSearchRooms(@Valid @ModelAttribute("form") SearchRoomForm form, BindingResult bindingResult, Model model) {
        if (form.getEnd_date().before(form.getStart_date())) {
            bindingResult.rejectValue("end_date", "error.form", "Ne sme biti pre datuma polaska");
        }
        if (bindingResult.hasErrors()) {
            return "redirect:/buyer/book_apartment/" + form.getId();
        }
        ApartmentEntity apartment = apartmentService.findApartmentById(form.getId());
        model.addAttribute("apartment", apartment);
        model.addAttribute("form", form);
        List<RoomEntity> availableRooms = roomService.findAvailableRooms(form.getId(), form.getStart_date(), form.getEnd_date());
        if (availableRooms != null && !availableRooms.isEmpty()) {
            model.addAttribute("available_rooms", availableRooms);
        }
        return "/buyer/search_rooms";
    }

    @PostMapping("/buyer/confirm_reservation/{id}")
    public String postConfirmReservation(@PathVariable int id, @ModelAttribute("form") SearchRoomForm form, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountUserEntity user = accountUserService.findUserByUsername(authentication.getName());
        ReservationEntity reservationEntity = new ReservationEntity();
        reservationEntity.setRoomId(id);
        reservationEntity.setStartDate(new Timestamp(form.getStart_date().getTime()));
        reservationEntity.setEndDate(new Timestamp(form.getEnd_date().getTime()));
        reservationEntity.setUserId(user.getId());
        reservationService.insertOrUpdateReservation(reservationEntity);

        model.addAttribute("status", "Usepšno ste napravili rezervaciju");
        return "redirect:/buyer/";
    }
}
