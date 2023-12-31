package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class OuttakeController {

     Lift lift;
     public OuttakeArm arm;
     public OuttakeBox box;

     private boolean isUp;
     public final int LIFT_UP_MIN = 400;

     public OuttakeController(HardwareMap hardwareMap, Telemetry telemetry, Lift liftParam) {
         lift = liftParam;
         arm = new OuttakeArm(hardwareMap, telemetry);
         box = new OuttakeBox(hardwareMap, telemetry);

         isUp = false;
     }


    /** Brings the arm up
     * Precondition: Lift is up
     * Postcondition: Arm will be up
     */
    public void armUp(){
         if(liftIsUp()){
             arm.goTo(OuttakeArm.Position.UP);
             isUp = true;
         }
     }

    /** Brings arm down
     *  Precondition: Lift is UP
     *  Postcondition: Arm is DOWN
     */
    public void armDown(){
        if(liftIsUp()){
            arm.goTo(OuttakeArm.Position.DOWN);
            isUp = false;
        }
     }

    /**
     * Checks if lift is UP
     * @return true if life is UP, false if DOWN
     */
     public boolean liftIsUp() {
        if(lift.getEncoderValue() < LIFT_UP_MIN){
            return false;
        }
        return true;
     }

    /** returns if arm is up or not
     *
     * @return true if up false if down
     */
    public boolean getArmUp(){
         return isUp;
     }

     public void spinBoxIn() {
        box.intake();
     }

     public void spinBoxOut() {
        box.outtake();
     }

     public void stopBox() {
        box.stopSpinning();
     }

}
