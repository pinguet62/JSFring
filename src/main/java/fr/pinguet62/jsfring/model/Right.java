package fr.pinguet62.jsfring.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "\"RIGHT\"")
public class Right implements java.io.Serializable {

    /** Serial version UID. */
    private static final long serialVersionUID = 1;

    private String code;
    private Set<Profile> profiles = new HashSet<Profile>(0);
    private String title;

    public Right() {
    }

    public Right(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public Right(String code, String title, Set<Profile> profiles) {
        this.code = code;
        this.title = title;
        this.profiles = profiles;
    }

    /**
     * Compare two {@link Right}s by their {@link #code}.
     *
     * @return {@code true} if the two {@link #code} are equals (and not
     *         {@code null}), {@code false} otherwise.
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (!(obj instanceof Right))
            return false;
        Right other = (Right) obj;
        return Objects.equals(code, other.code);
    }

    @Id
    @Column(name = "CODE", unique = true, nullable = false, length = 30)
    public String getCode() {
        return code;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "profiles_rights", joinColumns = { @JoinColumn(name = "right", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "profile", nullable = false, updatable = false) })
    public Set<Profile> getProfiles() {
        return profiles;
    }

    @Column(name = "TITLE", nullable = false, length = 50)
    public String getTitle() {
        return title;
    }

    /**
     * Get the {@code hashCode} of the {@link #code}.
     *
     * @return The {@link String#hashCode()} of the {@link #code}.
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setProfiles(Set<Profile> profiles) {
        this.profiles = profiles;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
