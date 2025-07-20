package packagetracking.server.Courier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8082")
public class CourierController {
    @Autowired
    private CourierService courierService;
    @PostMapping("/courier")
    public Courier create(@RequestBody Courier myCourier){
        return courierService.create(myCourier);


    }
    @GetMapping("/courier")
    public List<Courier> getAllCourier(){
        return courierService.findAllCourier();

    }
    @DeleteMapping("/courier/{id}")
    public void deleteCourier(@PathVariable Integer id) {
        courierService.deleteCourier(id);
    }

    @PutMapping("/courier/{id}")
    public Courier updateCourier(@PathVariable Integer id, @RequestBody Courier updatedCourier) {
        return courierService.updateCourier(id, updatedCourier);
    }
    @GetMapping("/managers/delivered-packages-count")
    public List<Object[]> getAllManagersAndDeliveredNumber() {
        return courierService.getAllManagersAndDeliveredNumber();
    }


    @GetMapping("/couriers/no-pending-packages")
    public List<Courier> getAllCouriersWithoutPendingPackages() {
        return courierService.getAllCouriersWithoutPendingPackages();
    }

}
