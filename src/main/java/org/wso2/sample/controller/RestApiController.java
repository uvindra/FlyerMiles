package org.wso2.sample.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.wso2.sample.model.FlyerMiles;
import org.wso2.sample.service.FlyerMilesService;


@RestController
@RequestMapping("/api")
public class RestApiController {

    private static final Logger logger = LoggerFactory.getLogger(RestApiController.class);

    @Autowired
    private FlyerMilesService flyerMilesService; //Service which will do all data retrieval/manipulation work


    @RequestMapping(value = "/miles/{id}", method = RequestMethod.GET, produces = "application/xml")
    public ResponseEntity<?> getMiles(@PathVariable("id") String id) {
        logger.info("getMiles() invoked with id = " + id);

        if (flyerMilesService.isCustomerExists(id)) {
            FlyerMiles flyerMiles = flyerMilesService.getMiles(id);

            return new ResponseEntity<>(flyerMiles, HttpStatus.OK);
        } else {
            logger.info("getMiles() entry with id = " + id + " does not exist");
            return new ResponseEntity<>("Customer with id " + id + " not found.", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/miles/", method = RequestMethod.PUT, consumes = "application/xml",
            produces = "application/xml")
    public ResponseEntity<?> updateMiles(@RequestBody FlyerMiles flyerMiles, UriComponentsBuilder ucBuilder) {
        if (!isMandatoryFieldsSet(flyerMiles)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        String id = flyerMiles.getCustomerId();
        long milesFlown = flyerMiles.getMilesFlown();

        logger.info("updateMiles() invoked with id = " + id + ", miles = " + milesFlown);

        if (flyerMilesService.isCustomerExists(id)) {
            FlyerMiles existingMiles = flyerMilesService.getMiles(id);

            long currentMilesFlown = existingMiles.getMilesFlown();

            existingMiles.setMilesFlown(currentMilesFlown + milesFlown);

            logger.info("updateMiles() update existing id = " + id + ", updated miles = " + milesFlown);

            return new ResponseEntity<>(existingMiles, HttpStatus.OK);
        } else {
            flyerMilesService.addMiles(flyerMiles);

            logger.info("updateMiles() add new id = " + id + ", miles = " + milesFlown);

            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/api/miles/{id}").buildAndExpand(id).toUri());
            return new ResponseEntity<String>(headers, HttpStatus.CREATED);
        }
    }

    @RequestMapping(value = "/miles/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteMiles(@PathVariable("id") String id) {
        logger.info("deleteMiles() invoked with id = " + id);

        if (flyerMilesService.isCustomerExists(id)) {
            logger.info("deleteMiles() deleting entry for id = " + id);

            flyerMilesService.deleteMiles(id);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            logger.info("deleteMiles() entry with id = " + id + " does not exist");

            return new ResponseEntity<>("Unable to delete, customer with id " + id + " not found.",
                    HttpStatus.NOT_FOUND);
        }
    }

    private boolean isMandatoryFieldsSet(FlyerMiles flyerMiles) {
        if (flyerMiles.getMilesFlown() < 0L || flyerMiles.getMilesFlown() == 0L) {
            return false;
        }

        return flyerMiles.getCustomerId() != null;
    }

}
