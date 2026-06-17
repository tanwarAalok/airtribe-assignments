package service;

import exception.RiderNotFoundException;
import model.Rider;

import java.util.ArrayList;
import java.util.List;

public class RiderService {

    List<Rider> riderList = new ArrayList<>();

    public Rider getRiderById(int id) throws RiderNotFoundException {
        for (Rider rider : riderList){
            if (rider.getId() == id){
                return rider;
            }
        }
        throw new RiderNotFoundException("Rider with id " + id + " not found.");
    }

    public void registerRider(String name, double location){
        Rider rider = new Rider(name, location);
        riderList.add(rider);
    }
}
