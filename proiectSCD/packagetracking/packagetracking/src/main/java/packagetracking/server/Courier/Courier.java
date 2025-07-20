package packagetracking.server.Courier;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
public class Courier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String email;
    private Boolean payOnDelivery;
    @ManyToOne
    @JoinColumn(name="manager_id",nullable = true)
    @JsonIgnore
    private Courier managerId;



}
