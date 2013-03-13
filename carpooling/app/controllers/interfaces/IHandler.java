package controllers.interfaces;

/**
 * S'occupe d'executer les actions prevues quand un timer est "fini".
 */
public interface IHandler{
	public void execute(Object ... objs);
}