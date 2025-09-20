# Space Telescope Assignment & Reservation System (STARS)

This application was created for my Software Contruction course: CPSC 210, and was supported by the teaching team in the course.

## Application Overview

### What is STARS?
**STARS** is a telescope management and reservation system designed for astronomical organizations, observatories, and rental programs that lend out equipment to employees or the public. The application helps managers catalog and track telescopes, and process rental requests efficiently.

As someone who initiated and manages one of the few affordable telescope rental programs in Vancouver, I have personally faced challenges in organizing incoming rental requests, tracking inventory, and addressing observation-related inquiries. **STARS** aims to streamline this process by providing an intuitive system for users to find the best telescope based on their experience level while ensuring managers can efficiently handle reservations and manage equipment availability.



## User Stories
* As a manager, I want to add a new telescope to the inventory so that users can reserve it.
* As a manager, I want to specify the type of telescope I want to add to my inventory.
* As a manager, I want to remove a telescope from the inventory so that reserved ones are no longer available for reservations.
* As a user, I want to filter available telescopes based on my experience level so that I can find one that is easy for me to use.
* As a user, I want to make a reservation for a telescope so that I can use it for my observation session.
* As a manager, I want to approve or deny a reservation request so that only valid bookings are confirmed.
* As a user/manager, I want the option of saving my current state of STARS for future reference before quitting the application.
* As a user/manager, I want an option in the main menu to access and view my saved STARS state if I am a returning user.

## Instructions for End User
- You can add a reservation by clicking the "Add Reservation" button and entering your name and a valid telescope ID.
- You can generate the first required action related to the user story "adding multiple Xs to a Y" by clicking "Add Reservation" button and entering your name and a valid telescope ID. You can also cancel any reservation by clicking "Cancel Reservation" and entering your reservation ID.
- You can generate the second required action by clicking "Filter by Telescope" and entering the name of a telescope.
- You can save the state of my application by clicking the "Save" button, which writes current reservations to ./data/reservations.json.
- You can reload the state of my application by clicking the "Load" button, which reads from ./data/reservations.json.





