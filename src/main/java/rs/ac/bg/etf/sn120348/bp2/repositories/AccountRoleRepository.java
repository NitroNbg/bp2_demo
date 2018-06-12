package rs.ac.bg.etf.sn120348.bp2.repositories;

import org.springframework.data.repository.CrudRepository;
import rs.ac.bg.etf.sn120348.bp2.entities.AccountRoleEntity;

import java.util.List;

public interface AccountRoleRepository extends CrudRepository<AccountRoleEntity, Long> {
    List<AccountRoleEntity> findAll();
    AccountRoleEntity findById(int id);
    AccountRoleEntity findByRole(String role);
}
