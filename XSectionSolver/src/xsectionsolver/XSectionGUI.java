
package xsectionsolver;

import org.mariuszgromada.math.mxparser.*;
import org.mariuszgromada.math.mxparser.mathcollection.*;
import org.mariuszgromada.math.mxparser.parsertokens.*;
import org.mariuszgromada.math.mxparser.regressiontesting.*;
import org.mariuszgromada.math.mxparser.syntaxchecker.*;

public class XSectionGUI extends javax.swing.JFrame {
    
    private String welcomeGuide;
    private Window3d Model3d;

    public XSectionGUI() {
        initComponents();
        initiate();
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
        txtF = new javax.swing.JTextField();
        txtG = new javax.swing.JTextField();
        txtLayersNum = new javax.swing.JTextField();
        txtUpperLim = new javax.swing.JTextField();
        txtLowerLim = new javax.swing.JTextField();
        txtActualLength = new javax.swing.JTextField();
        cbBoxXSectionType = new javax.swing.JComboBox<>();
        cbBoxRSumType = new javax.swing.JComboBox<>();
        btn2D = new javax.swing.JButton();
        lblOutput = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtOutput = new javax.swing.JTextArea();
        btnReset = new javax.swing.JButton();
        btn3D = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();

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

        txtF.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtF.setMaximumSize(new java.awt.Dimension(150, 25));
        txtF.setMinimumSize(new java.awt.Dimension(150, 25));
        txtF.setPreferredSize(new java.awt.Dimension(150, 25));

        txtG.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtG.setMaximumSize(new java.awt.Dimension(150, 25));
        txtG.setMinimumSize(new java.awt.Dimension(150, 25));
        txtG.setPreferredSize(new java.awt.Dimension(150, 25));

        txtLayersNum.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtLayersNum.setText("" + Calculator.MIN_LAYERS_NUM);
        txtLayersNum.setMaximumSize(new java.awt.Dimension(150, 25));
        txtLayersNum.setMinimumSize(new java.awt.Dimension(150, 25));
        txtLayersNum.setPreferredSize(new java.awt.Dimension(150, 25));

        txtUpperLim.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtUpperLim.setMaximumSize(new java.awt.Dimension(150, 25));
        txtUpperLim.setMinimumSize(new java.awt.Dimension(150, 25));
        txtUpperLim.setPreferredSize(new java.awt.Dimension(150, 25));

        txtLowerLim.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtLowerLim.setMaximumSize(new java.awt.Dimension(150, 25));
        txtLowerLim.setMinimumSize(new java.awt.Dimension(150, 25));
        txtLowerLim.setPreferredSize(new java.awt.Dimension(150, 25));

        txtActualLength.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
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

        btn2D.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btn2D.setText("Calculate & Draw 2D!");
        btn2D.setMaximumSize(new java.awt.Dimension(355, 50));
        btn2D.setMinimumSize(new java.awt.Dimension(355, 50));
        btn2D.setPreferredSize(new java.awt.Dimension(355, 50));
        btn2D.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn2DActionPerformed(evt);
            }
        });

        lblOutput.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblOutput.setText("Output:");
        lblOutput.setMaximumSize(new java.awt.Dimension(100, 30));
        lblOutput.setMinimumSize(new java.awt.Dimension(100, 30));
        lblOutput.setPreferredSize(new java.awt.Dimension(100, 30));

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

        btn3D.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btn3D.setText("Calculate & Draw 3D!");
        btn3D.setMaximumSize(new java.awt.Dimension(355, 50));
        btn3D.setMinimumSize(new java.awt.Dimension(355, 50));
        btn3D.setPreferredSize(new java.awt.Dimension(355, 50));
        btn3D.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn3DActionPerformed(evt);
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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblInput, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(lblG, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblF, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblXSectionType, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbBoxXSectionType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblActualLength, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtActualLength, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(btn2D, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(lblRSumType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(cbBoxRSumType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblLayersNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtLayersNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblUpperLim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtUpperLim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblLowerLim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtLowerLim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btn3D, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblOutput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(31, Short.MAX_VALUE))
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
                    .addComponent(lblInput, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblOutput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblXSectionType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblLayersNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtLayersNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblUpperLim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtUpperLim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblLowerLim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtLowerLim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblActualLength, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtActualLength, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(cbBoxXSectionType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblRSumType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbBoxRSumType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btn2D, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn3D, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed

        initiate();
        
    }//GEN-LAST:event_btnResetActionPerformed

    private void btn3DActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn3DActionPerformed

        Model3d = new Window3d(800,600,"3d model");
        Model3d.run();

    }//GEN-LAST:event_btn3DActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed

        System.exit(0);

    }//GEN-LAST:event_btnExitActionPerformed

    private void btn2DActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn2DActionPerformed

        new Window2d(800,600,"2d model").run();

    }//GEN-LAST:event_btn2DActionPerformed

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
    
    public void run(){
        System.out.println("run");
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
                System.out.println("run2");
                new XSectionGUI().setVisible(true);
            }
        });
    }

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn2D;
    private javax.swing.JButton btn3D;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnReset;
    private javax.swing.JComboBox<String> cbBoxRSumType;
    private javax.swing.JComboBox<String> cbBoxXSectionType;
    private javax.swing.JScrollPane jScrollPane1;
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