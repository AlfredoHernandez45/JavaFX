/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML.java to edit this template
 */
package calculadora;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import jdk.nashorn.internal.runtime.ParserException;

/**
 *
 * @author joselopez
 */
public class Calculadora extends Application {
    //Declarar variables
    @FXML
    private TextField textfieldResult;
    
    @FXML
    private TextField resultadoFinal;

    private boolean operationOn = true;
    
    private double lasOperation = 0;
    
    /*
    *===========================================================================
    * Variables de la version 2
    *===========================================================================
    */
    
    @FXML
    private Button a;

    @FXML
    private Button b;

    @FXML
    private Button c;
    
    @FXML
    private Button d;
    
    @FXML
    private Button e;
    
    @FXML
    private Button f;
    
    @FXML
    private CheckBox optBin;
    
    @FXML
    private CheckBox optDecimal;

    @FXML
    private CheckBox optHex;

    @FXML
    private CheckBox optOcta;
    
    private int decimal = 0;
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(args);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        
        Parent root = FXMLLoader.load(getClass().getResource("calculadora.fxml"));
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
    }

    /*
    *===========================================================================
    */
    
    /*
    * Se ingresan los valores de cada numero
    */
    @FXML
    void addValue(ActionEvent event) {
        
        //Se asigna al TextField "textfieldResult" el valor obtenido de cada boton.
        textfieldResult.setText(textfieldResult.getText() + ((Button)event.getSource()).getText());
        operationOn = true;//indica que seguido de un numero pueda ingresar un operador
        
        Pattern operacion = Pattern.compile("^(\\-?\\d+(\\.?\\d+)?[\\/(x)\\+\\-])+((\\d+(\\.?\\d+)?)+){1}$");
        Matcher matchoperation = operacion.matcher(textfieldResult.getText());
        
        if (matchoperation.matches()) {
            //manda a la funcion para calcular el resultado
            resultadoOperacion(event);
        }
        
    }
    
    /*
    * Agrega los operadores al TextField "textfieldResult"
    */
    @FXML
    void addOperation(ActionEvent event) {
        if (operationOn) {
            textfieldResult.setText(textfieldResult.getText() + ((Button)event.getSource()).getText());
            operationOn = false;//impide que engresen 2 operadores seguidos
        }
        
    }
    
    /*
    * Limpia los dos TextFields
    */
    @FXML
    void cleanScreen(ActionEvent event) {
        textfieldResult.setText("");
        resultadoFinal.setText("");
        operationOn = true;
          
    }

    /*
    * elimina el ultimo valor ingresado
    */
    @FXML
    void delValue(ActionEvent event) {
        //si el text field no esta vacio
        if(!(textfieldResult.getText().length() == 0)) {
            //al text field se le asigna el text field obtenido de su longitud menos 1
            textfieldResult.setText(textfieldResult.getText().substring(0, textfieldResult.getText().length() - 1));
        }
        
    }

    /*
    * Obtiene el ultimo resultado obtenido
    */
    @FXML
    void getLasResult(ActionEvent event) {
        if(!(lasOperation == 0)){
            textfieldResult.clear();
            textfieldResult.setText(String.valueOf(lasOperation));
        }

    }

    /*
    * Obtiene el resultado al precionar "="
    */
    @FXML
    void resultadoOperacion(ActionEvent event) {
        
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");
        
        try {
            //evalua las operaciones, en caso de tener una "x" replazarlo por un "*" para la multiplicacion
            Object operation = engine.eval(textfieldResult.getText().replaceAll("x", "*"));
            resultadoFinal.setText("" + operation );// resultado se muestra en el TextField resultados
            lasOperation = Integer.parseInt(resultadoFinal.getText());//guarda el resultado de la ultima operacion
            
            //Didige a resultado 
            if (optBin.isSelected()) {
                
                optHex.setSelected(false);
                optDecimal.setSelected(false);
                optOcta.setSelected(false);
                sistemaBinario(event);
                
            }
            else if (optDecimal.isSelected()) {
                
                optHex.setSelected(false);
                optBin.setSelected(false);
                optOcta.setSelected(false);
                sistemaDecimal(event);
                
            } 
            else if (optHex.isSelected()) {
                
                optDecimal.setSelected(false);
                optBin.setSelected(false);
                optOcta.setSelected(false);
                sistemaHexadecimal(event);
                
            } else if(optOcta.isSelected()){
                
                optHex.setSelected(false);
                optDecimal.setSelected(false);
                optBin.setSelected(false);
                sistemaOctal(event);
            }

            
        } catch (ScriptException | ParserException e) {
            textfieldResult.setText("nan");
        }
    }
    
    /*
    * Cambia el signo del resultado al precionar el boton "+/-"
    */
    @FXML
    void cambiarSigno(ActionEvent event) {
        
        int number = Integer.parseInt(resultadoFinal.getText());//Obtiene el resultado
        number = number*(-1);//se convierte a negativo o positivo
        resultadoFinal.setText(String.valueOf(number));
        
    }
    
    /*
    *===========================================================================
    * V2
    *===========================================================================
    * Para realizar la conversion de Decimal a Binario, Octal, Hexadecimal.
    * Primero se debe presionar el signo =
    */
    
    /**
    Pattern regularBinario = Pattern.compile("^[0-1]+$");
    Pattern regularOctal = Pattern.compile("^[0-7]+$");
    Pattern regularHexadecimal = Pattern.compile("^[0-9A-F]+$");
    
    Matcher matchBin = regularBinario.matcher(resultadoFinal.getText());
    Matcher matchOct = regularOctal.matcher(resultadoFinal.getText());
    Matcher matchHex = regularHexadecimal.matcher(resultadoFinal.getText());
           */ 
    /*
    * Cambia de decimal a binario
    */
    @FXML
    void sistemaBinario(ActionEvent event) {
        
        if (optBin.isSelected()) {
            
            //Se deactiva las demas opciones
            optHex.setSelected(false);
            optDecimal.setSelected(false);
            optOcta.setSelected(false);
            
            //if () {
                decimal = Integer.parseInt(resultadoFinal.getText());
                String binario = convertidor(2);
                resultadoFinal.setText(binario);
            //}
            
            
        } else {
            resultadoFinal.clear();
            optDecimal.setSelected(true);
            
        }
        
    }
    
    /*
    * Cambia a decimal 
    */
    @FXML
    void sistemaDecimal(ActionEvent event) {
        
        if (optDecimal.isSelected()) {
            
            //Se deactiva las demas opciones
            optHex.setSelected(false);
            optBin.setSelected(false);
            optOcta.setSelected(false);
            
            if (resultadoFinal.getText().isEmpty()) {
                decimal = Integer.parseInt(textfieldResult.getText());
                //regresar a decimal
                
                //String binario = convertidor(2);
                //System.out.println(binario);
                //resultadoFinal.setText(binario);
            }
            else{
                decimal = Integer.parseInt(resultadoFinal.getText());
                convertidor(2);
            }
            
        } else {
            resultadoFinal.clear();
            sistemaDecimal(event);
            //resultadoFinal.setText("Hola perra");
        }
    }

    /*
    * Cambia de decimal a hexadecimal
    */
    @FXML
    void sistemaHexadecimal(ActionEvent event) {
        addHexa(event);
        
        if (optHex.isSelected()) {
            
            //Se deactiva las demas opciones
            optDecimal.setSelected(false);
            optBin.setSelected(false);
            optOcta.setSelected(false);
            
            if (resultadoFinal.getText().isEmpty()) {
                decimal = Integer.parseInt(textfieldResult.getText());
                String hexaadecimal = convertidor(16);
                resultadoFinal.setText(hexaadecimal);
            }
            else{
                decimal = Integer.parseInt(resultadoFinal.getText());
                String hexaadecimal = convertidor(16);
                resultadoFinal.setText(hexaadecimal);
            }
            
        } else {
            resultadoFinal.clear();//elimina el resultado
            optDecimal.setSelected(true);//activa la opciones decimal
            sistemaDecimal(event);
        }
    }
    
    /*
     * Agregar un valor hexadecimal, de la A a la F
     */
    @FXML
    void addHexa(ActionEvent event) {
        
        if (optHex.isSelected()) {
            a.setDisable(false);
            b.setDisable(false);
            c.setDisable(false);
            d.setDisable(false);
            e.setDisable(false);
            f.setDisable(false);
            
            textfieldResult.setText(textfieldResult.getText() + ((Button)event.getSource()).getText());
        
            
        } else if (!optHex.isSelected()) {
            
            a.setDisable(true);
            b.setDisable(true);
            c.setDisable(true);
            d.setDisable(true);
            e.setDisable(true);
            f.setDisable(true);
        }
    }
    
    /*
    * Cambia de decimal a octal
    */
    @FXML
    void sistemaOctal(ActionEvent event) {
        
        if (optOcta.isSelected()) {
            
            optHex.setSelected(false);
            optDecimal.setSelected(false);
            optBin.setSelected(false);
            
            if (resultadoFinal.getText().isEmpty()) {
                decimal = Integer.parseInt(textfieldResult.getText());
                String octal = convertidor(8);
                resultadoFinal.setText(octal);
            }
            else{
                decimal = Integer.parseInt(resultadoFinal.getText());
                String octal = convertidor(8);
                resultadoFinal.setText(octal);
            }
            
        } else {
            resultadoFinal.clear();
            optDecimal.setSelected(true);
        }
        
    }
    
    private String convertidor(int systemNum){
        Vector<String> order = new Vector<String>();
        String str = "";
        
        try {
            while (decimal>0) {            
            //Guarda los residuos en la posicion 0
            order.add(String.valueOf(decimal%systemNum));
            //Asigna el nuevo resultado
            decimal = decimal/systemNum;
            //System.out.println("decimal "+decimal);
            }
        
            //Ordena el resultado ->
            for (int i = order.size(); i > 0; i--) {
                //ordena el resultado en la posicion 1
                str += order.get(i-1);
                //System.out.println(str);
            }
        } catch (StringIndexOutOfBoundsException e) {
            textfieldResult.setText("");
        }
        //System.out.println(str);
        return str;
    }
    
    
}
