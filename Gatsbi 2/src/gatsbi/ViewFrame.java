package gatsbi;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ViewFrame.java created by thigley on Apr 3, 2014 at 1:54:54 PM
 */
public class ViewFrame extends javax.swing.JFrame {

    Controller c;

    public ViewFrame() {  // this constructor creates the form and displays it
        initComponents();
        setSize(630, 473);
        setTitle("Gabriel Andrew and Tyler's System of Basic Intelligence");
        System.out.println(System.getProperty("os.name"));
        if(System.getProperty("os.name").contains("Windows")){
            setSize(630, 487);
        }
        setVisible(true);
        inputTF.requestFocus();
    }

    public ViewFrame(Controller c) {
        this();
        this.c = c;
    }

    /**
     * You can't modify the following code; it is regenerated by the Form
     * Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        inputTF = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        outputTA = new javax.swing.JTextArea();
        resetButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(null);

        inputTF.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        inputTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputTFActionPerformed(evt);
            }
        });
        getContentPane().add(inputTF);
        inputTF.setBounds(0, 420, 630, 30);

        outputTA.setEditable(false);
        outputTA.setColumns(20);
        outputTA.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        outputTA.setLineWrap(true);
        outputTA.setRows(5);
        outputTA.setWrapStyleWord(true);
        jScrollPane1.setViewportView(outputTA);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(20, 330, 590, 80);

        resetButton.setText("Reset");
        resetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetButtonActionPerformed(evt);
            }
        });
        getContentPane().add(resetButton);
        resetButton.setBounds(0, 0, 78, 29);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gatsbi/gatsby.jpg"))); // NOI18N
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, 0, 630, 420);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void inputTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputTFActionPerformed
        final String input = inputTF.getText();
        outputTA.append(c.getFirstName() + ": " + input + "\n");
        inputTF.setText("");

        new Timer().schedule(
                new TimerTask() {
            @Override
            public void run() {
                c.parse(input);
            }
        }, 1000);
    }//GEN-LAST:event_inputTFActionPerformed

    private void resetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetButtonActionPerformed
        c.reset();
        outputTA.setText("");
        inputTF.setText("");
        inputTF.requestFocus();
    }//GEN-LAST:event_resetButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ViewFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField inputTF;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea outputTA;
    private javax.swing.JButton resetButton;
    // End of variables declaration//GEN-END:variables

    void say(String output) {
  

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            System.out.println("nope.");        }
 

        outputTA.append("GATSBI: " + output + "\n");
        outputTA.setCaretPosition(outputTA.getDocument().getLength());
    }
}
