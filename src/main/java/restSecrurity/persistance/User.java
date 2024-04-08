package restSecrurity.persistance;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mindrot.jbcrypt.BCrypt;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name ="users")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private String Username;
    private String password;

    @JoinTable(name = "user_role", joinColumns = {
            @JoinColumn(name = "user_name",referencedColumnName = "username")},inverseJoinColumns = {
            @JoinColumn(name = "role_name",referencedColumnName = "name")})
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();

    public User(String username,String password){
        this.Username=username;
        this.password= password;
        String salt = BCrypt.gensalt();
        this.password = BCrypt.hashpw(password,salt);
    }
    public void addRole(Role role){
        roles.add(role);
        role.getUsers().add(this);
    }

    public void removeRole(Role role) {
        roles.remove(role);
        role.getUsers().remove(this);
    }

    public Set<String> getRolesAsString(){
        if(roles.isEmpty()){
            return null;
        }
        Set<String> rolesAsString = new HashSet<>();
        roles.forEach((role) -> {
            rolesAsString.add(role.getName());
        });
        return rolesAsString;
    }
    public boolean verifyUser(String password){
        return BCrypt.checkpw(password,this.password);
    }

}
