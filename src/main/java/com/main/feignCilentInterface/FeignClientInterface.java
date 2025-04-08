package com.main.feignCilentInterface;

import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.main.binding.Passenger;
import com.main.binding.TicketInformation;
;

@FeignClient(name="FeignProjectForirtc",url="http://localhost:8085/")

public interface FeignClientInterface {

	@PostMapping("/bookTicket") 
	public ResponseEntity<TicketInformation> bookTicketFromController(@RequestBody Passenger passenger);
	
	@GetMapping("/getTicket/{pnr}")
	
	
	public ResponseEntity<TicketInformation> getTicketDataFromRestController(@PathVariable String pnr);

	@PutMapping("/updateTicket/{pnr}")

	public ResponseEntity<TicketInformation> updateTicket(@PathVariable String pnr, @RequestBody Passenger passenger);
	
	@DeleteMapping("/cancelTicket/{pnr}")

	public ResponseEntity<String> cancelTicket(@PathVariable String pnr);
	
}
