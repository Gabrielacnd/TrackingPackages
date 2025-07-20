package packagetracking.server.Courier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import packagetracking.server.pkg.Status;

import java.util.List;

@Service
public class CourierService {
    @Autowired
    private CourierRepository courierRepository;

    public Courier create(Courier myCourier) {
        return courierRepository.save(myCourier);
    }

    public List<Courier> findAllCourier() {
        return courierRepository.findAll();
    }

    public void deleteCourier(Integer id) {
        courierRepository.deleteById(id);
    }

    public Courier updateCourier(Integer id, Courier updatedCourier) {
        Courier existingCourier = courierRepository.findById(id).orElse(null);
        if (existingCourier != null) {
            existingCourier.setName(updatedCourier.getName());
            existingCourier.setEmail(updatedCourier.getEmail());
            existingCourier.setManagerId(updatedCourier.getManagerId());
            existingCourier.setPayOnDelivery(updatedCourier.getPayOnDelivery());
            return courierRepository.save(existingCourier);
        }
        else {
            return null;
        }
    }
    public List<Object[]> getAllManagersAndDeliveredNumber() {
        Status deliveredStatus = Status.DELIVERED;
        List<Object[]> result = courierRepository.findAllManagersAndDeliveredNumber(deliveredStatus);


        return result;
    }

    public List<Courier> getAllCouriersWithoutPendingPackages() {
        return courierRepository.findCouriersWithoutPendingPackages(Status.PENDING);
    }


}
