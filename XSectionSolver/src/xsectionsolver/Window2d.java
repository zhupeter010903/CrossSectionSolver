/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xsectionsolver;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBEasyFont.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.text.DecimalFormat;

import org.joml.Intersectionf;
import org.joml.Matrix3x2f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.joml.camera.OrthoCameraControl;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.mariuszgromada.math.mxparser.*;

public class Window2d implements Runnable{
    GLFWErrorCallback errorCallback;
    GLFWKeyCallback keyCallback;
    GLFWFramebufferSizeCallback fbCallback;
    GLFWWindowSizeCallback wsCallback;
    GLFWCursorPosCallback cpCallback;
    GLFWMouseButtonCallback mbCallback;
    GLFWScrollCallback sCallback;

    private long window;
    private int width = 1280;
    private int height = 720;
    private int fbWidth = 1280;
    private int fbHeight = 720;
    private String title;
    private Calculator cal;
    
    private float minX, minY;
    private float maxX, maxY;
    private float mouseX, mouseY;
    private int[] viewport = new int[4];
    private boolean translate;
    private boolean rotate;
    private OrthoCameraControl cam = new OrthoCameraControl(4);
    private Matrix3x2f tmp = new Matrix3x2f();
    private FloatBuffer fb = BufferUtils.createFloatBuffer(16);
    private Vector4f rect = new Vector4f();
    private Vector3f v = new Vector3f();
    private Vector3f v2 = new Vector3f();
    private Vector2f p = new Vector2f();
    private ByteBuffer charBuffer = BufferUtils.createByteBuffer(32 * 270);
    private float textScale = 3.1f;
    private float maxTicks = 17.0f;
    
    private int stepNum = 1000;
    private double[][] functionPoints;

    private DecimalFormat frmt = new DecimalFormat("0.###");
    
    public Window2d(int width, int height, String title){
        this.width = width;
        this.height = height;
        this.title = title;
    }
    
    public Window2d(int width, int height, String title,Calculator cal){
        this(width, height, title);
        this.cal = cal;
        functionPoints = new double[stepNum+1][3];
        
        double upperLimit=cal.getUpperLimit();
        double lowerLimit=cal.getLowerLimit();
        
        double step=(upperLimit-lowerLimit)/1000;
        for(int i = 0; i<=stepNum;i++){
            double x = lowerLimit+i*step;
            double y1 = cal.getFunction1().calculate(new Argument("x="+x));
            double y2 = cal.getFunction2().calculate(new Argument("x="+x));
            functionPoints[i][0] = x;
            functionPoints[i][1] = y1;
            functionPoints[i][2] = y2;
            
        }
    }
    
    public void run() {
        try {
            init();
            loop();
            glfwDestroyWindow(window);
            /*keyCallback.free();
            fbCallback.free();
            wsCallback.free();
            cpCallback.free();
            mbCallback.free();
            sCallback.free();*/
        } finally {
            //stop();
            //errorCallback.free();
        }
    }
    
    public void stop(){
        //glfwTerminate();
        glfwDestroyWindow(window);
    }

    private void toWorld(float x, float y) {
        float nx = (float) x / width * 2.0f - 1.0f;
        float ny = (float) (height - y) / height * 2.0f - 1.0f;
        cam.invviewproj().transformPosition(v.set(nx, ny, 0));
    }

    private void init() {
        glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));
        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");
        // Configure our window
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_SAMPLES, 4);
        window = glfwCreateWindow(width, height, title, NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");
        //System.out.println("Drag with the left mouse key to move around");
        //System.out.println("Drag with the right mouse key to rotate");
        //System.out.println("Use the mouse wheel to zoom in/out");
        glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                    glfwSetWindowShouldClose(window, true);
            }
        });
        glfwSetCursorPosCallback(window, cpCallback = new GLFWCursorPosCallback() {
            public void invoke(long window, double x, double y) {
                cam.onMouseMove((int) x, height - (int) y);
                mouseX = (float) x;
                mouseY = (float) y;
            }
        });
        glfwSetScrollCallback(window, sCallback = new GLFWScrollCallback() {
            public void invoke(long window, double xoffset, double yoffset) {
                float scale;
                if (yoffset > 0.0) {
                    scale = 1.2f;
                } else {
                    scale = 1.0f / 1.2f;
                }
                cam.zoom(scale);
            }
        });
        glfwSetMouseButtonCallback(window, mbCallback = new GLFWMouseButtonCallback() {
            public void invoke(long window, int button, int action, int mods) {
                if (action == GLFW_PRESS)
                    cam.onMouseDown(button);
                else
                    cam.onMouseUp(button);
            }
        });
        glfwSetFramebufferSizeCallback(window, fbCallback = new GLFWFramebufferSizeCallback() {
            public void invoke(long window, int w, int h) {
                if (w > 0 && h > 0) {
                    fbWidth = w;
                    fbHeight = h;
                }
            }
        });
        glfwSetWindowSizeCallback(window, wsCallback = new GLFWWindowSizeCallback() {
            public void invoke(long window, int w, int h) {
                if (w > 0 && h > 0) {
                    width = w;
                    height = h;
                    cam.setSize(w, h);
                }
            }
        });
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(window, (vidmode.width() - width) / 2, (vidmode.height() - height) / 2);
        IntBuffer framebufferSize = BufferUtils.createIntBuffer(2);
        nglfwGetFramebufferSize(window, memAddress(framebufferSize), memAddress(framebufferSize) + 4);
        fbWidth = framebufferSize.get(0);
        fbHeight = framebufferSize.get(1);
        cam.setSize(width, height);
    }

    private float stippleOffsetY(int width) {
        cam.invviewproj().unprojectInv(0, 0, 0, viewport, v);
        float x0 = v.x, y0 = v.y;
        cam.invviewproj().unprojectInv(0, width, 0, viewport, v);
        float x1 = v.x, y1 = v.y;
        float len = (float) Math.sqrt((x1 - x0) * (x1 - x0) + (y1 - y0) * (y1 - y0));
        return y0 % len - len * 0.25f;
    }

    private float stippleOffsetX(int width) {
        cam.invviewproj().unprojectInv(0, 0, 0, viewport, v);
        float x0 = v.x, y0 = v.y;
        cam.invviewproj().unprojectInv(width, 0, 0, viewport, v);
        float x1 = v.x, y1 = v.y;
        float len = (float) Math.sqrt((x1 - x0) * (x1 - x0) + (y1 - y0) * (y1 - y0));
        return x0 % len - len * 0.25f;
    }

    private float tick(float range, float subs) {
        return tick(range, subs, false);
    }

    private float tick(float range, float subs, boolean sub) {
        float tempStep = range / subs;
        float mag = (float) Math.floor(Math.log10(tempStep));
        float magPow = (float) Math.pow(10.0, mag);
        float magMsd = (int) (tempStep / magPow + 0.5f);
        if (magMsd > 5.0)
            magMsd = sub ? 2.0f : 10.0f;
        else if (magMsd > 2.0f)
            magMsd = sub ? 1.0f : 5.0f;
        else if (magMsd > 1.0f)
            magMsd = sub ? 0.5f : 2.0f;
        else if (magMsd == 1.0f)
            magMsd = sub ? 0.2f : 1.0f;
        return magMsd * magPow;
    }

    private float diagonal() {
        cam.invviewproj().transformPosition(v.set(-1, -1, 0));
        float x = v.x, y = v.y;
        cam.invviewproj().transformPosition(v.set(+1, +1, 0));
        float x2 = v.x, y2 = v.y;
        return (float) Math.sqrt((x2 - x) * (x2 - x) + (y2 - y) * (y2 - y));
    }

    private float px(int px) {
        cam.invviewproj().unprojectInv(0, 0, 0, viewport, v);
        float x0 = v.x, y0 = v.y;
        cam.invviewproj().unprojectInv(px, 0, 0, viewport, v);
        float x1 = v.x, y1 = v.y;
        return (float) Math.sqrt((x1 - x0) * (x1 - x0) + (y1 - y0) * (y1 - y0));
    }

    private void renderGrid() {
        Vector2f min = new Vector2f();
        Vector2f v0 = new Vector2f();
        Vector2f v1 = new Vector2f();
        cam.viewSpan(min, v0, v1);
        glColor3f(0.6f, 0.6f, 0.6f);
        glLineWidth(2.1f);
        glBegin(GL_LINES);
        float sx = stippleOffsetX(16);
        float sy = stippleOffsetY(16);
        float subticks = tick(diagonal(), maxTicks, true);
        float startX = subticks * (float) Math.floor(minX / subticks);
        float subtickLen = px(5);
        for (float x = startX; x <= maxX; x += subticks) {
            glVertex2f(x, 0);
            glVertex2f(x, +subtickLen);
        }
        float startY = subticks * (float) Math.floor(minY / subticks);
        for (float y = startY; y <= maxY; y += subticks) {
            glVertex2f(-subtickLen, y);
            glVertex2f(0, y);
        }
        glEnd();
        glLineWidth(1.2f);
        glEnable(GL_LINE_STIPPLE);
        glLineStipple(1, (short) 0xFC);
        glBegin(GL_LINES);
        float ticks = tick(diagonal(), maxTicks);
        startX = ticks * (float) Math.floor(minX / ticks);
        for (float x = startX; x <= maxX; x += ticks) {
            glVertex2f(x, minY - sy);
            glVertex2f(x, maxY + sy);
        }
        startY = ticks * (float) Math.floor(minY / ticks);
        for (float y = startY; y <= maxY; y += ticks) {
            glVertex2f(minX - sx, y);
            glVertex2f(maxX + sx, y);
        }
        glEnd();
        glDisable(GL_LINE_STIPPLE);

        // Main axes
        glLineWidth(1.7f);
        glBegin(GL_LINES);
        glColor3f(0.5f, 0.2f, 0.2f);
        glVertex2f(minX, 0);
        glVertex2f(maxX, 0);
        glColor3f(0.2f, 0.5f, 0.2f);
        glVertex2f(0, minY);
        glVertex2f(0, maxY);
        glEnd();
        glLineWidth(1.0f);

        // unit square
        /*glColor3f(0.2f, 0.4f, 0.6f);
        glLineWidth(1.9f);
        glBegin(GL_LINES);
        for (int i = -1; i <= +1; i++) {
            if (i == 0)
                continue;
            glVertex2f(i, -1);
            glVertex2f(i, +1);
            glVertex2f(-1, i);
            glVertex2f(+1, i);
        }
        glEnd();
        glLineWidth(1.0f);*/
    }

    private boolean snapX(float edge, float x2, float y2, float x3, float y3) {
        cam.invviewproj().transformPosition(v2.set(edge, +1, 0));
        float x0 = v2.x, y0 = v2.y;
        cam.invviewproj().transformPosition(v2.set(edge, -1, 0));
        float x1 = v2.x, y1 = v2.y;
        if (Intersectionf.intersectLineLine(x0, y0, x1, y1, x2, y2, x3, y3, p)) {
            cam.viewproj().transformPosition(v2.set(p.x, p.y, 0));
            return v2.x >= -1.1f && v2.y >= -1.1f && v2.x <= 1.1f && v2.y <= 1.1f;
        }
        return false;
    }

    private boolean snapY(float edge, float x2, float y2, float x3, float y3) {
        cam.invviewproj().transformPosition(v2.set(-1, edge, 0));
        float x0 = v2.x, y0 = v2.y;
        cam.invviewproj().transformPosition(v2.set(+1, edge, 0));
        float x1 = v2.x, y1 = v2.y;
        if (Intersectionf.intersectLineLine(x0, y0, x1, y1, x2, y2, x3, y3, p)) {
            cam.viewproj().transformPosition(v2.set(p.x, p.y, 0));
            return v2.x >= -1.1f && v2.y >= -1.1f && v2.x <= 1.1f && v2.y <= 1.1f;
        }
        return false;
    }

    private float textWidth(String text) {
        return stb_easy_font_width(text) * textScale / width;
    }

    private float textHeight(String text) {
        return stb_easy_font_height(text) * textScale / height;
    }

    private void renderTickLabels() {
        glEnableClientState(GL_VERTEX_ARRAY);
        glVertexPointer(2, GL_FLOAT, 16, charBuffer);
        glMatrixMode(GL_MODELVIEW);
        glPushMatrix();
        glLoadIdentity();
        glMatrixMode(GL_PROJECTION);
        glPushMatrix();
        float subticks = tick(diagonal(), maxTicks);
        float startX = subticks * (float) Math.floor(minX / subticks);
        float xoff = 8.0f / width;
        float yoff = 8.0f / height;
        for (float x = startX; x <= maxX; x += subticks) {
            if (Math.abs(x) < 1E-5f)
                continue;
            String text = frmt.format(x);
            float textWidth = textWidth(text);
            float textHeight = textHeight(text);
            cam.viewproj().transformPosition(v.set(x, 0, 0));
            if (v.x < -1 && snapX(-1, x, -1, x, +1)) {
                glColor3f(0.5f, 0.3f, 0.3f);
                v.set(v2);
                v.x += xoff;
            } else if (v.x > +1 && snapX(+1, x, -1, x, +1)) {
                glColor3f(0.5f, 0.3f, 0.3f);
                v.set(v2);
                v.x -= textWidth + xoff;
            } else if (v.y < -1 && snapY(-1, x, -1, x, +1)) {
                glColor3f(0.5f, 0.3f, 0.3f);
                v.set(v2);
                v.y += textHeight * 0.8f;
            } else if (v.y > +1 && snapY(+1, x, -1, x, +1)) {
                glColor3f(0.5f, 0.3f, 0.3f);
                v.set(v2);
                v.y -= yoff;
            } else {
                glColor3f(0.3f, 0.3f, 0.3f);
                v.y -= yoff;
                v.x += xoff;
            }
            glLoadIdentity();
            glTranslatef(v.x, v.y, 0);
            glScalef(textScale / width, -textScale / height, 0.0f);
            int quads = stb_easy_font_print(0, 0, text, null, charBuffer);
            glDrawArrays(GL_QUADS, 0, quads * 4);
        }
        float startY = subticks * (float) Math.floor(minY / subticks);
        for (float y = startY; y <= maxY; y += subticks) {
            if (Math.abs(y) < 1E-5f)
                continue;
            String text = frmt.format(y);
            float textWidth = textWidth(text);
            float textHeight = textHeight(text);
            cam.viewproj().transformPosition(v.set(0, y, 0));
            if (v.y < -1 && snapY(-1, -1, y, +1, y)) {
                glColor3f(0.3f, 0.5f, 0.3f);
                v.set(v2);
                v.y += textHeight * 0.8f;
            } else if (v.y > +1 && snapY(+1, -1, y, +1, y)) {
                glColor3f(0.3f, 0.5f, 0.3f);
                v.set(v2);
                v.y -= yoff;
            } else if (v.x < -1 && snapX(-1, -1, y, +1, y)) {
                glColor3f(0.3f, 0.5f, 0.3f);
                v.set(v2);
                v.x += xoff;
            } else if (v.x > +1 && snapX(+1, -1, y, +1, y)) {
                glColor3f(0.3f, 0.5f, 0.3f);
                v.set(v2);
                v.x -= textWidth + xoff;
            } else {
                v.x += xoff;
                v.y -= yoff;
                glColor3f(0.3f, 0.3f, 0.3f);
            }
            glLoadIdentity();
            glTranslatef(v.x, v.y, 0);
            glScalef(textScale / width, -textScale / height, 0.0f);
            int quads = stb_easy_font_print(0, 0, text, null, charBuffer);
            glDrawArrays(GL_QUADS, 0, quads * 4);
        }
        glDisableClientState(GL_VERTEX_ARRAY);
        glPopMatrix();
        glMatrixMode(GL_PROJECTION);
        glPopMatrix();
    }

    private void renderMouseCursorCoordinates() {
        glEnableClientState(GL_VERTEX_ARRAY);
        glVertexPointer(2, GL_FLOAT, 16, charBuffer);
        glMatrixMode(GL_MODELVIEW);
        glPushMatrix();
        glLoadIdentity();
        glMatrixMode(GL_PROJECTION);
        glPushMatrix();
        glLoadIdentity();
        cam.invviewproj().unprojectInv(mouseX, height - mouseY, 0, viewport, v);
        String str = frmt.format(v.x) + "\n" + frmt.format(v.y);
        float ndcX = (mouseX-viewport[0])/viewport[2]*2.0f-1.0f;
        float ndcY = (viewport[3]-mouseY-viewport[1])/viewport[3]*2.0f-1.0f;
        glTranslatef(ndcX, ndcY, 0);
        int quads = stb_easy_font_print(0, 0, str, null, charBuffer);
        glScalef(textScale / width, -textScale / height, 0.0f);
        glTranslatef(5, -15, 0);
        glColor3f(0.3f, 0.3f, 0.3f);
        glDrawArrays(GL_QUADS, 0, quads * 4);
        glDisableClientState(GL_VERTEX_ARRAY);
        glPopMatrix();
        glMatrixMode(GL_PROJECTION);
        glPopMatrix();
    }
    
    private void drawFunction(){
        //float upperLimit=(float)cal.getUpperLimit();
        //float lowerLimit=(float)cal.getLowerLimit();
        
        glBegin(GL11.GL_LINE_STRIP);
        glLineWidth(2.5f);
        glColor3f(0.0f,0.0f,0.0f);
        for(int i=0;i<=stepNum;i++){
            if(functionPoints[i][1]==Double.NaN){
                glEnd();
                glBegin(GL11.GL_LINE_STRIP);
            }
            else{
                glVertex2f((float)functionPoints[i][0], (float)functionPoints[i][1]);
            }
            //glVertex2f((float)(j+step), (float)y);
        }
        glEnd();
        glLineWidth(1.0f);
        
        glBegin(GL11.GL_LINE_STRIP);
        glLineWidth(2.5f);
        glColor3f(0.0f,0.0f,0.0f);
        for(int i=0;i<=stepNum;i++){
            if(functionPoints[i][2]==Double.NaN){
                glEnd();
                glBegin(GL11.GL_LINE_STRIP);
            }
            else{
                glVertex2f((float)functionPoints[i][0], (float)functionPoints[i][2]);
            }
            //glVertex2f((float)(j+step), (float)y);
        }
        glEnd();
        glLineWidth(1.0f);
    }

    private void computeVisibleExtents() {
        cam.viewRect(rect);
        minX = rect.x;
        minY = rect.y;
        maxX = rect.z;
        maxY = rect.w;
    }

    private void loop() {
        glfwMakeContextCurrent(window);
        GL.createCapabilities();
        glClearColor(0.97f, 0.97f, 0.97f, 1.0f);
        while (!glfwWindowShouldClose(window)) {
            glfwPollEvents();
            glViewport(0, 0, fbWidth, fbHeight);
            viewport[2] = fbWidth; viewport[3] = fbHeight;
            glClear(GL_COLOR_BUFFER_BIT);
            computeVisibleExtents();
            glMatrixMode(GL_PROJECTION);
            glLoadMatrixf(cam.viewproj().get(fb));
            renderGrid();
            renderTickLabels();
            drawFunction();
            //renderMouseCursorCoordinates();
            glfwSwapBuffers(window);
        }
    }

}