package rs.ac.bg.etf.sn120348.bp2.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.ac.bg.etf.sn120348.bp2.entities.AccountRoleEntity;
import rs.ac.bg.etf.sn120348.bp2.entities.AccountUserEntity;
import rs.ac.bg.etf.sn120348.bp2.repositories.AccountRoleRepository;
import rs.ac.bg.etf.sn120348.bp2.repositories.AccountUserRepository;

@Service("accountUserService")
public class AccountUserService {

    private static final Logger logger = LoggerFactory.getLogger(AccountUserService.class);

    @Autowired
    AccountUserRepository accountUserRepository;

    @Autowired
    AccountRoleRepository accountRoleRepository;

    @Autowired
    Md5PasswordEncoder passwordEncoder;

    public AccountUserEntity findUserById(int id) {
        AccountUserEntity user = accountUserRepository.findAccountUserEntityById(id);
        return user;
    }

    public AccountUserEntity findUserByUsername(String username) {
        AccountUserEntity user = accountUserRepository.findAccountUserEntityByUsername(username);
        return user;
    }

    public AccountUserEntity findUserByEmail(String email) {
        AccountUserEntity user = accountUserRepository.findAccountUserEntityByEmail(email);
        return user;
    }

    public int saveAccountUser(AccountUserEntity user, int roleId) {
        user.setPassHash(passwordEncoder.encodePassword(user.getPassHash(), null));

        AccountRoleEntity roleEntity = accountRoleRepository.findById(roleId);

        if (roleEntity == null) {
            logger.error("Couldn't find AccountRole with id {}", roleId);
        }

        user.setRoleId(roleEntity.getId());

        return accountUserRepository.save(user).getId();
    }

    public void updateAccountUser(AccountUserEntity user) {
        accountUserRepository.save(user);
    }
}
