package fr.pinguet62.jsfring.model.sql;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
    @JoinTable(name = "profiles_rights", joinColumns = {
            @JoinColumn(name = "profile", nullable = false, updatable = false) }, inverseJoinColumns = {
                    @JoinColumn(name = "\"RIGHT\"", nullable = false, updatable = false) })
    private Set<Right> rights;

    // Validation
    @Length(min = 1, max = 255)
    // JPA
    @Column(nullable = false, length = 255)
    private String title;

    @ManyToMany
    @JoinTable(name = "users_profiles", joinColumns = {
            @JoinColumn(name = "profile", nullable = false, updatable = false) }, inverseJoinColumns = {
                    @JoinColumn(name = "user", nullable = false, updatable = false) })
    private Set<User> users;

}