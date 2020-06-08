Project Description:
FlightsBookingSystem is an e-business system that consist of multiple Travel Agencies(clients) and multiples
Airlines Companies(servers). The travel agencies can buy in quantity (block) of tickets from the Airlines with
lower prices. A specialize service is used to automatically calculate the price of a ticket based on the Airline 
Company business logic; If the new price is lower than the previous price, the Airline notify the Travel Agency that
there is a promotional price, and the TA can decide to purchase some tickets or not too.

In this project, some advanced programming concepts such as multithreading, distributed system(client/server architecture),
and event/listening( when the price change, the Airline emit an event to notify the Travel Agency)