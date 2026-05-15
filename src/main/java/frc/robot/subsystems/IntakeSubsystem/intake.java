package frc.robot.subsystems.IntakeSubsystem;

import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.intakeConstants;

public class intake extends SubsystemBase {

    TalonFX intakemotor;
    SparkMax right_intakemotor;
    SparkMax left_intakemotor;
    RelativeEncoder Left_Encoder;
    SparkClosedLoopController rackcontroller;

    public intake(){
        intakemotor = new TalonFX(intakeConstants.ID_intakeWheelsMotor);

        right_intakemotor = new SparkMax(intakeConstants.ID_rightIntakeMotor, MotorType.kBrushless);
        left_intakemotor = new SparkMax(intakeConstants.ID_leftIntakeMotor, MotorType.kBrushless);

        Left_Encoder = left_intakemotor.getEncoder();
        Left_Encoder.setPosition(0);
        
        SparkMaxConfig leftConfig = new SparkMaxConfig();
        SparkMaxConfig rightConfig = new SparkMaxConfig();

        leftConfig.smartCurrentLimit(40).idleMode(IdleMode.kBrake);
        rightConfig.smartCurrentLimit(40).idleMode(IdleMode.kBrake);
       
        rightConfig.follow(left_intakemotor, true);
        
        leftConfig.closedLoop
        .p(intakeConstants.pid.P)
        .i(intakeConstants.pid.I)
        .d(intakeConstants.pid.D);
        
        
        left_intakemotor.configure(leftConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        right_intakemotor.configure(rightConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
       
        rackcontroller = left_intakemotor.getClosedLoopController();

    }

    public double getLeftPosition(){
        return Left_Encoder.getPosition();
    }

    public void goToHomePosition(){
        rackcontroller.setSetpoint(intakeConstants.setpoints.intake_HOMED, ControlType.kPosition);
    }
    
    public void goToCollectPosition(){
         rackcontroller.setSetpoint(intakeConstants.setpoints.intake_COLLECTING, ControlType.kPosition);
    }

    public void collectfuel(){
        intakemotor.set(intakeConstants.intake_tunning_values.INTAKE_SPEED);
    }
    
    public void expellfuel(){
        intakemotor.set(intakeConstants.intake_tunning_values.EXPELL_SPEED);
    }

    public double getintakerpm(){
        return intakemotor.getVelocity().getValueAsDouble() * 60;
    }
    
    @Override
    public void periodic(){
        SmartDashboard.putNumber("leftEncoder", getLeftPosition());
    }
    
}
