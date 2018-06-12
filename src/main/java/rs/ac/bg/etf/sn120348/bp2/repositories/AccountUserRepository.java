package rs.ac.bg.etf.sn120348.bp2.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rs.ac.bg.etf.sn120348.bp2.entities.AccountUserEntity;

@Repository
public interface AccountUserRepository extends CrudRepository<AccountUserEntity, Long> {

    AccountUserEntity findAccountUserEntityByUsername(String username);
    AccountUserEntity findAccountUserEntityByEmail(String email);
    AccountUserEntity findAccountUserEntityById(int id);

}
