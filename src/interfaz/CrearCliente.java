/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

/**
 *
 * @author max
 */
public class CrearCliente extends javax.swing.JFrame {

    /**
     * Creates new form CrearCliente
     */
    public CrearCliente() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelMain = new javax.swing.JPanel();
        labelHaber = new javax.swing.JLabel();
        textNombre = new javax.swing.JTextField();
        textDeber = new javax.swing.JTextField();
        labelDeber = new javax.swing.JLabel();
        textHaber = new javax.swing.JTextField();
        labelNombre = new javax.swing.JLabel();
        textApellido = new javax.swing.JTextField();
        labelApellido = new javax.swing.JLabel();
        buttonCancelar = new javax.swing.JButton();
        buttonConfirmar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        panelMain.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        labelHaber.setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N
        labelHaber.setText("Haber");

        textNombre.setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N

        textDeber.setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N
        textDeber.setText("0");

        labelDeber.setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N
        labelDeber.setText("Deber");

        textHaber.setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N
        textHaber.setText("0");

        labelNombre.setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N
        labelNombre.setText("Nombre*");

        textApellido.setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N

        labelApellido.setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N
        labelApellido.setText("Apellido*");

        buttonCancelar.setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N
        buttonCancelar.setText("Cancelar");

        buttonConfirmar.setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N
        buttonConfirmar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/save.png"))); // NOI18N
        buttonConfirmar.setText("Confirmar");

        jLabel1.setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N
        jLabel1.setText("*Campos Obligatorios");

        javax.swing.GroupLayout panelMainLayout = new javax.swing.GroupLayout(panelMain);
        panelMain.setLayout(panelMainLayout);
        panelMainLayout.setHorizontalGroup(
            panelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMainLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelMainLayout.createSequentialGroup()
                        .addComponent(labelDeber)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelMainLayout.createSequentialGroup()
                        .addComponent(labelNombre)
                        .addGap(2, 2, 2)))
                .addGroup(panelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonConfirmar)
                    .addComponent(textDeber, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(panelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelMainLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(labelApellido)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelMainLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(buttonCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelMainLayout.createSequentialGroup()
                                .addComponent(labelHaber)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textHaber, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
            .addGroup(panelMainLayout.createSequentialGroup()
                .addGap(128, 128, 128)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelMainLayout.setVerticalGroup(
            panelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMainLayout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addGroup(panelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelNombre)
                    .addComponent(textNombre)
                    .addComponent(labelApellido)
                    .addComponent(textApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(panelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textDeber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelDeber)
                    .addComponent(textHaber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelHaber))
                .addGap(18, 18, 18)
                .addGroup(panelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addComponent(jLabel1))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
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
            java.util.logging.Logger.getLogger(ClienteGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClienteGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClienteGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClienteGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClienteGUI().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonCancelar;
    private javax.swing.JButton buttonConfirmar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel labelApellido;
    private javax.swing.JLabel labelDeber;
    private javax.swing.JLabel labelHaber;
    private javax.swing.JLabel labelNombre;
    private javax.swing.JPanel panelMain;
    private javax.swing.JTextField textApellido;
    private javax.swing.JTextField textDeber;
    private javax.swing.JTextField textHaber;
    private javax.swing.JTextField textNombre;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the buttonCancelar
     */
    public javax.swing.JButton getButtonCancelar() {
        return buttonCancelar;
    }

    /**
     * @return the buttonConfirmar
     */
    public javax.swing.JButton getButtonConfirmar() {
        return buttonConfirmar;
    }

    /**
     * @return the textApellido
     */
    public javax.swing.JTextField getTextApellido() {
        return textApellido;
    }

    /**
     * @return the textDeber
     */
    public javax.swing.JTextField getTextDeber() {
        return textDeber;
    }

    /**
     * @return the textHaber
     */
    public javax.swing.JTextField getTextHaber() {
        return textHaber;
    }

    /**
     * @return the textNombre
     */
    public javax.swing.JTextField getTextNombre() {
        return textNombre;
    }


}
