package fr.usmb.isc.m2;

/**
 * La classe <code>ExpressionInvalide</code> est levee lorsque l'expression a
 * evaluer n'est pas correcte du point de vue syntaxique.
 */
public class ExpressionInvalide extends Exception
{
	
	private static final long serialVersionUID = -8385644355249806406L;

	public ExpressionInvalide () {}

	public ExpressionInvalide ( Exception cause ) {
		super( cause );
	}

	public ExpressionInvalide ( String message ) {
		super( message );
	}
}
