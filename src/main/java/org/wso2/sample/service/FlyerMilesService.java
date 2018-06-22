package org.wso2.sample.service;

import org.wso2.sample.model.FlyerMiles;

public interface FlyerMilesService {
    FlyerMiles getMiles(String id);
    boolean isCustomerExists(String id);
    void addMiles(FlyerMiles flyerMiles);
    void deleteMiles(String id);
}
