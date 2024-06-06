package exception;

/**
 * Una excepción que se utiliza cuando se encuentra un usuario incorrecto
 * Extiende de CustomException para permitir la asignación de un código personalizado
 */
public class WrongUser extends CustomException {

    private static final long serialVersionUID = 1L;

    /**
     * Crea una instancia de WrongUser con un código y un mensaje descriptivo
     * 
     * @param pCodi   El código asociado al error de usuario incorrecto
     * @param message El mensaje descriptivo del error
     */
    public WrongUser(String pCodi, String message) {
        super(pCodi, message);
    }
}

