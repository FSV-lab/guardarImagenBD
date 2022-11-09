
package programa;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import modelo.Conexion;


public class Ventana extends javax.swing.JFrame {

    
    public Ventana() {
        initComponents();
        
        
    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelImagen = new javax.swing.JPanel();
        botonCargar = new javax.swing.JButton();
        botonMostrar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        botonCargar.setText("Cargar Imagen");
        botonCargar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCargarActionPerformed(evt);
            }
        });

        botonMostrar.setText("Mostrar Imagen");
        botonMostrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonMostrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelImagenLayout = new javax.swing.GroupLayout(panelImagen);
        panelImagen.setLayout(panelImagenLayout);
        panelImagenLayout.setHorizontalGroup(
            panelImagenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelImagenLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(botonCargar)
                .addGap(59, 59, 59)
                .addComponent(botonMostrar)
                .addContainerGap(86, Short.MAX_VALUE))
        );
        panelImagenLayout.setVerticalGroup(
            panelImagenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelImagenLayout.createSequentialGroup()
                .addContainerGap(453, Short.MAX_VALUE)
                .addGroup(panelImagenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonCargar)
                    .addComponent(botonMostrar))
                .addGap(29, 29, 29))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelImagen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelImagen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonCargarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCargarActionPerformed
        JFileChooser escoger = new JFileChooser();
        escoger.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);//Establecemos el modelo
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("*jpg","jpg");
        FileNameExtensionFilter filtro2 = new FileNameExtensionFilter("*png","png");//Mas filtros
        escoger.setFileFilter(filtro);
         escoger.setFileFilter(filtro2);
        
        int seleccion = escoger.showOpenDialog(this);//darnos cuentas de la opcion que el usuario ha escogido
        
        PreparedStatement ps = null;
        Conexion con = new Conexion();
        
        if (seleccion == JFileChooser.APPROVE_OPTION) {//si el usuario escogió aceptar 
          File archivo = escoger.getSelectedFile();//Obtenemos el archivo seleccionado
         
            try {
                
                FileInputStream archivoEntrada = new FileInputStream(archivo);
                Connection Conexion = con.getConnection();
                
                ps = Conexion.prepareStatement("insert into imagen(img)values (?)");
                ps.setBinaryStream(1, archivoEntrada, (int)archivo.length());
                ps.executeUpdate();
                
                JOptionPane.showMessageDialog(null,"Imagen Guardada!");
                
            } catch (Exception ex) {
                System.err.println("Error ,"+ex);
            }
           
        }
        
        //int ancho = panelImagen.getWidth();
        //int alto = panelImagen.getHeight();
        //String ruta = "/imagenes/backetball.jpg";
        
        //Imagen imagen = new Imagen(ancho, alto, ruta);
        //panelImagen.add(imagen);
        //panelImagen.repaint();
    }//GEN-LAST:event_botonCargarActionPerformed

    private void botonMostrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonMostrarActionPerformed
       int idimagen = Integer.parseInt(JOptionPane.showInputDialog("Digite la id de la imagen que desea cargar"));
       
       PreparedStatement ps = null;
       ResultSet rs = null ;
       Conexion con = new Conexion();
       
        try {
            Connection conexion = con.getConnection();
            ps = conexion.prepareCall("select img from imagen where idimagen=?");
            ps.setInt(1, idimagen);
            rs = ps.executeQuery();
            
            BufferedImage buffimg = null;
            
            while (rs.next()) {                
                InputStream img = rs.getBinaryStream(1);
                buffimg = ImageIO.read(img);
                
                 int ancho = panelImagen.getWidth();
                 int alto = panelImagen.getHeight();
                
        
                 Imagen imagen = new Imagen(ancho, alto,buffimg);
                 panelImagen.add(imagen);
                 panelImagen.repaint();
            }
            rs.close();
        } catch (Exception ex) {
            System.err.println("Error ,"+ex);
        }
       
    }//GEN-LAST:event_botonMostrarActionPerformed

    
    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Ventana().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonCargar;
    private javax.swing.JButton botonMostrar;
    private javax.swing.JPanel panelImagen;
    // End of variables declaration//GEN-END:variables
}
