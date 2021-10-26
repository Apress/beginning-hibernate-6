package chapter10.userrole;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class User1 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    @Column(unique = true)
    String name;
    boolean active;
    @ManyToMany
    Set<Role1> roles;

    public User1(String name, boolean active) {
        this.name = name;
        this.active = active;
    }

}
