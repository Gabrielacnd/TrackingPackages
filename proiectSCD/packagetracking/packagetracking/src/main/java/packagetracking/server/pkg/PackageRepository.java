package packagetracking.server.pkg;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PackageRepository extends JpaRepository<Package,Integer> {
     List<Package> findByCourierId(Integer id);




}
