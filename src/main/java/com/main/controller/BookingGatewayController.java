package com.main.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.main.binding.Passenger;
import com.main.binding.TicketInformation;
import com.main.service.Servicelayer;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
public class BookingGatewayController {
	
    private Servicelayer gatewayService;
   

    public BookingGatewayController(Servicelayer gatewayService) {
		this.gatewayService = gatewayService;
	}

	@PostMapping("/book")
	public ResponseEntity<TicketInformation> postTicketInfoFromFeign(@RequestBody Passenger passenger) {
       TicketInformation bookTicket=gatewayService.bookTicketViaFeign(passenger);
       return new ResponseEntity<TicketInformation>(bookTicket,HttpStatus.OK);
    }

	@GetMapping("/getTicket/{pnr}")
	@Retry(name = "myServiceRetry", fallbackMethod = "fallbackGetTicketData")
	@CircuitBreaker(name = "myServiceCB", fallbackMethod = "fallbackGetTicketData")
	public ResponseEntity<TicketInformation> getTicketInfoFromFeign(@PathVariable String pnr) {
	    TicketInformation data = gatewayService.getTicket(pnr);
	    return new ResponseEntity<>(data, HttpStatus.OK);
	}
	public ResponseEntity<String> fallbackGetTicketData(String pnr, Exception e) {
		
		return new ResponseEntity<>("Ticket service is currently unavailable. Please try again later , Some Internal Errors Occured....", HttpStatus.SERVICE_UNAVAILABLE);
		
		
		
//	    if (e instanceof FeignException && ((FeignException) e).status() == 500) {
//	        return new ResponseEntity<>("Ticket service is currently unavailable. Please try again later.", HttpStatus.SERVICE_UNAVAILABLE);
//	    }
//	    return new ResponseEntity<>("Ticket service is currently unavailable. Please try again later , Some Internal Errors Occured....", HttpStatus.SERVICE_UNAVAILABLE);
	}

    @PutMapping("/updateTicket/{pnr}")
    public ResponseEntity<TicketInformation> updateTicketInfoFromFeign(@PathVariable String pnr, @RequestBody Passenger passenger) {
    	TicketInformation updatedTicket = gatewayService.updateTicket(pnr, passenger);
        return new ResponseEntity<TicketInformation>(updatedTicket, HttpStatus.OK);
      }

    @DeleteMapping("/deleteTicket/{pnr}")
    public ResponseEntity<String> deleteTicketInfoFromFeign(@PathVariable String pnr) {
    		String isDeleted = gatewayService.cancelTicket(pnr);   		
    		if (isDeleted != null) {
    			return new ResponseEntity<>("Ticket with PNR " + pnr + " has been cancelled.", HttpStatus.OK);
    		} else {
    			return new ResponseEntity<>("Ticket not found with PNR " + pnr, HttpStatus.NOT_FOUND);
    		}
    		
    }
}
