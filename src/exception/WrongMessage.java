package exception;

/**
 * Una excepcion que se utiliza cuando se detecta un mensaje incorrecto
 * Extiende de CustomException para permitir la asignación de un código personalizado
 */
public class WrongMessage extends CustomException {

    private static final long serialVersionUID = 1L;

    /**
     * Crea una instancia de WrongMessage con un código y un mensaje descriptivo
     * 
     * @param pCodi   El código asociado al error de mensaje incorrecto
     * @param message El mensaje descriptivo del error
     */
    public WrongMessage(String pCodi, String message) {
        super(pCodi, message);
    }
}
