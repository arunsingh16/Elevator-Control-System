Elevator Control System
=======================

Task:
-----
Design and implement an elevator control system. What data structures, interfaces and algorithms will you need? Your elevator control system should be able to handle a few elevators — up to 16.

Design:
-------
The project has 2 main classes 'Elevator.java' and 'ElevatorControlSystem.java'.

#### Elevator.java
This class manages the Elevator's status, its direction and movement across floors.
 
#### ElevatorControlSystem.java
This class manages all the Elevators available in the system. Its responsible for managing Elevators' statuses and scheduling the pickUp requests from users on various floors of the building.

#### Direction.java
This class gives the possible directions an Elevator can move in.
```
public enum Direction {
        ELEVATOR_UP,
        ELEVATOR_DOWN,
        ELEVATOR_NONE;
}
```

#### ElevatorStatus.java
This class gives the possible statuses an Elevator can be in.
```
public enum ElevatorStatus {
    ELEVATOR_FUNCTIONAL,
    ELEVATOR_MAINTENANCE;
}
```

PickUp Requests Scheduling Algorithm:
-------------------------------------
```
*   Upper bound of active requests per elevator (Average per elevator) = activeRequests/elevatorCount + 1
*   Do not assign the request to an elevator if it is under MAINTENANCE or is already serving more than average number of active requests in the system.
*   Now, among all elevators excluding above ones, find the closest elevator moving in direction of request or is IDLE.
    * Case I   -   There are 2 elevators, 1 above the requestFloor coming down and 1 below the requestFloor which is coming up:
                   Assign the request to the closest of these 2.
                   return true
    * Case II  -   There is only 1 elevator moving towards the requestFloor.
                   Assign the request to the given elevator.
                   return true
    * Case III -   No elevators were found eligible to serve the request. Can happen if all the elevators are under MAINTENANCE
                   return false as we could not schedule the request to any of the elevators in the system.
```

Elevator Service Algorithm:
---------------------------
```
It can happen that an elevator is on its way from floor 1 to floor 10 and while it is at floor 5, elevator got a pickUp request for floor 8.
In this case, we should stop the elevator at floor 8 first and then proceed to floor 10. So a simple FCFS algorithm in elevator service queue will not work.

*   Each Elevator maintains 2 sets of floors to be serviced.
*   1 TreeSet for all pickUp requests from floors above the getCurrentFloor in ascending order.
*   1 TreeSet for all pickUp requests from floors below the getCurrentFloor in descending order.
*   TreeSet also helps avoid duplicate requests.
*   When an elevator moves:
    * Case I: 
      * Elevator was in NONE state which means elevator hasn't serviced any request yet or was done servicing all previous requests in the direction of its move.
      * Start servicing the request in the direction which has closest requestFloor and set the direction as UP or DOWN based on target floor's relative location.
    * Case II:
      * Elevator is moving UP.
      * Continue to move in this direction until all requestFloors above current floors are serviced.
      * When done servicing all above floors, reset the direction to NONE.
    * Case III:
      * Elevator is moving DOWN.
      * Continue to move in this direction until all requestFloors below current floors are serviced.
      * When done servicing all below floors, reset the direction to NONE.
```                        

Things to Improve:
------------------
1. All Elevators in current System are initialized at level 0. We can uniformly distribute the elevators between all floors for efficient start of scheduling.
2. Handle the cases where Elevator is sitting in "ELEVATOR_NONE" state for longer than "x" seconds. System could trigger a alert.
3. Better Exception handling and error handling
4. Synchronize the Elevator's Service Queue to which requests could be added and removed at the same time.
