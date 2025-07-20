package packagetracking.server.pkg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:8082")
public class PackageController {
    @Autowired
    private PackageService packageService;
    @PostMapping("/package")
    public Package create(@RequestBody Package myPackage){
        return packageService.create(myPackage);


    }
    @GetMapping("/package")
    public List<Package> getAllPackages(){
        return packageService.findAllPackages();

    }
    @DeleteMapping("/package/{id}")
    public void deletePackage(@PathVariable Integer id) {
        packageService.deletePackage(id);
    }

    @PutMapping("/package/{id}")
    public Package updatePackage(@PathVariable Integer id, @RequestBody Package updatedPackage) {
        return packageService.updatePackage(id, updatedPackage);
    }
    @GetMapping("/package/courier/{courierId}")
    public List<Package> getPackagesForCourier(@PathVariable Integer courierId) {
        return packageService.findPackagesByCourier(courierId);
    }

    @PostMapping("/package/deliver")
    public Package deliverPackage(@RequestBody Map<String, Integer> requestBody) {
        Integer packageId = requestBody.get("packageId");
        if (packageId == null) {
            throw new IllegalArgumentException("Package ID is required");
        }
        return packageService.deliverPackage(packageId);
    }
    @PutMapping("/packages/{packageId}/assign")
    public ResponseEntity<Package> assignPackage(
            @PathVariable Integer packageId,
            @RequestBody Map<String, Integer> request) {
        Integer courierId = request.get("courier_id");
        Package assignedPackage = packageService.assignPackage(packageId, courierId);
        return ResponseEntity.ok(assignedPackage);
    }

}
