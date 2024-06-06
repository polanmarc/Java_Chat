package exception;

/**
 * Una excepcion personalizada que puede llevar un código asociado
 */
public class CustomException extends Exception {

    private static final long serialVersionUID = 1L;
    protected String codi;

    /**
     * Crea una instancia de CustomException sin ningún detalle
     */
    public CustomException() {
        super();
    }

    /**
     * Crea una instancia de CustomException con un mensaje descriptivo
     * 
     * @param message El mensaje descriptivo de la excepcion
     */
    public CustomException(String message) {
        super(message);
    }

    /**
     * Crea una instancia de CustomException con una causa subyacente
     * 
     * @param cause La causa subyacente de la excepcion
     */
    public CustomException(Throwable cause) {
        super(cause);
    }

    /**
     * Crea una instancia de CustomException con un código y un mensaje descriptivo
     * 
     * @param codi    El código asociado a la excepcion
     * @param message El mensaje descriptivo de la excepcion
     */
    public CustomException(String codi, String message) {
        super(message);
        this.codi = codi;
    }

    /**
     * Crea una instancia de CustomException con un mensaje y una causa subyacente
     * 
     * @param message El mensaje descriptivo de la excepcion
     * @param cause   La causa subyacente de la excepcion
     */
    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Crea una instancia de CustomException con un mensaje, una causa subyacente,
     * y opciones de supresión y trazabilidad
     * 
     * @param message            El mensaje descriptivo de la excepcion
     * @param cause              La causa subyacente de la excepcion
     * @param enableSuppression  Indica si la supresión de excepciones está
     *                           habilitada o no
     * @param writableStackTrace Indica si el rastreo de la pila debe ser
     *                           grabable o no
     */
    public CustomException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * Obtiene el código asociado a la excepcion
     * 
     * @return El código asociado a la excepcion
     */
    public String getCodi() {
        return codi;
    }

    /**
     * Establece el código asociado a la excepcion
     * 
     * @param codi El código asociado a la excepcion
     */
    public void setCodi(String codi) {
        this.codi = codi;
    }

}
