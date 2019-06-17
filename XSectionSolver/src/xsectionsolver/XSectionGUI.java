
package xsectionsolver;

import javax.swing.JOptionPane;
import org.mariuszgromada.math.mxparser.*;
import org.mariuszgromada.math.mxparser.mathcollection.*;
import org.mariuszgromada.math.mxparser.parsertokens.*;
import org.mariuszgromada.math.mxparser.regressiontesting.*;
import org.mariuszgromada.math.mxparser.syntaxchecker.*;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;

public class XSectionGUI extends javax.swing.JFrame {
    
    private String welcomeGuide;
    private Calculator c;
    private Thread t3d;
    private Thread t2d;

    public XSectionGUI() {
        initComponents();
        initiate();
    }
    
    private boolean constructCalculator(){
        
        if(txtF.getText().equals("")||txtG.getText().equals("")||txtLayersNum.getText().equals("")||
                txtLowerLim.getText().equals("")||txtUpperLim.getText().equals("")||
                txtActualLength.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Error: empty Input Exist!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        
        int LayerNum;
        double actualLength;
        Argument lowerLimit = new Argument("x="+txtLowerLim.getText());
        Argument upperLimit = new Argument("x="+txtUpperLim.getText());
        try{
            LayerNum = Integer.parseInt(txtLayersNum.getText());
        }
        catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Error: invalid input for number of layers!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        try{
            actualLength = Double.parseDouble(txtActualLength.getText());
        }
        catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Error: invalid intput for actual length!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if(Double.isNaN(lowerLimit.getArgumentValue())){
            JOptionPane.showMessageDialog(null, "Error: invalid input for lower limit!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if(Double.isNaN(upperLimit.getArgumentValue())){
            JOptionPane.showMessageDialog(null, "Error: invalid input for upper limit!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if(lowerLimit.getArgumentValue()>upperLimit.getArgumentValue()){
            Object[] options = {"Yes", "No"};
                //the option buttons for the confirm message will be "Yes", and "Cancel"
                int switchComfirm = JOptionPane.showOptionDialog(null,
                        "The lower limit is greater than the upper limit, do you want to switch the limits?", 
                        "Switch Limits??",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            if(switchComfirm == JOptionPane.YES_OPTION){
                String limit = txtLowerLim.getText();
                txtLowerLim.setText(txtUpperLim.getText());
                txtUpperLim.setText(limit);
            }
            else{
                return false;
            }
        }
        
        c = new Calculator(functionInputAdjust(txtF.getText()), functionInputAdjust(txtG.getText()), cbBoxXSectionType.getSelectedIndex(),
                           LayerNum, txtLowerLim.getText(), txtUpperLim.getText(),
                           actualLength, cbBoxRSumType.getSelectedIndex());
        
        if(Double.isNaN(c.getFunction1().calculate(lowerLimit))){
            JOptionPane.showMessageDialog(null, "Error: invalid input for function 1!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if(Double.isNaN(c.getFunction2().calculate(lowerLimit))){
            JOptionPane.showMessageDialog(null, "Error: invalid input for function 2!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitle = new javax.swing.JLabel();
        lblAuthors = new javax.swing.JLabel();
        lblInput = new javax.swing.JLabel();
        lblF = new javax.swing.JLabel();
        lblG = new javax.swing.JLabel();
        lblXSectionType = new javax.swing.JLabel();
        lblLayersNum = new javax.swing.JLabel();
        lblUpperLim = new javax.swing.JLabel();
        lblLowerLim = new javax.swing.JLabel();
        lblActualLength = new javax.swing.JLabel();
        lblRSumType = new javax.swing.JLabel();
        txtLayersNum = new javax.swing.JTextField();
        txtUpperLim = new javax.swing.JTextField();
        txtLowerLim = new javax.swing.JTextField();
        txtActualLength = new javax.swing.JTextField();
        cbBoxXSectionType = new javax.swing.JComboBox<>();
        cbBoxRSumType = new javax.swing.JComboBox<>();
        lblOutput = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtOutput = new javax.swing.JTextArea();
        btnReset = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        btnCalc = new javax.swing.JButton();
        btn3d = new javax.swing.JButton();
        btn2d = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtF = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtG = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblTitle.setFont(new java.awt.Font("Yu Mincho", 1, 30)); // NOI18N
        lblTitle.setText("Volume by Cross Section Solver");
        lblTitle.setMaximumSize(new java.awt.Dimension(500, 50));
        lblTitle.setMinimumSize(new java.awt.Dimension(500, 50));
        lblTitle.setPreferredSize(new java.awt.Dimension(500, 50));

        lblAuthors.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
        lblAuthors.setText("by Malcolm Wang & Peter Zhu");
        lblAuthors.setMaximumSize(new java.awt.Dimension(250, 30));
        lblAuthors.setMinimumSize(new java.awt.Dimension(250, 30));
        lblAuthors.setName(""); // NOI18N
        lblAuthors.setPreferredSize(new java.awt.Dimension(250, 30));

        lblInput.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblInput.setText("Input:");
        lblInput.setMaximumSize(new java.awt.Dimension(100, 30));
        lblInput.setMinimumSize(new java.awt.Dimension(100, 30));
        lblInput.setPreferredSize(new java.awt.Dimension(100, 30));

        lblF.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblF.setText("Function 1: ");
        lblF.setMaximumSize(new java.awt.Dimension(200, 25));
        lblF.setMinimumSize(new java.awt.Dimension(200, 25));
        lblF.setPreferredSize(new java.awt.Dimension(200, 25));

        lblG.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblG.setText("Function 2: ");
        lblG.setMaximumSize(new java.awt.Dimension(200, 25));
        lblG.setMinimumSize(new java.awt.Dimension(200, 25));
        lblG.setPreferredSize(new java.awt.Dimension(200, 25));

        lblXSectionType.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblXSectionType.setText("Cross Section Type:  ");
        lblXSectionType.setMaximumSize(new java.awt.Dimension(200, 25));
        lblXSectionType.setMinimumSize(new java.awt.Dimension(200, 25));
        lblXSectionType.setPreferredSize(new java.awt.Dimension(200, 25));

        lblLayersNum.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblLayersNum.setText("Number of Layers: ");
        lblLayersNum.setMaximumSize(new java.awt.Dimension(200, 25));
        lblLayersNum.setMinimumSize(new java.awt.Dimension(200, 25));
        lblLayersNum.setPreferredSize(new java.awt.Dimension(200, 25));

        lblUpperLim.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblUpperLim.setText("Upper Limit: ");
        lblUpperLim.setMaximumSize(new java.awt.Dimension(200, 25));
        lblUpperLim.setMinimumSize(new java.awt.Dimension(200, 25));
        lblUpperLim.setPreferredSize(new java.awt.Dimension(200, 25));

        lblLowerLim.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblLowerLim.setText("Lower Limit:");
        lblLowerLim.setMaximumSize(new java.awt.Dimension(200, 25));
        lblLowerLim.setMinimumSize(new java.awt.Dimension(200, 25));
        lblLowerLim.setPreferredSize(new java.awt.Dimension(200, 25));

        lblActualLength.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblActualLength.setText("Actual Length (in cm): ");
        lblActualLength.setMaximumSize(new java.awt.Dimension(200, 25));
        lblActualLength.setMinimumSize(new java.awt.Dimension(200, 25));
        lblActualLength.setPreferredSize(new java.awt.Dimension(200, 25));

        lblRSumType.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblRSumType.setText("Riemann Sum Type: ");
        lblRSumType.setMaximumSize(new java.awt.Dimension(200, 25));
        lblRSumType.setMinimumSize(new java.awt.Dimension(200, 25));
        lblRSumType.setPreferredSize(new java.awt.Dimension(200, 25));

        txtLayersNum.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        txtLayersNum.setText("" + Calculator.MIN_LAYERS_NUM);
        txtLayersNum.setMaximumSize(new java.awt.Dimension(150, 25));
        txtLayersNum.setMinimumSize(new java.awt.Dimension(150, 25));
        txtLayersNum.setPreferredSize(new java.awt.Dimension(150, 25));

        txtUpperLim.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        txtUpperLim.setMaximumSize(new java.awt.Dimension(150, 25));
        txtUpperLim.setMinimumSize(new java.awt.Dimension(150, 25));
        txtUpperLim.setPreferredSize(new java.awt.Dimension(150, 25));

        txtLowerLim.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        txtLowerLim.setMaximumSize(new java.awt.Dimension(150, 25));
        txtLowerLim.setMinimumSize(new java.awt.Dimension(150, 25));
        txtLowerLim.setPreferredSize(new java.awt.Dimension(150, 25));

        txtActualLength.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        txtActualLength.setText("" + Calculator.MIN_ACTUAL_LENGTH);
        txtActualLength.setMaximumSize(new java.awt.Dimension(150, 25));
        txtActualLength.setMinimumSize(new java.awt.Dimension(150, 25));
        txtActualLength.setPreferredSize(new java.awt.Dimension(150, 25));

        cbBoxXSectionType.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cbBoxXSectionType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Square", "Circle", "Semi-Circle", "Equilibrium Triangle", "Right-Isosceles Triangle (Hypotenuse)" }));
        cbBoxXSectionType.setSelectedIndex(2);
        cbBoxXSectionType.setMaximumSize(new java.awt.Dimension(150, 25));
        cbBoxXSectionType.setMinimumSize(new java.awt.Dimension(150, 25));
        cbBoxXSectionType.setPreferredSize(new java.awt.Dimension(150, 25));

        cbBoxRSumType.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cbBoxRSumType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Left", "Right", "Middle" }));
        cbBoxRSumType.setMaximumSize(new java.awt.Dimension(150, 25));
        cbBoxRSumType.setMinimumSize(new java.awt.Dimension(150, 25));
        cbBoxRSumType.setPreferredSize(new java.awt.Dimension(150, 25));

        lblOutput.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblOutput.setText("Output:");
        lblOutput.setMaximumSize(new java.awt.Dimension(100, 30));
        lblOutput.setMinimumSize(new java.awt.Dimension(100, 30));
        lblOutput.setPreferredSize(new java.awt.Dimension(100, 30));

        txtOutput.setEditable(false);
        txtOutput.setColumns(20);
        txtOutput.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtOutput.setRows(5);
        jScrollPane1.setViewportView(txtOutput);

        btnReset.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        btnReset.setText("Reset");
        btnReset.setMaximumSize(new java.awt.Dimension(170, 120));
        btnReset.setMinimumSize(new java.awt.Dimension(170, 120));
        btnReset.setPreferredSize(new java.awt.Dimension(170, 120));
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        btnExit.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        btnExit.setText("Exit");
        btnExit.setMaximumSize(new java.awt.Dimension(170, 120));
        btnExit.setMinimumSize(new java.awt.Dimension(170, 120));
        btnExit.setPreferredSize(new java.awt.Dimension(170, 120));
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        btnCalc.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnCalc.setText("Calculate!");
        btnCalc.setMaximumSize(new java.awt.Dimension(360, 40));
        btnCalc.setMinimumSize(new java.awt.Dimension(360, 40));
        btnCalc.setPreferredSize(new java.awt.Dimension(360, 40));
        btnCalc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalcActionPerformed(evt);
            }
        });

        btn3d.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btn3d.setText("Draw 3D!");
        btn3d.setMaximumSize(new java.awt.Dimension(360, 40));
        btn3d.setMinimumSize(new java.awt.Dimension(360, 40));
        btn3d.setPreferredSize(new java.awt.Dimension(360, 40));
        btn3d.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn3dMouseClicked(evt);
            }
        });
        btn3d.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn3dActionPerformed(evt);
            }
        });

        btn2d.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btn2d.setText("Draw 2D!");
        btn2d.setMaximumSize(new java.awt.Dimension(360, 40));
        btn2d.setMinimumSize(new java.awt.Dimension(360, 40));
        btn2d.setPreferredSize(new java.awt.Dimension(360, 40));
        btn2d.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn2dMouseClicked(evt);
            }
        });
        btn2d.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn2dActionPerformed(evt);
            }
        });

        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        txtF.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jScrollPane2.setViewportView(txtF);

        jScrollPane3.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        txtG.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jScrollPane3.setViewportView(txtG);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblAuthors, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblInput, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblXSectionType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbBoxXSectionType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblActualLength, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtActualLength, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblRSumType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbBoxRSumType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblUpperLim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtUpperLim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblLowerLim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtLowerLim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblLayersNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtLayersNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btn3d, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnCalc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn2d, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(lblF, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jScrollPane2))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(lblG, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblOutput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnExit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAuthors, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblInput, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblOutput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane1))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(48, 48, 48)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE)
                                    .addComponent(lblF, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE)
                                    .addComponent(lblG, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cbBoxXSectionType, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                                    .addComponent(lblXSectionType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtLayersNum, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                                    .addComponent(lblLayersNum, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtUpperLim, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                                    .addComponent(lblUpperLim, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtLowerLim, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                                    .addComponent(lblLowerLim, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblActualLength, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                                    .addComponent(txtActualLength, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cbBoxRSumType, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                                    .addComponent(lblRSumType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(18, 18, 18)
                        .addComponent(btnCalc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn2d, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn3d, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed

        initiate();
        
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        
        System.exit(0);

    }//GEN-LAST:event_btnExitActionPerformed

    private void btnCalcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalcActionPerformed
        
        if(constructCalculator()){
        
            txtOutput.setText(c.getDataString());
            txtOutput.setCaretPosition(0);
        }
    }//GEN-LAST:event_btnCalcActionPerformed
    
    private void btn2dActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn2dActionPerformed

        if(constructCalculator()){
            Window2d model2d = new Window2d(800, 600, "2D Model - F: " + txtF.getText() + " G: " + txtG.getText(), c,btn2d);
            t2d = new Thread(model2d);

            t2d.start();
        }
    }//GEN-LAST:event_btn2dActionPerformed

    private void btn3dActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn3dActionPerformed
        
        if(constructCalculator()){
            Window3d model3d = new Window3d(800, 600, "3D Model - F: " + txtF.getText() + " G: " + txtG.getText(), c,btn3d);
            t3d = new Thread(model3d);

            t3d.start();
        }

    }//GEN-LAST:event_btn3dActionPerformed

    private void btn2dMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn2dMouseClicked

        btn2d.setEnabled(false);
        
    }//GEN-LAST:event_btn2dMouseClicked

    private void btn3dMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn3dMouseClicked

        btn3d.setEnabled(false);

    }//GEN-LAST:event_btn3dMouseClicked

    private void initiate() {

        welcomeGuide = "Welcome to Volume by Cross Section Solver!"
                     + "\nTo use this solver, simply enter and select information."
                     + "\nPlease check your brackets when entering functions."
                     + "\nEnjoy your exploration in Calculus!";
        txtOutput.setText(welcomeGuide);
        txtF.setText("");
        txtG.setText("");
        txtLayersNum.setText("" + Calculator.MIN_LAYERS_NUM);
        txtLowerLim.setText("");
        txtUpperLim.setText("");
        txtActualLength.setText("" + Calculator.MIN_ACTUAL_LENGTH);
        cbBoxXSectionType.setSelectedIndex(2);
        cbBoxRSumType.setSelectedIndex(0);
        
        
    }
    
    public String functionInputAdjust(String function){
        String f;
        f = function.replace(" ", "");
        if(f.contains("(")){
            for(int i=1;i<f.length()-1;i++){
                if(f.charAt(i)==')'&&f.charAt(i+1)=='('){
                    f = f.subSequence(0, i+1)+"*"+f.subSequence(i+1, f.length());
                    i++;
                }
                else if(f.charAt(i+1)=='x' && f.charAt(i)>=48 && f.charAt(i)<=57){
                    f = f.subSequence(0, i+1)+"*"+f.subSequence(i+1, f.length());
                    i++;
                }
            }
        }

        return f;
    }
    
    public void run(){
        //System.out.println("run");
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(XSectionGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(XSectionGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(XSectionGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(XSectionGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //this.setVisible(true);
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //System.out.println("run2");
                new XSectionGUI().setVisible(true);
            }
        });
    }

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn2d;
    private javax.swing.JButton btn3d;
    private javax.swing.JButton btnCalc;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnReset;
    private javax.swing.JComboBox<String> cbBoxRSumType;
    private javax.swing.JComboBox<String> cbBoxXSectionType;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblActualLength;
    private javax.swing.JLabel lblAuthors;
    private javax.swing.JLabel lblF;
    private javax.swing.JLabel lblG;
    private javax.swing.JLabel lblInput;
    private javax.swing.JLabel lblLayersNum;
    private javax.swing.JLabel lblLowerLim;
    private javax.swing.JLabel lblOutput;
    private javax.swing.JLabel lblRSumType;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblUpperLim;
    private javax.swing.JLabel lblXSectionType;
    private javax.swing.JTextField txtActualLength;
    private javax.swing.JTextField txtF;
    private javax.swing.JTextField txtG;
    private javax.swing.JTextField txtLayersNum;
    private javax.swing.JTextField txtLowerLim;
    private javax.swing.JTextArea txtOutput;
    private javax.swing.JTextField txtUpperLim;
    // End of variables declaration//GEN-END:variables

}