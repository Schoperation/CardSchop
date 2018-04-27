package schoperation.cardschop.command;

public interface ICommand {

    /*
        Command interface
     */

    // Command itself
    String command = "";

    // Arguments
    String[] args = new String[4];

    public String getCommand();
    public String[] getArgs();

    public void execute();
}
