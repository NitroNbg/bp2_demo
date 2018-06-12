package rs.ac.bg.etf.sn120348.bp2.entities;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "account_role", schema = "bp2", catalog = "")
public class AccountRoleEntity {
    private int id;
    private String role;
    private Collection<AccountUserEntity> accountUsersById;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "role")
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccountRoleEntity that = (AccountRoleEntity) o;

        if (id != that.id) return false;
        if (role != null ? !role.equals(that.role) : that.role != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "accountRoleByRoleId")
    public Collection<AccountUserEntity> getAccountUsersById() {
        return accountUsersById;
    }

    public void setAccountUsersById(Collection<AccountUserEntity> accountUsersById) {
        this.accountUsersById = accountUsersById;
    }
}
