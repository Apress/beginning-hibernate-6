package chapter10.userrole;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity
@Data
@NoArgsConstructor

public class Role1 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    @Column(unique = true)
    String name;

    public Role1(String name) {
        this.name=name;
    }
}
