package rs.ac.bg.etf.sn120348.bp2.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import rs.ac.bg.etf.sn120348.bp2.Roles;
import rs.ac.bg.etf.sn120348.bp2.entities.AccountUserEntity;
import rs.ac.bg.etf.sn120348.bp2.services.AccountUserService;

import javax.validation.Valid;
import java.util.Collection;

@Controller
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    AccountUserService accountUserService;

    @GetMapping("/")
    public String getIndex(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        }

        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) auth.getAuthorities();
        String role;
        for (SimpleGrantedAuthority authority : authorities) {
            role = authority.getAuthority();

            switch (role) {
                case Roles.SELLER:
                    return goToSellerHome(model);
                case Roles.BUYER:
                    return goToBuyerHome(model);
            }
        }
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String getLogin(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/";
        }

        return "index";
    }

    @GetMapping("/register")
    public String getRegister(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/";
        }

        model.addAttribute("form", new UserForm());

        return "register";
    }

    @PostMapping("/register")
    public String postRegister(@Valid @ModelAttribute("form") UserForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        if (accountUserService.findUserByUsername(form.getUsername()) != null) {
            bindingResult.rejectValue("username", "error.form", "*Username already exists");
        }

        if (accountUserService.findUserByEmail(form.getEmail()) != null) {
            bindingResult.rejectValue("email", "error.form", "*Email already exists");
        }

        if (bindingResult.hasErrors()) {
            return "register";
        }

        AccountUserEntity accountUserEntity = AccountUserEntity.convertUserFormToAccountUserEntity(form);
        int id = accountUserService.saveAccountUser(accountUserEntity, form.getRoleId());

        model.addAttribute("status", "success");

        return "registration-successful";
    }

    @GetMapping("/buyer/")
    public String goToBuyerHome(Model model) {
        return "buyer/index";
    }

    @GetMapping("/seller/")
    public String goToSellerHome(Model model) {
        return "seller/index";
    }

    @GetMapping("/buyer/profile_info")
    public String getBuyerInfo(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        AccountUserEntity userEntity = accountUserService.findUserByUsername(username);

        if (userEntity == null) {
            return "redirect:/logout";
        }

        UserForm form = new UserForm();
        form.setUsername(userEntity.getUsername());
        form.setFirstName(userEntity.getFirstname());
        form.setLastName(userEntity.getLastname());
        form.setEmail(userEntity.getEmail());
        form.setTelephone(userEntity.getTelephone());
        form.setCardNumber(userEntity.getCardNumber());
        form.setCardType(userEntity.getCardType());
        model.addAttribute("form", form);

        return "buyer/profile_info";
    }

    @PostMapping("/buyer/edit_profile")
    public String postBuyerInfo(@Valid @ModelAttribute("form") UserForm form, BindingResult bindingResult, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        AccountUserEntity userEntity = accountUserService.findUserByUsername(username);

        if (userEntity == null) {
            return "redirect:/logout";
        }

        userEntity.setFirstname(form.getFirstName());
        userEntity.setLastname(form.getLastName());
        userEntity.setEmail(form.getEmail());
        userEntity.setTelephone(form.getTelephone());
        userEntity.setCardNumber(form.getCardNumber());
        userEntity.setCardType(form.getCardType());

        accountUserService.updateAccountUser(userEntity);

        model.addAttribute("status", "success");

        return "buyer/profile_info";
    }

    @GetMapping("/seller/profile_info")
    public String getSellerInfo(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        AccountUserEntity userEntity = accountUserService.findUserByUsername(username);

        if (userEntity == null) {
            return "redirect:/logout";
        }

        UserForm form = new UserForm();
        form.setUsername(userEntity.getUsername());
        form.setFirstName(userEntity.getFirstname());
        form.setLastName(userEntity.getLastname());
        form.setEmail(userEntity.getEmail());
        form.setTelephone(userEntity.getTelephone());
        form.setPos(userEntity.getPos());
        model.addAttribute("form", form);

        return "seller/profile_info";
    }

    @PostMapping("/seller/edit_profile")
    public String postSellerInfo(@Valid @ModelAttribute("form") UserForm form, BindingResult bindingResult, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        AccountUserEntity userEntity = accountUserService.findUserByUsername(username);

        if (userEntity == null) {
            return "redirect:/logout";
        }

        userEntity.setFirstname(form.getFirstName());
        userEntity.setLastname(form.getLastName());
        userEntity.setEmail(form.getEmail());
        userEntity.setTelephone(form.getTelephone());
        userEntity.setPos(form.getPos());

        accountUserService.updateAccountUser(userEntity);

        model.addAttribute("status", "success");

        return "seller/profile_info";
    }
}
