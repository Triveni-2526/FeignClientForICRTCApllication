package com.main.service;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.main.binding.Passenger;
import com.main.binding.TicketInformation;
import com.main.feignCilentInterface.FeignClientInterface;

@Service
public class Servicelayer{
	
	private  FeignClientInterface ticketFeignClient;

    public Servicelayer(FeignClientInterface ticketFeignClient) {
		this.ticketFeignClient = ticketFeignClient;
	}

	public TicketInformation bookTicketViaFeign(Passenger passenger) {
        ResponseEntity<TicketInformation> response = ticketFeignClient.bookTicketFromController(passenger);
        return response.getBody(); // returns TicketInformation
    }

    public TicketInformation getTicket(String pnr) {
    	ResponseEntity<TicketInformation> getresponse = ticketFeignClient.getTicketDataFromRestController(pnr);
        return getresponse.getBody();
    }

    public TicketInformation updateTicket(String pnr, Passenger passenger) {
    	ResponseEntity<TicketInformation> updateresponse=ticketFeignClient.updateTicket(pnr, passenger);
    	return updateresponse.getBody();
    }

    public String cancelTicket(String pnr) {
    	ResponseEntity<String> deleteresponse=ticketFeignClient.cancelTicket(pnr);
    	return deleteresponse.getBody();
    }

}
