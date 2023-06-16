# Parking API

## Task
Create an API that allows you to receive and transmit information about parking lots: address, retail network, number of parking spaces, information about filled parking spaces, duration of use of each parking space (time of arrival of the car, time of departure of the car, number of the car), tickets issued, when entering the parking lot ( also contain car number)

## Features

- Create, update and delete Parking,ParkingSpot,Ticket
- Read from CSV files
- Write to CSV files
- Retrieve Parking,ParkingSpot,Ticket by id

## Technologies Used

- Java
- Spring Framework
- Maven

## Installation

To run this project locally, follow these steps:
1. Clone the repository:
   git clone https://github.com/rlazyrk/courseWork.git
2. Open project in your IDE
3. Build project with command : mvn clean install
4. Run the application with command : mvn spring-boot: run
5. The application will start running on `http://localhost:8080`.

## Usage

The following endpoints are available for interacting with the system:
### Parking
- `GET /parking` : Retrieve a list of all parking.
- `GET /parking/{id}` : Retrieve a specific parking lot for an id and all the parking spots that belong to it.
- `POST /parking` : Create a parking by the body requires.
- `PUT /parking/{id}` : Update an existing parking.
- `PUT /parking/{id}/parkingSpot` : Adds a new parking spot to an existing park
- `DELETE /parking/{id}` : Delete a parking by ID.
### ParkingSpot
- `GET /parkingSpot` : Retrieve a list of all parkingSpot.
- `GET /parkingSpot/{id}` : Retrieve a specific parkingSpot.
- `POST /parkingSpot` : Create a parkingSpot by the body requires.
- `PUT /parking/{id}` : Update an existing parking.
- `PUT /parkingSpot/{id}/ticket` : creates a new ticket and changes the existing parkingSpot accordingly.
- `DELETE /parking/{id}` : Delete a parkingSpot by ID.
### Ticket
- `GET /ticket` : Retrieve a list of all tickets.
- `GET /ticket/{id}` : Retrieve a specific ticket.
- `POST /ticket` : Create a ticket by the body requires.
- `PUT /ticket/{id}` : Update an existing ticket.
- `DELETE /ticket/{id}` : Delete a ticket by ID.

## Data Storage

The application stores data in CSV files located in the `src/main/resources/{className}` directory. Each file named with the format `{ClassName}-yyyy-mm-dd.csv`.

## Contact

For any questions, please contact [Rlazyrk@gmail.com].
