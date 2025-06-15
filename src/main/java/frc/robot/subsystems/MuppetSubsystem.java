package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class MuppetSubsystem extends SubsystemBase{
    
    Boolean driving;
    Servo lefthandservo;
    Servo righthandservo;

    public MuppetSubsystem() {
        lefthandservo = new Servo(0);
        righthandservo = new Servo(1);
        driving = false;
    }

    public void drivestick(double forward, double rotate) {
        double right = clamp(forward*1.5 + 0.5);  
        double left = 1.0-right;

        right = right - rotate*1.5;
        left = left - rotate*1.5;

        if ((forward != 0) || (rotate != 0.0)) {
            driving = true;
            lefthandservo.set(left);
            righthandservo.set(right);
        }
        else if (driving) {
            driving = false;
            lefthandservo.set(0.5);
            righthandservo.set(0.5);          
        }
    }

    public void elevatorstick(double value) {
        if (!driving){
            righthandservo.set(value);
            lefthandservo.set(value);
        }
    }

    public void leftstick(double value){
        lefthandservo.set(value);
    }

    public void rightstick(double value){
        righthandservo.set(value);
    }

    private double clamp(double value){
        return Math.max(0.0, Math.min(1.0, value));
    }
}
