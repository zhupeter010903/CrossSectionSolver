package xsectionsolver;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.camera.ArcBallCamera;
import org.joml.camera.ViewSettings;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL20.glUseProgram;

public class Window3d {
    GLFWErrorCallback errorCallback;
    GLFWKeyCallback keyCallback;
    GLFWFramebufferSizeCallback fbCallback;
    GLFWCursorPosCallback cpCallback;
    GLFWScrollCallback sCallback;
    GLFWMouseButtonCallback mbCallback;

    private long window;
    private int width = 800;
    private int height = 600;
    private int x, y;
    private float zoom = 20;
    private float lookx=0;
    private float looky=0;
    private float lookz=0;
    private int mouseX, mouseY;
    private boolean down;
    private String title = "Hello ArcBall Camera!";
    private double fps_cap=60, time, processedTime = 0;
    private boolean[] keyDown = new boolean[GLFW.GLFW_KEY_LAST + 1];
    private boolean keys[]=new boolean[GLFW.GLFW_KEY_LAST];
    private Calculator cal;
    private float[][] graphData;
    
    public Window3d(int width, int height, String title){
        this.width = width;
        this.height = height;
        this.title = title;
    }
    
    public Window3d(int width, int height, String title, Calculator cal){
        this(width, height, title);
        this.cal = cal;
        
        graphData = new float[cal.getLayersNum()][5];
        for(int i=0;i<cal.getLayersNum();i++){
            float y = (float)(cal.getYBoundaryActual(i)[0]+cal.getYBoundaryActual(i)[1])/2.f;
            graphData[i][0] = y;
            graphData[i][1] = 0;
            graphData[i][2] = (float)(cal.getSliceActualXPos(i)-cal.getActualLength()/2);
            graphData[i][3] = (float)cal.getBaseLengthActual(i)/2.f;
            graphData[i][4] = (float)cal.getLayerThickness();
        }
    }

    public void run() {
        try {
            init();
            if(isUpdating()){
                loop();
                //threadLoop();
            }
            glfwDestroyWindow(window);
            keyCallback.free();
            fbCallback.free();
            cpCallback.free();
            sCallback.free();
            mbCallback.free();
        } finally {
            glfwTerminate();
            errorCallback.free();
        }
        
    }

    ArcBallCamera cam = new ArcBallCamera();

    private void init() {
        glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));
        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure our window
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_SAMPLES, 4);

        window = glfwCreateWindow(width, height, title, NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                    glfwSetWindowShouldClose(window, true);

                /*if (key == GLFW_KEY_ENTER && action == GLFW_PRESS) {
                    cam.center((float) Math.random() * 20.0f - 10.0f, 0.0f, (float) Math.random() * 20.0f - 10.0f);
                }*/
                if (action == GLFW_PRESS || action == GLFW_REPEAT)
                    keyDown[key] = true;
                else
                    keyDown[key] = false;
            }
        });
        glfwSetFramebufferSizeCallback(window, fbCallback = new GLFWFramebufferSizeCallback() {
            @Override
            public void invoke(long window, int w, int h) {
                if (w > 0 && h > 0) {
                    width = w;
                    height = h;
                }
            }
        });
        glfwSetCursorPosCallback(window, cpCallback = new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double xpos, double ypos) {
                x = (int) xpos- width / 2;
                y = height / 2 - (int) ypos;
            }
        });
        glfwSetMouseButtonCallback(window, mbCallback = new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                if (action == GLFW_PRESS) {
                    down = true;
                    mouseX = x;
                    mouseY = y;
                } else if (action == GLFW_RELEASE) {
                    down = false;
                }
            }
        });
        glfwSetScrollCallback(window, sCallback = new GLFWScrollCallback() {
            @Override
            public void invoke(long window, double xoffset, double yoffset) {
                
                if (yoffset < 0) {
                    zoom += 0.1f;
                    cam.zoom(zoom);
                } else if(yoffset > 0){
                    zoom -= 0.1f;
                    cam.zoom(zoom);
                }
                else{
                    cam.zoom(zoom);
                }
                
            }
        });

        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(window, (vidmode.width() - width) / 2, (vidmode.height() - height) / 2);

        IntBuffer framebufferSize = BufferUtils.createIntBuffer(2);
        nglfwGetFramebufferSize(window, memAddress(framebufferSize), memAddress(framebufferSize) + 4);
        width = framebufferSize.get(0);
        height = framebufferSize.get(1);

        glfwMakeContextCurrent(window);
        glfwSwapInterval(0);
        glfwShowWindow(window);
    }

    private void renderCube() {
        glBegin(GL_QUADS);
        //glColor3f(   0.0f,  0.0f,  0.2f );
        glVertex3f(  0.5f, -0.5f, -0.5f );
        glVertex3f(  0.5f,  0.5f, -0.5f );
        glVertex3f( -0.5f,  0.5f, -0.5f );
        glVertex3f( -0.5f, -0.5f, -0.5f );
        //glColor3f(   0.0f,  0.0f,  1.0f );
        glVertex3f(  0.5f, -0.5f,  0.5f );
        glVertex3f(  0.5f,  0.5f,  0.5f );
        glVertex3f( -0.5f,  0.5f,  0.5f );
        glVertex3f( -0.5f, -0.5f,  0.5f );
        //glColor3f(   1.0f,  0.0f,  0.0f );
        glVertex3f(  0.5f, -0.5f, -0.5f );
        glVertex3f(  0.5f,  0.5f, -0.5f );
        glVertex3f(  0.5f,  0.5f,  0.5f );
        glVertex3f(  0.5f, -0.5f,  0.5f );
        //glColor3f(   0.2f,  0.0f,  0.0f );
        glVertex3f( -0.5f, -0.5f,  0.5f );
        glVertex3f( -0.5f,  0.5f,  0.5f );
        glVertex3f( -0.5f,  0.5f, -0.5f );
        glVertex3f( -0.5f, -0.5f, -0.5f );
        //glColor3f(   0.0f,  1.0f,  0.0f );
        glVertex3f(  0.5f,  0.5f,  0.5f );
        glVertex3f(  0.5f,  0.5f, -0.5f );
        glVertex3f( -0.5f,  0.5f, -0.5f );
        glVertex3f( -0.5f,  0.5f,  0.5f );
        //glColor3f(   0.0f,  0.2f,  0.0f );
        glVertex3f(  0.5f, -0.5f, -0.5f );
        glVertex3f(  0.5f, -0.5f,  0.5f );
        glVertex3f( -0.5f, -0.5f,  0.5f );
        glVertex3f( -0.5f, -0.5f, -0.5f );
        glEnd();
    }
    
    private void drawSphere(double r, int lats, int longs) {
        int i, j;
        for(i = 0; i <= lats; i++) {
            double lat0 = Math.PI * (-0.5 + (double) (i - 1) / lats);
            double z0  = Math.sin(lat0);
            double zr0 =  Math.cos(lat0);

            double lat1 = Math.PI * (-0.5 + (double) i / lats);
            double z1 = Math.sin(lat1);
            double zr1 = Math.cos(lat1);

            glBegin(GL_QUAD_STRIP);
            glColor3f(1f, 0.0f, 0.0f);
            for(j = 0; j <= longs; j++) {
                double lng = 2 * Math.PI * (double) (j - 1) / longs;
                double x = Math.cos(lng);
                double y = Math.sin(lng);

                glNormal3d(x * zr0, y * zr0, z0);
                glVertex3d(x * zr0, y * zr0, z0);
                glNormal3d(x * zr1, y * zr1, z1);
                glVertex3d(x * zr1, y * zr1, z1);
            }
            glEnd();
        }
    }
    
    private void render(int colorLocation){
        int crossSectionType = cal.getxSectionType();
        switch(crossSectionType){
            case(Calculator.XSECTION_SQUARE):
                renderSquare(colorLocation);
                break;
            case(Calculator.XSECTION_CIRCLE):
                renderCircleCylinder(colorLocation);
                break;
            case(Calculator.XSECTION_SEMICIRCLE):
                renderSemiCircleCylinder(colorLocation);
                break;
            case(Calculator.XSECTION_EQUILIBRIUM_TRIANGLE):
                renderIsoscelesTriangle(colorLocation,(float)Math.sqrt(3));
                break;
            case(Calculator.XSECTION_RIGHTISOSCELES_TRIANGLE_HYPOTENUSE):
                renderIsoscelesTriangle(colorLocation,1.f);
                break;
            default:
                break;
        }
    }
    
    private void renderSquare(int colorLocation){
        for(int i=0;i<cal.getLayersNum();i++){
            glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
            glUniform3f(colorLocation, 0.5f, 0.7f, 0.8f);
            
            renderSquareSlice(graphData[i][0],graphData[i][1],
                    graphData[i][2],graphData[i][3],graphData[i][4]);
            
            glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
            glEnable(GL_POLYGON_OFFSET_LINE);
            glPolygonOffset(-1.f,-1.f);
            glUniform3f(colorLocation, 0.0f, 0.0f, 0.0f);
            renderSquareSlice(graphData[i][0],graphData[i][1],
                    graphData[i][2],graphData[i][3],graphData[i][4]);
            glDisable(GL_POLYGON_OFFSET_LINE);
        }
    }
    
    private void renderSquareSlice(float x, float y, float z, float r, float thickness){
        glBegin(GL11.GL_POLYGON);
        glVertex3f(x-r,y,z);
        glVertex3f(x+r,y,z);
        glVertex3f(x+r,y+2*r,z);
        glVertex3f(x-r,y+2*r,z);
        glEnd();
        
        glBegin(GL11.GL_POLYGON);
        glVertex3f(x-r,y,z+thickness);
        glVertex3f(x+r,y,z+thickness);
        glVertex3f(x+r,y+2*r,z+thickness);
        glVertex3f(x-r,y+2*r,z+thickness);
        glEnd();
        
        glBegin(GL_QUAD_STRIP);
        glVertex3f(x-r,y,z);
        glVertex3f(x-r,y,z+thickness);
        glVertex3f(x+r,y,z);
        glVertex3f(x+r,y,z+thickness);
        glVertex3f(x+r,y+2*r,z);
        glVertex3f(x+r,y+2*r,z+thickness);
        glVertex3f(x-r,y+2*r,z);
        glVertex3f(x-r,y+2*r,z+thickness);
        glVertex3f(x-r,y,z);
        glVertex3f(x-r,y,z+thickness);
        glEnd();
    }
    
    private void renderIsoscelesTriangle(int colorLocation,float heightRatio){
        for(int i=0;i<cal.getLayersNum();i++){
            glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
            glUniform3f(colorLocation, 0.5f, 0.7f, 0.8f);
            
            renderIsoscelesTriangleSlice(graphData[i][0],graphData[i][1],
                    graphData[i][2],graphData[i][3],graphData[i][4],heightRatio);
            
            glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
            glEnable(GL_POLYGON_OFFSET_LINE);
            glPolygonOffset(-1.f,-1.f);
            glUniform3f(colorLocation, 0.0f, 0.0f, 0.0f);
            renderIsoscelesTriangleSlice(graphData[i][0],graphData[i][1],
                    graphData[i][2],graphData[i][3],graphData[i][4],heightRatio);
            glDisable(GL_POLYGON_OFFSET_LINE);
        }
    }
    
    private void renderIsoscelesTriangleSlice(float x, float y, float z, float r, float thickness,float heightRatio){
        
        glBegin(GL11.GL_POLYGON);
        glVertex3f(x-r,y,z);
        glVertex3f(x+r,y,z);
        glVertex3f(x,y+r*heightRatio,z);
        glEnd();
        
        glBegin(GL11.GL_POLYGON);
        glVertex3f(x-r,y,z+thickness);
        glVertex3f(x+r,y,z+thickness);
        glVertex3f(x,y+r*heightRatio,z+thickness);
        glEnd();
        
        glBegin(GL_QUAD_STRIP);
        glVertex3f(x-r,y,z);
        glVertex3f(x-r,y,z+thickness);
        glVertex3f(x+r,y,z);
        glVertex3f(x+r,y,z+thickness);
        glVertex3f(x,y+r*heightRatio,z);
        glVertex3f(x,y+r*heightRatio,z+thickness);
        glVertex3f(x-r,y,z);
        glVertex3f(x-r,y,z+thickness);
        glEnd();
    }
    
    private void renderSemiCircleCylinder(int colorLocation){
        for(int i=0;i<cal.getLayersNum();i++){
            renderSemiCircleCylinderSlice(colorLocation,graphData[i][0],graphData[i][1],
                    graphData[i][2],graphData[i][3],graphData[i][4]);
        }
    }
    
    private void renderSemiCircleCylinderSlice(int colorLocation, float x, float y, float z, float r, float thickness){
        int sides=40;
        //polygons
        glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
        glUniform3f(colorLocation, 0.5f, 0.7f, 0.8f);
        
        glBegin(GL11.GL_POLYGON);
        for(int i=0; i<=sides; i++){
            double angle = Math.PI*i/(sides);
            double dx = r*Math.cos(angle);
            double dy = r*Math.sin(angle);
            glVertex3f(x+(float)dx,y+(float)dy,z);
        }
        glEnd();
        
        glBegin(GL11.GL_POLYGON);
        for(int i=0; i<=sides; i++){
            double angle = Math.PI*i/(sides);
            double dx = r*Math.cos(angle);
            double dy = r*Math.sin(angle);
            glVertex3f(x+(float)dx,y+(float)dy,z+thickness);
        }
        glEnd();
        
        glBegin(GL_QUAD_STRIP);
        for(int i=0; i<=sides; i++){
            double angle1 = Math.PI*i/(sides);
            double dx1 = r*Math.cos(angle1);
            double dy1 = r*Math.sin(angle1);
            
            double angle2 = Math.PI*i/(sides);
            double dx2 = r*Math.cos(angle2);
            double dy2 = r*Math.sin(angle2);
            glVertex3f(x+(float)dx1,y+(float)dy1,z);
            glVertex3f(x+(float)dx1,y+(float)dy1,z+thickness);
            glVertex3f(x+(float)dx2,y+(float)dy2,z);
            glVertex3f(x+(float)dx2,y+(float)dy2,z+thickness);
        }
        double dx1 = r;
        double dy1 = 0;

        double angle2 = Math.PI*1/(sides);
        double dx2 = r*Math.cos(angle2);
        double dy2 = r*Math.sin(angle2);
        glVertex3f(x+(float)dx1,y+(float)dy1,z);
        glVertex3f(x+(float)dx1,y+(float)dy1,z+thickness);
        glVertex3f(x+(float)dx2,y+(float)dy2,z);
        glVertex3f(x+(float)dx2,y+(float)dy2,z+thickness);
        
        glEnd();
        
        
        //Lines
        glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        glEnable(GL_POLYGON_OFFSET_LINE);
        glPolygonOffset(-1.f,-1.f);
        glUniform3f(colorLocation, 0.0f, 0.0f, 0.0f);
        
        glBegin(GL11.GL_POLYGON);
        for(int i=0; i<=sides; i++){
            double angle = Math.PI*i/sides;
            double dx = r*Math.cos(angle);
            double dy = r*Math.sin(angle);
            glVertex3f(x+(float)dx,y+(float)dy,z);
        }
        glEnd();
        
        glBegin(GL11.GL_POLYGON);
        for(int i=0; i<=sides; i++){
            double angle = Math.PI*i/sides;
            double dx = r*Math.cos(angle);
            double dy = r*Math.sin(angle);
            glVertex3f(x+(float)dx,y+(float)dy,z+thickness);
        }
        glEnd();
        
        glBegin(GL11.GL_POLYGON);
        glVertex3f(x-r,y,z);
        glVertex3f(x+r,y,z);
        glVertex3f(x+r,y,z+thickness);
        glVertex3f(x-r,y,z+thickness);
        glEnd();
        
        glDisable(GL_POLYGON_OFFSET_LINE);
    }
    
    private void renderCircleCylinder(int colorLocation/*, float x, float y, float z, float initialR, float step,float length*/){
        for(int i=0;i<cal.getLayersNum();i++){
            renderCircleCylinderSlice(colorLocation,graphData[i][0],graphData[i][1],
                    graphData[i][2],graphData[i][3],graphData[i][4]);
        }
    }
    
    private void renderCircleCylinderSlice(int colorLocation, float x, float y, float z, float r, float thickness){
        int sides=80;
        //polygons
        glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
        glUniform3f(colorLocation, 0.5f, 0.7f, 0.8f);
        
        glBegin(GL11.GL_POLYGON);
        for(int i=0; i<=sides; i++){
            double angle = Math.PI*2*i/sides;
            double dx = r*Math.cos(angle);
            double dy = r*Math.sin(angle);
            glVertex3f(x+(float)dx,y+(float)dy,z);
        }
        glEnd();
        
        glBegin(GL11.GL_POLYGON);
        for(int i=0; i<=sides; i++){
            double angle = Math.PI*2*i/sides;
            double dx = r*Math.cos(angle);
            double dy = r*Math.sin(angle);
            glVertex3f(x+(float)dx,y+(float)dy,z+thickness);
        }
        glEnd();
        
        glBegin(GL_QUAD_STRIP);
        for(int i=0; i<=sides; i++){
            double angle1 = Math.PI*2*i/sides;
            double dx1 = r*Math.cos(angle1);
            double dy1 = r*Math.sin(angle1);
            
            double angle2 = Math.PI*2*i/sides;
            double dx2 = r*Math.cos(angle2);
            double dy2 = r*Math.sin(angle2);
            glVertex3f(x+(float)dx1,y+(float)dy1,z);
            glVertex3f(x+(float)dx1,y+(float)dy1,z+thickness);
            glVertex3f(x+(float)dx2,y+(float)dy2,z);
            glVertex3f(x+(float)dx2,y+(float)dy2,z+thickness);
        }
        glEnd();
        
        
        //Lines
        glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        glEnable(GL_POLYGON_OFFSET_LINE);
        glPolygonOffset(-1.f,-1.f);
        glUniform3f(colorLocation, 0.0f, 0.0f, 0.0f);
        
        glBegin(GL11.GL_POLYGON);
        for(int i=0; i<sides; i++){
            double angle = Math.PI*2*i/sides;
            double dx = r*Math.cos(angle);
            double dy = r*Math.sin(angle);
            glVertex3f(x+(float)dx,y+(float)dy,z);
        }
        glEnd();
        
        glBegin(GL11.GL_POLYGON);
        for(int i=0; i<sides; i++){
            double angle = Math.PI*2*i/sides;
            double dx = r*Math.cos(angle);
            double dy = r*Math.sin(angle);
            glVertex3f(x+(float)dx,y+(float)dy,z+thickness);
        }
        glEnd();
        glDisable(GL_POLYGON_OFFSET_LINE);
    }

    private void renderGrid() {
        glBegin(GL_LINES);
        glColor3f(0.5f, 0.5f, 0.5f);
        for (int i = -30; i <= 30; i++) {
            glVertex3f(-30.0f, 0.0f, i);
            glVertex3f(30.0f, 0.0f, i);
            glVertex3f(i, 0.0f, -30.0f);
            glVertex3f(i, 0.0f, 30.0f);
        }
        glEnd();
        
        // Main axes
        glLineWidth(5f);
        glBegin(GL_LINES);
        glColor3f(0.5f, 0.2f, 0.2f);
        glVertex3f(-30f, 0, 0);
        glVertex3f(30f, 0, 0);
        glColor3f(0.2f, 0.5f, 0.2f);
        glVertex3f(0, 0, -30f);
        glVertex3f(0, 0, 30f);
        glEnd();
        glLineWidth(1.0f);
    }
    
    public boolean isKeyDown(int keycode){
        return GLFW.glfwGetKey(window,keycode) == 1;
    }
    
    public boolean isKeyPressed(int keycode){
        return isKeyDown(keycode) && !keys[keycode];
    }
    
    public boolean isKeyReleased(int keycode){
        return !isKeyDown(keycode) && keys[keycode];
    }
    
    public double getTime(){
        return (double)System.nanoTime()/(double)1000000000;
    }
    
    public boolean isUpdating(){
         double nextTime = getTime();
         double passedTime = nextTime-time;
         processedTime +=passedTime;
         time = nextTime;
         
         while(processedTime > 1.0/fps_cap){
             processedTime -= 1.0/fps_cap;
             return true;
         }
         
         return false;
    }
    
    void threadLoop(){
        new Thread(new Runnable() {
            public void run() {
                loop();
            }
        }).start();

        while (!glfwWindowShouldClose(window)) {
            glfwWaitEvents();
        }
    }
    
    private void loop() {
        GL.createCapabilities();
        
        // Set the clear color
        glClearColor(0.95f, 0.95f, 0.95f, 1.0f);
        // Enable depth testing
        glEnable(GL_DEPTH_TEST);
        glLineWidth(1.4f);
        
        
        int program = glCreateProgram();
        
        int fs = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fs,
                "uniform vec3 color;" +
                "void main(void) {" + 
                "  gl_FragColor = vec4(color, 1.0);" + 
                "}");
        glCompileShader(fs);
        glAttachShader(program, fs);
        glLinkProgram(program);
        glUseProgram(program);

        // Obtain uniform location
        //int matLocation = glGetUniformLocation(program, "viewProjMatrix");
        int colorLocation = glGetUniformLocation(program, "color");
        

        // Remember the current time.
        long lastTime = System.nanoTime();

        Matrix4f mat = new Matrix4f();
        // FloatBuffer for transferring matrices to OpenGL
        FloatBuffer fb = BufferUtils.createFloatBuffer(16);

        cam.setAlpha((float) Math.toRadians(-20));
        cam.setBeta((float) Math.toRadians(20));

        while (!glfwWindowShouldClose(window)) {
            /* Set input values for the camera */
            if (down) {
                cam.setAlpha(cam.getAlpha() + Math.toRadians((x - mouseX) * 0.1f));
                cam.setBeta(cam.getBeta() + Math.toRadians((mouseY - y) * 0.1f));
                mouseX = x;
                mouseY = y;
            }
            if (keyDown[GLFW_KEY_S]&&zoom<40){
                zoom+=0.1f;
                cam.zoom(zoom);
            }
            else if (keyDown[GLFW_KEY_W]&&zoom>-5){
                zoom-=0.1f;
                cam.zoom(zoom);
            }
            else if (keyDown[GLFW_KEY_X]&&(keyDown[GLFW_KEY_UP]||keyDown[GLFW_KEY_EQUAL])){
                lookx+=0.05f;
            }
            else if (keyDown[GLFW_KEY_X]&&(keyDown[GLFW_KEY_DOWN]||keyDown[GLFW_KEY_MINUS])){
                lookx-=0.05f;
            }
            else if (keyDown[GLFW_KEY_Y]&&(keyDown[GLFW_KEY_UP]||keyDown[GLFW_KEY_EQUAL])){
                looky+=0.05f;
            }
            else if (keyDown[GLFW_KEY_Y]&&(keyDown[GLFW_KEY_DOWN]||keyDown[GLFW_KEY_MINUS])){
                looky-=0.05f;
            }
            else if (keyDown[GLFW_KEY_Z]&&(keyDown[GLFW_KEY_UP]||keyDown[GLFW_KEY_EQUAL])){
                lookz+=0.05f;
            }
            else if (keyDown[GLFW_KEY_Z]&&(keyDown[GLFW_KEY_DOWN]||keyDown[GLFW_KEY_MINUS])){
                lookz-=0.05f;
            }
            else if (keyDown[GLFW_KEY_ENTER]){
                lookx = 0;
                looky = 0;
                lookz = 0;
                zoom = 20;
                cam.setAlpha((float) Math.toRadians(-20));
                cam.setBeta((float) Math.toRadians(20));
            }
            else{
                cam.zoom(zoom);
            }

            /* Compute delta time */
            long thisTime = System.nanoTime();
            float diff = (float) ((thisTime - lastTime) / 1E9);
            lastTime = thisTime;
            /* And let the camera make its update */
            cam.update(diff);

            glViewport(0, 0, width, height);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            mat.setPerspective(2*(float) Math.atan((ViewSettings.screenHeight * height / ViewSettings.screenHeightPx) / ViewSettings.distanceToScreen),
                               (float) width / height, 0.01f, 100.0f).
                    lookAt(lookx, looky, lookz+10.0f,
                            lookx, looky, lookz,
                            0.0f, 1.0f, 0.0f)
               .get(fb);
            
            Quaternionf q = new Quaternionf();
            
            glMatrixMode(GL_PROJECTION);
            glLoadMatrixf(fb);

            /*
             * Obtain the camera's view matrix and render grid.
             */
            cam.viewMatrix(mat.identity()).get(fb);
            glMatrixMode(GL_MODELVIEW);
            glLoadMatrixf(fb);
            glUniform3f(colorLocation, 0.3f, 0.3f, 0.3f);
            renderGrid();

            /* Translate to cube position and render cube */
            mat.rotate(q.rotateY((float) Math.toRadians(180)).normalize());
            mat.translate(cam.centerMover.target).get(fb);
            
            glLoadMatrixf(fb);
             
            /*glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
            glUniform3f(colorLocation, 0.5f, 0.7f, 0.8f);
            renderCircleCylinder(0,0,0,1,1,20);
            
            glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
            glEnable(GL_POLYGON_OFFSET_LINE);
            glPolygonOffset(-1.f,-1.f);
            glUniform3f(colorLocation, 0.0f, 0.0f, 0.0f);
            //render();
            renderCircleCylinder(0,0,0,1,1,20);
            glDisable(GL_POLYGON_OFFSET_LINE);*/
            render(colorLocation);

            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }

}