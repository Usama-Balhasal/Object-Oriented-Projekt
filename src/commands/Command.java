package commands;

/** En redan validerad ändring. Historiken sköter ordningen på anropen. */
public interface Command
  {
  void execute();
  void undo();
  }
