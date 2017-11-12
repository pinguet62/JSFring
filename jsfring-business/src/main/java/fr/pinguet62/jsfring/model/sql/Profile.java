package fr.pinguet62.jsfring.model.sql;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "profile")
public class Profile implements Serializable {

    private static final long serialVersionUID = 1;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer id;

    @ManyToMany(fetch = EAGER)
    @JoinTable(name = "profiles_rights", joinColumns = @JoinColumn(name = "profile", nullable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "\"RIGHT\"", nullable = false, updatable = false))
    private Set<Right> rights;

    // Validation
    @Length(min = 1, max = 255)
    // JPA
    @Column(nullable = false)
    private String title;

    @ManyToMany
    @JoinTable(name = "users_profiles", joinColumns = @JoinColumn(name = "profile", nullable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "\"USER\"", nullable = false, updatable = false))
    private Set<User> users;

}