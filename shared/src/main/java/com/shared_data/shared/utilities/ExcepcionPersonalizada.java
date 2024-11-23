package com.shared_data.shared.utilities;

public class ExcepcionPersonalizada extends Exception {
    
    public ExcepcionPersonalizada(){
        super("valio hamburguesa con queso el proceso");
    }

    public ExcepcionPersonalizada(String mensaje){
        super(mensaje);
    }

    public ExcepcionPersonalizada(String mensaje, Throwable causa){
        super(mensaje,causa);
    }
}
