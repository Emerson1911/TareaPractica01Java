/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package grupo.practicatarea01;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableRowSorter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author emers
 */
public class CCuenta {

    private int cuentaID;
    private String numeroCuenta;
    private String nombre;
    private String tipo;
    private int nivel;
    private int padre;

    public int getCuentaID() {
        return cuentaID;
    }

    public void setCuentaID(int cuentaID) {
        this.cuentaID = cuentaID;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getPadre() {
        return padre;
    }

    public void setPadre(int padre) {
        this.padre = padre;
    }
    
     // Método para cargar los datos en un JComboBox
    public void cargarDatosPadre(JComboBox<String> comboBox) {
        // Limpiar el JComboBox antes de cargar los nuevos datos
        comboBox.removeAllItems();

        // Obtener los datos del padre de la base de datos
        String consulta = "SELECT CuentaID FROM Cuentas";

        try {
            CConexion conexion = new CConexion();
            CallableStatement cs = conexion.obtenerConexion().prepareCall(consulta);
            ResultSet rs = cs.executeQuery();

            // Agregar los datos al JComboBox como String
            while (rs.next()) {
                comboBox.addItem(String.valueOf(rs.getInt("CuentaID")));
            }

            rs.close();
            cs.close(); // Cierra el statement
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar los datos del JComboBox: " + e.toString());
        }
    }


    public void InsertarCuenta(JTextField paramNumeroCuenta, JTextField paramNombre, JTextField paramTipo, JTextField paramNivel, JComboBox<String> paramPadre) {
        setNumeroCuenta(paramNumeroCuenta.getText());
        setNombre(paramNombre.getText());
        setTipo(paramTipo.getText());
        try {
            setNivel(Integer.parseInt(paramNivel.getText()));
        } catch (NumberFormatException e) {
            System.err.println("Error: el valor de Nivel debe ser un número entero.");
            e.printStackTrace();
        }

       
        String padreSeleccionado = (String) paramPadre.getSelectedItem();
        if (padreSeleccionado != null && !padreSeleccionado.isEmpty()) {
            try {
               
                setPadre(Integer.parseInt(padreSeleccionado));
            } catch (NumberFormatException e) {
                System.err.println("Error: el valor seleccionado en el JComboBox no es un número válido.");
                e.printStackTrace();
            }
        } else {
           
            setPadre(0); 
        }

        // Insertar la cuenta en la base de datos
        CConexion conexion = new CConexion();
        String consulta = "INSERT INTO Cuentas (NumeroCuenta, Nombre, Tipo, Nivel, Padre) VALUES (?, ?, ?, ?, ?);";

        try {
            CallableStatement cs = conexion.obtenerConexion().prepareCall(consulta);
            cs.setString(1, getNumeroCuenta());
            cs.setString(2, getNombre());
            cs.setString(3, getTipo());
            cs.setInt(4, getNivel());
           
            cs.setObject(5, getPadre() != 0 ? getPadre() : null);

            cs.execute();

            JOptionPane.showMessageDialog(null, "Se insertó correctamente la Cuenta");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al insertar la Cuenta: " + e.toString());
        }
    }
    
    public void MostrarCuenta(JTable paramTablaTotalCuenta){
        CConexion conexion = new CConexion();
        
        DefaultTableModel modelo = new DefaultTableModel();
        
        TableRowSorter<TableModel> ordenarTabla= new TableRowSorter<TableModel>(modelo);
        paramTablaTotalCuenta.setRowSorter(ordenarTabla);
        
        var  sql="";
        modelo.addColumn("Id");
        modelo.addColumn("NumeroCuenta");
        modelo.addColumn("Nombre");
        modelo.addColumn("Tipo");
        modelo.addColumn("Nivel");
        modelo.addColumn("Padre");
        
        paramTablaTotalCuenta.setModel(modelo);
        
        sql = "SELECT * FROM Cuentas;";
        
        String[] datos = new String[6];
        Statement st;
        try {
            st = conexion.obtenerConexion().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
               datos[0]=rs.getString(1);
               datos[1]=rs.getString(2);
               datos[2]=rs.getString(3);
               datos[3]=rs.getString(4);
               datos[4]=rs.getString(5);
               datos[5]=rs.getString(6);
               
               modelo.addRow(datos);
               
            }
            paramTablaTotalCuenta.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar los registros: " + e.toString());
        }
    }

}
