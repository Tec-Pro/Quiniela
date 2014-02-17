/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import java.awt.Font;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 *
 * @author joako
 */
public class MainGUI extends javax.swing.JFrame {

    private final CajaGUI caja;
    private EstadisticasGUI estadisticas;
    private final JMenu menuAdmin;
    private final javax.swing.JMenuItem menuUsuario;

    /**
     * Creates new form MainGUI
     */
    public MainGUI() {
        initComponents();
        caja = new CajaGUI();
        estadisticas = new EstadisticasGUI();
        caja.disableAll();
        tab.add("Caja", caja);
        tab.add("Estadísticas", estadisticas);
        tab.setToolTipTextAt(0, "Manejo de la Caja Diaria");
        tab.setToolTipTextAt(1, "Estadisticas de ventas y productos");
        menuAdmin = new JMenu("Administrador");
        menuUsuario = new JMenuItem();
        this.setIconImage(new javax.swing.ImageIcon(getClass().getResource("/recursos/icono.png")).getImage());
        this.setTitle("Sistema de Gestión de Quiniela");

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tab = new javax.swing.JTabbedPane();
        barraMenu = new javax.swing.JMenuBar();
        archivo = new javax.swing.JMenu();
        abrirCaja = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        cajasAnteriores = new javax.swing.JMenuItem();
        imprimirParcial = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        cerrarCaja = new javax.swing.JMenuItem();
        cuenta = new javax.swing.JMenu();
        ventanaClientes = new javax.swing.JMenuItem();
        imprimirClientes = new javax.swing.JMenuItem();
        producto = new javax.swing.JMenu();
        ventanaProductos = new javax.swing.JMenuItem();
        imprimirProducto = new javax.swing.JMenuItem();
        ayuda = new javax.swing.JMenu();
        acercaDe = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(800, 600));

        jScrollPane1.setViewportView(tab);

        archivo.setText("Caja");
        archivo.setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N

        abrirCaja.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        abrirCaja.setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N
        abrirCaja.setText("Abrir");
        abrirCaja.setToolTipText("Abre una caja para empezar a registrar transacciones.");
        archivo.add(abrirCaja);

        jSeparator1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        archivo.add(jSeparator1);

        cajasAnteriores.setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N
        cajasAnteriores.setText("Anteriores");
        cajasAnteriores.setActionCommand("cajasAnteriores");
        archivo.add(cajasAnteriores);

        imprimirParcial.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        imprimirParcial.setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N
        imprimirParcial.setText("Parcial");
        imprimirParcial.setActionCommand("cajaParcial");
        archivo.add(imprimirParcial);

        jSeparator2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        archivo.add(jSeparator2);

        cerrarCaja.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        cerrarCaja.setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N
        cerrarCaja.setText("Cerrar");
        archivo.add(cerrarCaja);

        barraMenu.add(archivo);

        cuenta.setText("Clientes");
        cuenta.setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N

        ventanaClientes.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.ALT_MASK));
        ventanaClientes.setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N
        ventanaClientes.setText("Detalles");
        ventanaClientes.setActionCommand("VentanaClientes");
        cuenta.add(ventanaClientes);

        imprimirClientes.setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N
        imprimirClientes.setText("Imprimir");
        imprimirClientes.setActionCommand("imprimirClientes");
        cuenta.add(imprimirClientes);

        barraMenu.add(cuenta);

        producto.setText("Productos");
        producto.setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N

        ventanaProductos.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.ALT_MASK));
        ventanaProductos.setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N
        ventanaProductos.setText("Detalles");
        ventanaProductos.setActionCommand("VentanaProductos");
        producto.add(ventanaProductos);

        imprimirProducto.setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N
        imprimirProducto.setText("Imprimir");
        imprimirProducto.setActionCommand("imprimirProductos");
        producto.add(imprimirProducto);

        barraMenu.add(producto);

        ayuda.setText("Ayuda");
        ayuda.setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N

        acercaDe.setText("Acerca De");
        ayuda.add(acercaDe);

        barraMenu.add(ayuda);

        setJMenuBar(barraMenu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1356, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 599, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem abrirCaja;
    private javax.swing.JMenuItem acercaDe;
    private javax.swing.JMenu archivo;
    private javax.swing.JMenu ayuda;
    private javax.swing.JMenuBar barraMenu;
    private javax.swing.JMenuItem cajasAnteriores;
    private javax.swing.JMenuItem cerrarCaja;
    private javax.swing.JMenu cuenta;
    private javax.swing.JMenuItem imprimirClientes;
    private javax.swing.JMenuItem imprimirParcial;
    private javax.swing.JMenuItem imprimirProducto;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JMenu producto;
    private javax.swing.JTabbedPane tab;
    private javax.swing.JMenuItem ventanaClientes;
    private javax.swing.JMenuItem ventanaProductos;
    // End of variables declaration//GEN-END:variables

    public CajaGUI getCaja() {
        return caja;
    }

    /**
     * @return the abrirCaja
     */
    public javax.swing.JMenuItem getAbrirCaja() {
        return abrirCaja;
    }

    /**
     * @return the archivo
     */
    public javax.swing.JMenu getArchivo() {
        return archivo;
    }

    /**
     * @return the ayuda
     */
    public javax.swing.JMenu getAyuda() {
        return ayuda;
    }

    /**
     * @return the cerrarCaja
     */
    public javax.swing.JMenuItem getCerrarCaja() {
        return cerrarCaja;
    }

    /**
     * @return the producto
     */
    public javax.swing.JMenu getProducto() {
        return producto;
    }

    /**
     * @return the ventanaProductos
     */
    public javax.swing.JMenuItem getVentanaProductos() {
        return ventanaProductos;
    }

    /**
     * @return the ventanaClientes
     */
    public javax.swing.JMenuItem getVentanaClientes() {
        return ventanaClientes;
    }

    /**
     * @return the imprimirClientes
     */
    public javax.swing.JMenuItem getImprimirClientes() {
        return imprimirClientes;
    }

    /**
     * @return the imprimirParcial
     */
    public javax.swing.JMenuItem getImprimirParcial() {
        return imprimirParcial;
    }

    /**
     * @return the imprimirProductos
     */
    public javax.swing.JMenuItem getImprimirProductos() {
        return imprimirProducto;
    }

    /**
     * @return the cuenta
     */
    public javax.swing.JMenu getCuenta() {
        return cuenta;
    }

    /**
     * @return the estadisticas
     */
    public EstadisticasGUI getEstadisticas() {
        return estadisticas;
    }

    /**
     * @param estadisticas the estadisticas to set
     */
    public void setEstadisticas(EstadisticasGUI estadisticas) {
        this.estadisticas = estadisticas;
    }

    public void addMenuAdmin() {
        this.barraMenu.add(menuAdmin);
        getMenuUsuario().setText("Usuarios");
        getMenuUsuario().setActionCommand("MenuUsuario");
        Font f = new Font("SansSerif",Font.PLAIN,16);
        menuAdmin.setFont(f);
        getMenuUsuario().setFont(f);
        menuAdmin.add(getMenuUsuario());
    }

    /**
     * @return the menuUsuario
     */
    public javax.swing.JMenuItem getMenuUsuario() {
        return menuUsuario;
    }

    /**
     * @return the cajasAnteriores
     */
    public javax.swing.JMenuItem getCajasAnteriores() {
        return cajasAnteriores;
    }

    /**
     * @return the acercaDe
     */
    public javax.swing.JMenuItem getAcercaDe() {
        return acercaDe;
    }

}
