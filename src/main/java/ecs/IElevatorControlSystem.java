package ecs;

import ecs.enums.ElevatorStatus;

/**
 * Created by Arun Singh on 4/3/16.
 */
public interface IElevatorControlSystem {

    /*
    Update the status of given Elevator.
     */
    public boolean updateStatus(ElevatorStatus elevatorStatus, int elevatorId);

    /*
    Add a pickUpRequest to the System. Request will be scheduled based on Scheduling Algorithm.
     */
    public boolean pickUpRequest(int fromFloor);

    /*
    Calls moveAndCheckIfServed on all Elevators on the system.
     */
    public void step();
}
