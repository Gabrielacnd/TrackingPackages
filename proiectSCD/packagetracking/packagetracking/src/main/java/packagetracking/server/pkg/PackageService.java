package packagetracking.server.pkg;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import packagetracking.server.Courier.Courier;
import packagetracking.server.Courier.CourierRepository;

import java.util.List;

@Service
public class PackageService {
    @Autowired
    private PackageRepository packageRepository;
    private CourierRepository courierRepository;

    public Package create(Package myPackage) {
        return packageRepository.save(myPackage);
    }

    public List<Package> findAllPackages() {
        return packageRepository.findAll();
    }

    public void deletePackage(Integer id) {
        packageRepository.deleteById(id);
    }

    public Package updatePackage(Integer id, Package updatedPacked) {
        Package existingPackage = packageRepository.findById(id).orElse(null);
        if (existingPackage != null) {
            existingPackage.setDeliveryAdress(updatedPacked.getDeliveryAdress());
            existingPackage.setStatus(updatedPacked.getStatus());
            existingPackage.setCreatedOn(updatedPacked.getCreatedOn());
            existingPackage.setCourier(updatedPacked.getCourier());
            return packageRepository.save(existingPackage);
        }
        else {
            return null;
        }
    }
    public List<Package> findPackagesByCourier(Integer courierId)
    {
        return packageRepository.findByCourierId(courierId);
    }
    public Package deliverPackage(Integer packageId) {
        // Găsim pachetul după ID
        Package pkg = packageRepository.findById(packageId)
                .orElseThrow(() -> new IllegalArgumentException("Package not found"));

        // Verificăm dacă pachetul poate fi livrat
        if (pkg.getStatus() == Status.DELIVERED) {
            throw new IllegalStateException("Package is already delivered");
        }

        // Actualizăm statusul pachetului
        pkg.setStatus(Status.DELIVERED);
        return packageRepository.save(pkg);
    }
    public Package assignPackage(Integer packageId, Integer courierId) {
        Package pkg = packageRepository.findById(packageId)
                .orElseThrow(() -> new IllegalArgumentException("Package not found"));
        Courier courier = courierRepository.findById(courierId)
                .orElseThrow(() -> new IllegalArgumentException("Courier not found"));
        pkg.setCourier(courier);
        pkg.setStatus(Status.ASSIGNED); // Setăm statusul la ASSIGNED când este asignat un curier
        return packageRepository.save(pkg);
    }



}