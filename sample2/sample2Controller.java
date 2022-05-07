/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package sample2;

import com.sun.javafx.scene.layout.region.Margins;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import jdk.nashorn.internal.runtime.ParserException;

/**
 *
 * @author joselopez
 */
public class sample2Controller implements Initializable {
//Declarar variables
    @FXML
    private TextField textfieldResult;
    
    @FXML
    private TextField resultadoFinal;

    private boolean operationOn = true;
    
    private double lasOperation = 0;
    
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
    * Apartade de la segunda version
    */
    @FXML
    private CheckBox optBin;
    
    @FXML
    private CheckBox optDecimal;

    @FXML
    private CheckBox optHex;

    @FXML
    private CheckBox optOcta;
    
    private int decimal = 0;
    
    /*
    * Cambia de decimal a binario
    */
    @FXML
    void sistemaBinario(ActionEvent event) {
        
        if (optBin.isSelected()) {
            
            if (resultadoFinal.getText().isEmpty()) {
                decimal = Integer.parseInt(textfieldResult.getText());
                String binario = convertidor(2);
                //System.out.println(binario);
                resultadoFinal.setText(binario);
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
    * Cambia a decimal 
    */
    @FXML
    void sistemaDecimal(ActionEvent event) {
        optDecimal.setSelected(true);
        
        if (optBin.isSelected()) {
            
            if (resultadoFinal.getText().isEmpty()) {
                decimal = Integer.parseInt(textfieldResult.getText());
                String binario = convertidor(2);
                //System.out.println(binario);
                resultadoFinal.setText(binario);
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

    }
    
    /*
     * Agregar un valor hexadecimal, de la A a la F
     */
    @FXML
    void addHexa(ActionEvent event) {

    }
    
    /*
    * Cambia de decimal a octal
    */
    @FXML
    void sistemaOctal(ActionEvent event) {
        Object p = new Object();
        String str = "";
        
        try {
            LinkedList<String> octal = new LinkedList<String>();
            String[] bin;
                    
            if (resultadoFinal.getText().isEmpty()) {
                decimal = Integer.parseInt(textfieldResult.getText());
            }
            else{
                decimal = Integer.parseInt(resultadoFinal.getText());
            }
            
            Vector<String> vec = new Vector<String>();
            while (decimal>0) {
                //int n = decimal%8;
                
                //octal.add(String.valueOf(decimal%8));
                vec.add(String.valueOf(decimal%8));
                decimal = decimal/8;
                System.out.println(decimal);
            }
            
            for (int i = octal.size(); i > 0; i--) {
                //vec.add(octal.get(i-1));
                str += vec.get(i-1);
            }
            
            System.out.println(vec);
            System.out.println(str);
            
            resultadoFinal.setText(str);
            
            
        } catch (ParserException e) {
            textfieldResult.setText("nan");
        }
    }
    
    private String convertidor(int systemNum){
        //LinkedList<String> order = new LinkedList<String>(); 
        Vector<String> order = new Vector<String>();
        String str = "";
        
        try {
            while (decimal>0) {            
            //Guarda los residuos en la posicion 0
            order.add(String.valueOf(decimal%systemNum));
            //Asigna el nuevo resultado
            decimal = decimal/systemNum;
            System.out.println("decimal "+decimal);
            }
        
            //Ordena el resultado ->
            for (int i = order.size(); i > 0; i--) {
                //ordena el resultado en la posicion 1
                //order.get(1).add(order.get(0).get(i-1));
            
                str += order.get(i-1);
                System.out.println(str);
            }
        } catch (Exception e) {
            textfieldResult.setText("nan");
        }
        
        
        System.out.println(str);
        return str;
    }
    
    /**
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        optDecimal.setSelected(true);
    }    
    
}
