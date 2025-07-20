package packagetracking.server.Courier;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import packagetracking.server.pkg.Package;
import packagetracking.server.pkg.Status;

import java.util.List;
import java.util.Optional;

public interface CourierRepository extends JpaRepository<Courier, Integer> {

    @Query("SELECT c FROM Courier c WHERE NOT EXISTS (SELECT p FROM Package p WHERE p.courier = c AND p.status = :pendingStatus)")
    List<Courier> findCouriersWithoutPendingPackages(@Param("pendingStatus") Status pendingStatus);

    @Query("SELECT c.managerId.id, c.managerId.name, COUNT(p) " +
            "FROM Courier c JOIN Package p ON p.courier = c " +
            "WHERE p.status = :deliveredStatus AND c.managerId IS NOT NULL " +
            "GROUP BY c.managerId.id, c.managerId.name")
    List<Object[]> findAllManagersAndDeliveredNumber(@Param("deliveredStatus") Status deliveredStatus);


}