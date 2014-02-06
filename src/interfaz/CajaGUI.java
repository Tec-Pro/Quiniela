/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author joako
 */
public class CajaGUI extends javax.swing.JPanel {

    private DefaultTableModel tablaArtDef;
    private DefaultTableModel tablaCliDef;
    private DefaultTableModel tablaTransDef;
    private DefaultTableModel tablaDetallesDef;
    
    /**
     * Creates new form CajaGUI
     */
    public CajaGUI() {
        initComponents();
        tablaArtDef = (DefaultTableModel) tablaArticulos.getModel();
        tablaCliDef = (DefaultTableModel) tablaCliente.getModel();
        tablaTransDef = (DefaultTableModel) tablaTransacciones.getModel();
        tablaDetallesDef = (DefaultTableModel) tablaDetalles.getModel();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menuProducto = new javax.swing.JPopupMenu();
        detallesProd = new javax.swing.JMenuItem();
        panelArticulos = new javax.swing.JPanel();
        contTabArt = new javax.swing.JScrollPane();
        tablaArticulos = new javax.swing.JTable();
        panelTransacciones = new javax.swing.JPanel();
        contTrans = new javax.swing.JScrollPane();
        tablaTransacciones = new javax.swing.JTable();
        depManual = new javax.swing.JButton();
        retManual = new javax.swing.JButton();
        panelNuevaVenta = new javax.swing.JPanel();
        detallesVenta = new javax.swing.JScrollPane();
        tablaDetalles = new javax.swing.JTable();
        ventaOk = new javax.swing.JButton();
        totalField = new javax.swing.JTextField();
        totalLabel = new javax.swing.JLabel();
        panelCliente = new javax.swing.JPanel();
        contTabCli = new javax.swing.JScrollPane();
        tablaCliente = new javax.swing.JTable();
        clienteSel = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        menuProducto.setName(""); // NOI18N

        detallesProd.setText("Detalles");
        menuProducto.add(detallesProd);

        panelArticulos.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Articulos", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("DejaVu Sans", 0, 14))); // NOI18N

        tablaArticulos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Item", "Stock"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        contTabArt.setViewportView(tablaArticulos);
        if (tablaArticulos.getColumnModel().getColumnCount() > 0) {
            tablaArticulos.getColumnModel().getColumn(0).setMinWidth(50);
            tablaArticulos.getColumnModel().getColumn(0).setPreferredWidth(50);
            tablaArticulos.getColumnModel().getColumn(0).setMaxWidth(80);
            tablaArticulos.getColumnModel().getColumn(1).setMinWidth(100);
            tablaArticulos.getColumnModel().getColumn(1).setPreferredWidth(100);
            tablaArticulos.getColumnModel().getColumn(1).setMaxWidth(200);
            tablaArticulos.getColumnModel().getColumn(2).setMinWidth(30);
            tablaArticulos.getColumnModel().getColumn(2).setPreferredWidth(40);
            tablaArticulos.getColumnModel().getColumn(2).setMaxWidth(100);
        }

        javax.swing.GroupLayout panelArticulosLayout = new javax.swing.GroupLayout(panelArticulos);
        panelArticulos.setLayout(panelArticulosLayout);
        panelArticulosLayout.setHorizontalGroup(
            panelArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contTabArt, javax.swing.GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)
        );
        panelArticulosLayout.setVerticalGroup(
            panelArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contTabArt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
        );

        panelTransacciones.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Transacciones Realizadas", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("DejaVu Sans", 0, 14))); // NOI18N

        tablaTransacciones.setAutoCreateRowSorter(true);
        tablaTransacciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cod.", "Motivo", "Tipo", "Monto"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        contTrans.setViewportView(tablaTransacciones);
        if (tablaTransacciones.getColumnModel().getColumnCount() > 0) {
            tablaTransacciones.getColumnModel().getColumn(0).setMinWidth(8);
            tablaTransacciones.getColumnModel().getColumn(0).setPreferredWidth(40);
            tablaTransacciones.getColumnModel().getColumn(0).setMaxWidth(45);
            tablaTransacciones.getColumnModel().getColumn(2).setMinWidth(20);
            tablaTransacciones.getColumnModel().getColumn(2).setPreferredWidth(45);
            tablaTransacciones.getColumnModel().getColumn(2).setMaxWidth(50);
            tablaTransacciones.getColumnModel().getColumn(3).setMinWidth(20);
            tablaTransacciones.getColumnModel().getColumn(3).setPreferredWidth(60);
            tablaTransacciones.getColumnModel().getColumn(3).setMaxWidth(80);
        }

        depManual.setText("DEPOSITO MANUAL");

        retManual.setText("RETIRO MANUAL");

        javax.swing.GroupLayout panelTransaccionesLayout = new javax.swing.GroupLayout(panelTransacciones);
        panelTransacciones.setLayout(panelTransaccionesLayout);
        panelTransaccionesLayout.setHorizontalGroup(
            panelTransaccionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contTrans)
            .addGroup(panelTransaccionesLayout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(depManual)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(retManual)
                .addGap(41, 41, 41))
        );
        panelTransaccionesLayout.setVerticalGroup(
            panelTransaccionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTransaccionesLayout.createSequentialGroup()
                .addComponent(contTrans, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelTransaccionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(depManual)
                    .addComponent(retManual)))
        );

        panelNuevaVenta.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Venta", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("DejaVu Sans", 0, 14))); // NOI18N

        tablaDetalles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cod", "Item", "Cant", "Precio"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaDetalles.getTableHeader().setReorderingAllowed(false);
        detallesVenta.setViewportView(tablaDetalles);
        if (tablaDetalles.getColumnModel().getColumnCount() > 0) {
            tablaDetalles.getColumnModel().getColumn(0).setMinWidth(20);
            tablaDetalles.getColumnModel().getColumn(0).setPreferredWidth(30);
            tablaDetalles.getColumnModel().getColumn(0).setMaxWidth(40);
            tablaDetalles.getColumnModel().getColumn(1).setMinWidth(50);
            tablaDetalles.getColumnModel().getColumn(1).setPreferredWidth(100);
            tablaDetalles.getColumnModel().getColumn(1).setMaxWidth(300);
            tablaDetalles.getColumnModel().getColumn(2).setMinWidth(30);
            tablaDetalles.getColumnModel().getColumn(2).setPreferredWidth(50);
            tablaDetalles.getColumnModel().getColumn(2).setMaxWidth(70);
            tablaDetalles.getColumnModel().getColumn(3).setMinWidth(40);
            tablaDetalles.getColumnModel().getColumn(3).setPreferredWidth(60);
            tablaDetalles.getColumnModel().getColumn(3).setMaxWidth(100);
        }

        ventaOk.setText("OK");

        totalField.setEditable(false);

        totalLabel.setText("TOTAL:");

        javax.swing.GroupLayout panelNuevaVentaLayout = new javax.swing.GroupLayout(panelNuevaVenta);
        panelNuevaVenta.setLayout(panelNuevaVentaLayout);
        panelNuevaVentaLayout.setHorizontalGroup(
            panelNuevaVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(detallesVenta)
            .addGroup(panelNuevaVentaLayout.createSequentialGroup()
                .addComponent(ventaOk, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(totalLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(totalField, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelNuevaVentaLayout.setVerticalGroup(
            panelNuevaVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelNuevaVentaLayout.createSequentialGroup()
                .addComponent(detallesVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelNuevaVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ventaOk, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(totalField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(totalLabel))
                .addContainerGap())
        );

        contTabCli.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cuentas", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("DejaVu Sans", 0, 14))); // NOI18N
        contTabCli.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        tablaCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cod.", "Nombre y Apellido"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaCliente.setEnabled(false);
        tablaCliente.setFocusable(false);
        contTabCli.setViewportView(tablaCliente);
        if (tablaCliente.getColumnModel().getColumnCount() > 0) {
            tablaCliente.getColumnModel().getColumn(0).setMinWidth(30);
            tablaCliente.getColumnModel().getColumn(0).setPreferredWidth(60);
            tablaCliente.getColumnModel().getColumn(0).setMaxWidth(70);
            tablaCliente.getColumnModel().getColumn(1).setMinWidth(50);
            tablaCliente.getColumnModel().getColumn(1).setPreferredWidth(120);
            tablaCliente.getColumnModel().getColumn(1).setMaxWidth(300);
        }

        jLabel1.setText("Cliente Seleccionado");

        javax.swing.GroupLayout panelClienteLayout = new javax.swing.GroupLayout(panelCliente);
        panelCliente.setLayout(panelClienteLayout);
        panelClienteLayout.setHorizontalGroup(
            panelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelClienteLayout.createSequentialGroup()
                .addGroup(panelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(contTabCli, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(panelClienteLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(246, 246, 246))
                    .addComponent(clienteSel, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelClienteLayout.setVerticalGroup(
            panelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelClienteLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(contTabCli, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clienteSel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelArticulos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 20, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelTransacciones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelNuevaVenta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelArticulos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelNuevaVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 270, Short.MAX_VALUE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(panelCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(panelTransacciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(21, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    public void disableAll(){
        getDetallesVenta().setEnabled(false);
        getTablaArticulos().setEnabled(false);
        getTablaDetalles().setEnabled(false);
        getTotalField().setEnabled(false);
        getVentaOk().setEnabled(false);
        getDepManual().setEnabled(false);
        getRetManual().setEnabled(false);
        getClienteSel().setEnabled(false);
        getMenuProducto().setEnabled(false);
        getTablaTransacciones().setEnabled(false);
        contTabCli.setEnabled(false);
    }
    
    public void enableAll(){
        getDetallesVenta().setEnabled(true);
        getTablaArticulos().setEnabled(true);
        getTablaDetalles().setEnabled(true);
        getTotalField().setEnabled(true);
        getVentaOk().setEnabled(true);
        getDepManual().setEnabled(true);
        getRetManual().setEnabled(true);
        getTablaTransacciones().setEnabled(true);
        getClienteSel().setEnabled(true);
        getMenuProducto().setEnabled(true);
        contTabCli.setEnabled(true);
        tablaCliente.setEnabled(true);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField clienteSel;
    private javax.swing.JScrollPane contTabArt;
    private javax.swing.JScrollPane contTabCli;
    private javax.swing.JScrollPane contTrans;
    private javax.swing.JButton depManual;
    private javax.swing.JMenuItem detallesProd;
    private javax.swing.JScrollPane detallesVenta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPopupMenu menuProducto;
    private javax.swing.JPanel panelArticulos;
    private javax.swing.JPanel panelCliente;
    private javax.swing.JPanel panelNuevaVenta;
    private javax.swing.JPanel panelTransacciones;
    private javax.swing.JButton retManual;
    private javax.swing.JTable tablaArticulos;
    private javax.swing.JTable tablaCliente;
    private javax.swing.JTable tablaDetalles;
    private javax.swing.JTable tablaTransacciones;
    public javax.swing.JTextField totalField;
    private javax.swing.JLabel totalLabel;
    private javax.swing.JButton ventaOk;
    // End of variables declaration//GEN-END:variables

  
    /**
     * @return the depManual
     */
    public javax.swing.JButton getDepManual() {
        return depManual;
    }

    /**
     * @param depManual the depManual to set
     */
    public void setDepManual(javax.swing.JButton depManual) {
        this.depManual = depManual;
    }

    /**
     * @return the detallesVenta
     */
    public javax.swing.JScrollPane getDetallesVenta() {
        return detallesVenta;
    }

    /**
     * @param detallesVenta the detallesVenta to set
     */
    public void setDetallesVenta(javax.swing.JScrollPane detallesVenta) {
        this.detallesVenta = detallesVenta;
    }

    /**
     * @return the retManual
     */
    public javax.swing.JButton getRetManual() {
        return retManual;
    }

    /**
     * @param retManual the retManual to set
     */
    public void setRetManual(javax.swing.JButton retManual) {
        this.retManual = retManual;
    }

    /**
     * @return the tablaArticulos
     */
    public javax.swing.JTable getTablaArticulos() {
        return tablaArticulos;
    }

    /**
     * @param tablaArticulos the tablaArticulos to set
     */
    public void setTablaArticulos(javax.swing.JTable tablaArticulos) {
        this.tablaArticulos = tablaArticulos;
    }

    /**
     * @return the tablaCliente
     */
    public javax.swing.JTable getTablaCliente() {
        return tablaCliente;
    }

    /**
     * @param tablaCliente the tablaCliente to set
     */
    public void setTablaCliente(javax.swing.JTable tablaCliente) {
        this.tablaCliente = tablaCliente;
    }

    /**
     * @return the tablaDetalles
     */
    public javax.swing.JTable getTablaDetalles() {
        return tablaDetalles;
    }

    /**
     * @param tablaDetalles the tablaDetalles to set
     */
    public void setTablaDetalles(javax.swing.JTable tablaDetalles) {
        this.tablaDetalles = tablaDetalles;
    }

    /**
     * @return the tablaTransacciones
     */
    public javax.swing.JTable getTablaTransacciones() {
        return tablaTransacciones;
    }

    /**
     * @param tablaTransacciones the tablaTransacciones to set
     */
    public void setTablaTransacciones(javax.swing.JTable tablaTransacciones) {
        this.tablaTransacciones = tablaTransacciones;
    }

    /**
     * @return the totalField
     */
    public javax.swing.JTextField getTotalField() {
        return totalField;
    }

    /**
     * @param totalField the totalField to set
     */
    public void setTotalField(javax.swing.JTextField totalField) {
        this.totalField = totalField;
    }

    /**
     * @return the ventaOk
     */
    public javax.swing.JButton getVentaOk() {
        return ventaOk;
    }

    /**
     * @param ventaOk the ventaOk to set
     */
    public void setVentaOk(javax.swing.JButton ventaOk) {
        this.ventaOk = ventaOk;
    }

    /**
     * @return the tablaArtDef
     */
    public DefaultTableModel getTablaArtDef() {
        return tablaArtDef;
    }

    /**
     * @return the tablaCliDef
     */
    public DefaultTableModel getTablaCliDef() {
        return tablaCliDef;
    }

    /**
     * @return the tablaTransDef
     */
    public DefaultTableModel getTablaTransDef() {
        return tablaTransDef;
    }
    
    /**
     * 
     * @return the tablaDetallesDef
     */
    public DefaultTableModel getTablaDetDef() {
        return tablaDetallesDef;
    }
    
    public javax.swing.JTextField getClienteSel() {
        return clienteSel;
    }

    /**
     * @return the detallesProd
     */
    public javax.swing.JMenuItem getDetallesProd() {
        return detallesProd;
    }

    /**
     * @return the menuProducto
     */
    public javax.swing.JPopupMenu getMenuProducto() {
        return menuProducto;
    }

    public void detalleProducto() {
        JFrame frame = new JFrame("Detalles Producto");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
 
}
