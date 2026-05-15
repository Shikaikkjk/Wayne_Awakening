package frc.robot.constants;

import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;
import swervelib.math.Matter;
import swervelib.math.SwerveMath;

public class DriveConstants {
    public static final double maxSpeed = 2.0   ; // 4.72
    public static final double loopTime = 0.13;
    public static final double robotMass = 108.03;

    public static final double turnConstant = 6;

    public static final double wheelDiameterInMeters = Units.inchesToMeters(4);
    public static final double driveGearRatio = 6.75;
    public static final double angleGearRatio = 21.43;
    public static final int encoderResolution = 1;

    public static final Matter chassi = new Matter(new Translation3d(0, 0, Units.inchesToMeters(8)), robotMass);

    public static final double driveMotorConversion = SwerveMath.calculateMetersPerRotation(wheelDiameterInMeters,
            driveGearRatio, encoderResolution);
    public static final double steeringMotorConversion = SwerveMath.calculateDegreesPerSteeringRotation(angleGearRatio,
            encoderResolution);

    


}