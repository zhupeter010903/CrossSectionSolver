package org.joml.camera;

/**
 * Rotates a point on a circle/arc to reach a target angle using the rotation
 * direction with the shortest distance on the circle.
 * <p>
 * Angles are specified in radians.
 * 
 * @author Kai Burjack
 */
public class ArcRotor {

    public double maxAcceleration = Math.toRadians(250.0f);
    public double maxDeceleration = Math.toRadians(250.0f);
    public double target;
    public double current;
    public double velocity;

    public void update(float elapsedTimeInSeconds) {
        if (current == target) {
            return;
        }
        double currentToTarget = Math.PI - Math.abs((Math.abs(current - target) % (2.0 * Math.PI)) - Math.PI);
        if ((current - target + 2.0 * Math.PI) % (2.0 * Math.PI) < Math.PI) {
            currentToTarget *= -1.0f;
        }
        double directStopDistance = (velocity * velocity) / (2.0f * maxDeceleration);
        double acceleration = 0.0f;
        if (velocity * currentToTarget > 0.0f && directStopDistance >= Math.abs(currentToTarget)) {
            /* Decelerate */
            double directDec = maxDeceleration;
            acceleration = (currentToTarget < 0.0 ? -1 : 1) * -directDec;
        } else {
            /* Accelerate */
            double directAcc = maxAcceleration;
            acceleration = (currentToTarget < 0.0 ? -1 : 1) * directAcc;
        }
        velocity += acceleration * elapsedTimeInSeconds;
        double way = velocity * elapsedTimeInSeconds;
        if (velocity * currentToTarget > 0.0f && Math.abs(way) > Math.abs(currentToTarget)) {
            /* We would move too far */
            velocity = 0.0f;
            current = target;
        } else {
            current = (current + way + 2.0 * Math.PI) % (2.0 * Math.PI);
        }
    }

}