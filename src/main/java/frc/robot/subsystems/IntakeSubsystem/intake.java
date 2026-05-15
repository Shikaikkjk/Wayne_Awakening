package frc.robot.subsystems.IntakeSubsystem;

import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class intake extends SubsystemBase {

    TalonFX intakemotor;
    SparkMax right_intakemotor;
    SparkMax left_intakemotor;
    RelativeEncoder right_Encoder;
    RelativeEncoder left_Encoder;

    public intake(){
        right_intakemotor = new SparkMax(0, MotorType.kBrushless);
        left_intakemotor = new SparkMax(0, MotorType.kBrushless);

        right_Encoder = right_intakemotor.getEncoder();
        left_Encoder = left_intakemotor.getEncoder();
    
    }
    public double getRightPosition(){
        return right_Encoder.getPosition();

    }

    public double getLeftPosition(){
        return left_Encoder.getPosition();
    }
    public void periodic(){
        SmartDashboard.putNumber("leftEncoder", getLeftPosition());
        SmartDashboard.putNumber("RightEncoder", getRightPosition());
    }
    
}
