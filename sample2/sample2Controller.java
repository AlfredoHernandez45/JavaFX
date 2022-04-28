/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package sample2;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
    //private Object textFieldNumbers;
    
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
    * 
    */
    @FXML
    void getLasResult(ActionEvent event) {
        if(!(lasOperation == 0)){
            textfieldResult.clear();
            textfieldResult.setText(String.valueOf(lasOperation));
        }

    }

    @FXML
    void resultadoOperacion(ActionEvent event) {
        
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");
        
        try {
            //evalua las operaciones, en caso de tener una "x" replazarlo por un "*" para la multiplicacion
            Object operation = engine.eval(textfieldResult.getText().replaceAll("x", "*"));
            resultadoFinal.setText("" + operation );// resultado se muestra en el TextField resultados
            lasOperation = Double.parseDouble(resultadoFinal.getText());//guarda el resultado de la ultima operacion
        } catch (ScriptException | ParserException e) {
            textfieldResult.setText("nan");
        }
    }
    
    @FXML
    void cambiarSigno(ActionEvent event) {
        
        int number = Integer.parseInt(resultadoFinal.getText());//Obtiene el resultado
        number = number*(-1);//se convierte a negativo o positivo
        resultadoFinal.setText(String.valueOf(number));
        
    }
    
    
    
    /**
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
