package com.bjss.nhsd.a2si.idt.servicedataapi.endpoints;

import com.bjss.nhsd.a2si.idt.IdtServiceData;
import com.bjss.nhsd.a2si.idt.servicedataapi.domain.AcceptedForLocationCCGByTimePeriod;
import com.bjss.nhsd.a2si.idt.servicedataapi.domain.AcceptedForServiceIdByTimePeriod;
import com.bjss.nhsd.a2si.idt.servicedataapi.exceptions.AuthenticationException;

import com.bjss.nhsd.a2si.idt.servicedataapi.services.IdtServiceDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class IdtServiceDataController {


    private static final Logger logger = LoggerFactory.getLogger(IdtServiceDataController.class);

    private static final String serviceDataApiUsernameHttpHeaderName = "servicedata.api.username";
    private static final String serviceDataApiPasswordHttpHeadeerName = "servicedata.api.password";

    @Value("${servicedata.api.username}")
    private String serviceDataApiUsername;

    @Value("${servicedata.api.password}")
    private String serviceDataApiPassword;

    private IdtServiceDataService idtServiceDataService;

    @Autowired
    public IdtServiceDataController(IdtServiceDataService idtServiceDataService) {
        this.idtServiceDataService = idtServiceDataService;
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/idt-service-data-by-service-id/{serviceId}/{from}/{to}")
        public List<AcceptedForServiceIdByTimePeriod> getAcceptedByServiceIdForTimePeriod(
                @PathVariable String serviceId,
                @PathVariable String from,
                @PathVariable String to) { //,
                //@RequestHeader(serviceDataApiUsernameHttpHeaderName) String apiUsername,
                //@RequestHeader(serviceDataApiPasswordHttpHeadeerName) String apiPassword) {

      // validateApiCredentials(apiUsername, apiPassword);


        logger.debug("Getting AcceptedByTimePeriod objects for Service Id {} between  {} and {}", serviceId, from, to);

        List<AcceptedForServiceIdByTimePeriod> acceptedForServiceIdByTimePeriodList =
                idtServiceDataService.getAcceptedByTimePeriodForServiceId(serviceId, from, to);

        logger.debug("Returning Accepted For Service Id by Time Period List with value of {}", acceptedForServiceIdByTimePeriodList);

        return acceptedForServiceIdByTimePeriodList;
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/idt-service-data-by-location-ccg/{locationCCG}/{from}/{to}")
    public List<AcceptedForLocationCCGByTimePeriod> getAcceptedByLocationCCGForTimePeriod(
            @PathVariable String locationCCG,
            @PathVariable String from,
            @PathVariable String to) { //,
        //@RequestHeader(serviceDataApiUsernameHttpHeaderName) String apiUsername,
        //@RequestHeader(serviceDataApiPasswordHttpHeadeerName) String apiPassword) {

        // validateApiCredentials(apiUsername, apiPassword);

        logger.debug("Getting AcceptedByTimePeriod objects for location CCG {} between  {} and {}", locationCCG, from, to);

        List<AcceptedForLocationCCGByTimePeriod> acceptedForLocationCCGByTimePeriodList =
                idtServiceDataService.getAcceptedByTimePeriodForLocationCCG(locationCCG, from, to);

        logger.debug("Returning Accepted For Location CCG by Time Period List with value of {}", acceptedForLocationCCGByTimePeriodList);

        return acceptedForLocationCCGByTimePeriodList;
    }

    @PostMapping(value = "/idt-service-data")
    public void getAcceptedByLocationCCGForTimePeriod(
            @RequestHeader(serviceDataApiUsernameHttpHeaderName) String apiUsername,
            @RequestHeader(serviceDataApiPasswordHttpHeadeerName) String apiPassword,
            @RequestBody List<IdtServiceData> idtServiceDataList) {

        validateApiCredentials(apiUsername, apiPassword);

        logger.debug("Storing IDT System Case Info List {}", idtServiceDataList);

        idtServiceDataService.createIdtSystemCaseInfo(idtServiceDataList);

        logger.debug("Stored IDT System Case Info List {}", idtServiceDataList);
    }

    private void validateApiCredentials(String apiUsername, String apiPassword) {

        if (!serviceDataApiUsername.equals(apiUsername) || !serviceDataApiPassword.equals(apiPassword)) {
            throw new AuthenticationException("Username and Password could not be authenticated");
        }

    }
}
