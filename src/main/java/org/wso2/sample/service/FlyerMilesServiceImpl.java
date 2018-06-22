package org.wso2.sample.service;

import org.springframework.stereotype.Service;
import org.wso2.sample.model.FlyerMiles;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Service("flyerMilesService")
public class FlyerMilesServiceImpl implements FlyerMilesService {
    Map<String, FlyerMiles> flyerMilesLookup = new HashMap<>();

    public FlyerMilesServiceImpl() {
        Properties prop = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream stream = loader.getResourceAsStream("config.properties");
        try {
            prop.load(stream);
            FlyerMiles flyerMiles = new FlyerMiles();
            flyerMiles.setCustomerId(prop.getProperty("customerId"));
            flyerMiles.setMilesFlown(Long.parseLong(prop.getProperty("milesFlown")));
            flyerMilesLookup.put(prop.getProperty("customerId"), flyerMiles);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public FlyerMiles getMiles(String id) {
        return flyerMilesLookup.get(id);
    }

    @Override
    public boolean isCustomerExists(String id) {
        return flyerMilesLookup.containsKey(id);
    }

    @Override
    public void addMiles(FlyerMiles flyerMiles) {
        flyerMilesLookup.put(flyerMiles.getCustomerId(), flyerMiles);
    }

    @Override
    public void deleteMiles(String id) {
        flyerMilesLookup.remove(id);
    }
}
