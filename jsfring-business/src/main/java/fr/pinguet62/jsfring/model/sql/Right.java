package fr.pinguet62.jsfring.model.sql;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.querydsl.core.annotations.Config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(of = "code")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "\"RIGHT\"")
@Config(defaultVariableName = "right_")
public class Right implements Serializable {

    private static final long serialVersionUID = 1;

    @Id
    @Column(unique = true, nullable = false, length = 63)
    private String code;

    @ManyToMany(mappedBy = "rights")
    private Set<Profile> profiles;

    @Column(nullable = false)
    private String title;

}