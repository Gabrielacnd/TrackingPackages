package packagetracking.server.pkg;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import packagetracking.server.Courier.Courier;


import java.util.Date;

@Entity
@Data
public class Package {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(updatable = false,nullable = false)
    @CreationTimestamp
    private Date createdOn;
    private String deliveryAdress;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
    @ManyToOne
    @JoinColumn(name="courier_id",nullable = false)
    private Courier courier;
}
