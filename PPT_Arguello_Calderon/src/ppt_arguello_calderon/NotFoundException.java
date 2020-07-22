/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ppt_arguello_calderon;

/**
 * Clase para generar una excepción personalizada
 * @author Danip
 * @version 1.0
 * @since 01/07/2020
 */
class NotFoundException extends Exception{
    public NotFoundException() {
        
    }
    public NotFoundException(String message) {
        super(message);
    
    }
}
