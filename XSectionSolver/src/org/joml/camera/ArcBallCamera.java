/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joml.camera;

import org.joml.Matrix4f;

/**
 * A simple arcball camera, which provides smooth acceleration/velocity/elapsed-time based movement/zoom and rotation.
 * <p>
 * It makes use of the {@link Vector3Mover} to follow the {@link #center(float, float, float) center} position and
 * uses the {@link ArcRotor} for the rotation angles.
 * 
 * @author Kai Burjack
 */
public class ArcBallCamera {

    public Vector3Mover centerMover = new Vector3Mover();
    {
        centerMover.maxDirectAcceleration = 5.0f;
        centerMover.maxDirectDeceleration = 5.0f;
    }

    public final ArcRotor alphaMover = new ArcRotor();
    public final ArcRotor betaMover = new ArcRotor();
    public final ScalarMover zoomMover = new ScalarMover();
    {
        zoomMover.current = 10.0f;
        zoomMover.target = 10.0f;
        zoomMover.maxAcceleration = 10.0f;
        zoomMover.maxDeceleration = 15.0f;
    }

    /**
     * Apply the camera's view transformation to the given matrix by post-multiplying it.
     * 
     * @param mat
     *          the matrix which gets post-multiplied by the camera's view transformation matrix
     * @return the supplied matrix
     */
    public Matrix4f viewMatrix(Matrix4f mat) {
        /*
         * Explanation:
         * - First, translate the center position back to the origin, so that we can rotate about it
         * - Then, rotate first about Y and then about X (this will ensure that "right" is always parallel to the world's XZ-plane)
         * - Next, translate the camera back by its distance to the center (the radius of the arcball)
         */
        return mat.translate(0, 0, (float) -zoomMover.current)
                  .rotateX((float) betaMover.current)
                  .rotateY((float) alphaMover.current)
                  .translate(-centerMover.current.x, -centerMover.current.y, -centerMover.current.z);
    }

    public void setAlpha(double alpha) {
        alphaMover.target = alpha % (2.0 * Math.PI);
    }

    public void setBeta(double beta) {
        if (beta < -Math.PI / 2.0) {
            beta = -Math.PI / 2.0;
        } else if (beta > Math.PI / 2.0) {
            beta = Math.PI / 2.0;
        }
        betaMover.target = beta;
    }

    public double getAlpha() {
        return alphaMover.target;
    }

    public double getBeta() {
        return betaMover.target;
    }

    public void zoom(double zoom) {
        zoomMover.target = zoom;
    }

    public void center(float x, float y, float z) {
        centerMover.target.set(x, y, z);
    }

    public void update(float elapsedTimeInSeconds) {
        alphaMover.update(elapsedTimeInSeconds);
        betaMover.update(elapsedTimeInSeconds);
        zoomMover.update(elapsedTimeInSeconds);
        centerMover.update(elapsedTimeInSeconds);
    }

}
