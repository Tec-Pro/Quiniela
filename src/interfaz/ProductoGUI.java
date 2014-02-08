/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package interfaz;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author eze
 */
public final class ProductoGUI extends javax.swing.JFrame {

    private final DefaultTableModel tablaProdDef,tablaStockFechaDef;
    
    /**
     * Creates new form ProductoGUI
     */
    public ProductoGUI() {
        initComponents();
        tablaProdDef= (DefaultTableModel) getTablaProductos().getModel();
        tablaStockFechaDef= (DefaultTableModel) getTablaStockFecha().getModel();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaProductos = new javax.swing.JTable();
        ProdNuevo = new javax.swing.JButton();
        ProdModificar = new javax.swing.JButton();
        ProdEliminar = new javax.swing.JButton();
        ProdActualizar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        TablaStockFecha = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        LabelProducto = new javax.swing.JLabel();
        Insertar = new javax.swing.JButton();
        Quitar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel1.setText("PRODUCTOS");

        TablaProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Precio", "Comision", "Prod. c/Stock", "Stock"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Boolean.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(TablaProductos);
        TablaProductos.getColumnModel().getColumn(4).setCellEditor(TablaProductos.getDefaultEditor(Boolean.class));
        TablaProductos.getColumnModel().getColumn(4).setCellRenderer(TablaProductos.getDefaultRenderer(Boolean.class));

        ProdNuevo.setText("Nuevo");
        ProdNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProdNuevoActionPerformed(evt);
            }
        });

        ProdModificar.setText("Guardar Cambios");
        ProdModificar.setActionCommand("Modificar");
        ProdModificar.setEnabled(false);
        ProdModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProdModificarActionPerformed(evt);
            }
        });

        ProdEliminar.setText("Eliminar");
        ProdEliminar.setEnabled(false);

        ProdActualizar.setText("Actualizar Listado");
        ProdActualizar.setActionCommand("Actualizar");
        ProdActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProdActualizarActionPerformed(evt);
            }
        });

        TablaStockFecha.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Dias de Deposito"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(TablaStockFecha);

        jLabel2.setText("Detalles de:");

        Insertar.setText("Insertar");
        Insertar.setEnabled(false);
        Insertar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InsertarActionPerformed(evt);
            }
        });

        Quitar.setText("Quitar");
        Quitar.setEnabled(false);
        Quitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                QuitarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(ProdNuevo)
                        .addGap(18, 18, 18)
                        .addComponent(ProdEliminar)
                        .addGap(18, 18, 18)
                        .addComponent(ProdModificar)
                        .addGap(109, 109, 109)
                        .addComponent(ProdActualizar))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 598, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(68, 68, 68)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(jLabel2)
                                .addGap(37, 37, 37)
                                .addComponent(LabelProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(51, 51, 51)
                                .addComponent(Insertar)
                                .addGap(26, 26, 26)
                                .addComponent(Quitar)))))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(LabelProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Insertar)
                            .addComponent(Quitar))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ProdModificar)
                    .addComponent(ProdEliminar)
                    .addComponent(ProdNuevo)
                    .addComponent(ProdActualizar))
                .addGap(23, 23, 23))
        );

        ProdEliminar.getAccessibleContext().setAccessibleDescription("");
        ProdActualizar.getAccessibleContext().setAccessibleName("Actualizar");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ProdNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProdNuevoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ProdNuevoActionPerformed

    private void ProdModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProdModificarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ProdModificarActionPerformed

    private void ProdActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProdActualizarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ProdActualizarActionPerformed

    private void QuitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_QuitarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_QuitarActionPerformed

    private void InsertarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InsertarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_InsertarActionPerformed

    public JButton getProdActualizar() {
        return ProdActualizar;
    }

    public JButton getInsertar() {
        return Insertar;
    }

    public JLabel getLabelProducto() {
        return LabelProducto;
    }

    public JButton getQuitar() {
        return Quitar;
    }

    public JTable getTablaStockFecha() {
        return TablaStockFecha;
    }

    public JButton getProdEliminar() {
        return ProdEliminar;
    }

    public JButton getProdModificar() {
        return ProdModificar;
    }

    public JButton getProdNuevo() {
        return ProdNuevo;
    }

    public final JTable getTablaProductos() {
        return TablaProductos;
    }

    public DefaultTableModel getTablaProductosDef(){
        return (DefaultTableModel) tablaProdDef;
    }
    
    public DefaultTableModel getTablaStockFechaDef(){
        return (DefaultTableModel) tablaStockFechaDef;
    }
    
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
            java.util.logging.Logger.getLogger(ProductoGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ProductoGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ProductoGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ProductoGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ProductoGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Insertar;
    private javax.swing.JLabel LabelProducto;
    private javax.swing.JButton ProdActualizar;
    private javax.swing.JButton ProdEliminar;
    private javax.swing.JButton ProdModificar;
    private javax.swing.JButton ProdNuevo;
    private javax.swing.JButton Quitar;
    private javax.swing.JTable TablaProductos;
    private javax.swing.JTable TablaStockFecha;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
