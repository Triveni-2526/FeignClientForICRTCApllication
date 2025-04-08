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
    public ResponseEntity<TicketInformation> getTicketInfoFromFeign(@PathVariable String pnr){
    	TicketInformation data=gatewayService.getTicket(pnr);
    	return new ResponseEntity<TicketInformation>(data,HttpStatus.OK);
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
