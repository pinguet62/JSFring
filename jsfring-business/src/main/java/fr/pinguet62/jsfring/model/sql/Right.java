package fr.pinguet62.jsfring.model.sql;

import com.querydsl.core.annotations.Config;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

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