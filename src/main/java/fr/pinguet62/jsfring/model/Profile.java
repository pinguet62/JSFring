package fr.pinguet62.jsfring.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "profile")
public class Profile implements Serializable {

    private static final long serialVersionUID = 1;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Integer id;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "profiles_rights", joinColumns = { @JoinColumn(name = "profile", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "\"RIGHT\"", nullable = false, updatable = false) })
    private Set<Right> rights = new HashSet<Right>(0);

    // Validation
    @Length(min = 5, max = 30)
    // JPA
    @Column(name = "TITLE", nullable = false, length = 30)
    private String title;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_profiles", joinColumns = { @JoinColumn(name = "profile", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "\"USER\"", nullable = false, updatable = false) })
    private Set<User> users = new HashSet<User>(0);

    public Profile() {
        // No action
    }

    public Profile(Integer id) {
        this.id = id;
    }

    public Profile(Integer id, String title) {
        this.id = id;
        this.title = title;
    }

    public Profile(String title) {
        this.title = title;
    }

    public Profile(String title, Set<User> users, Set<Right> rights) {
        this.title = title;
        this.users = users;
        this.rights = rights;
    }

    /**
     * Test equality of object by comparing their {@link #id}.
     *
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (!obj.getClass().equals(Profile.class))
            return false;
        Profile other = (Profile) obj;
        return Objects.equals(id, other.id);
    }

    public Integer getId() {
        return id;
    }

    public Set<Right> getRights() {
        return rights;
    }

    public String getTitle() {
        return title;
    }

    public Set<User> getUsers() {
        return users;
    }

    /**
     * Get the {@link Object#hashCode() hash} of {@link #id}.
     *
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRights(Set<Right> rights) {
        this.rights = rights;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

}