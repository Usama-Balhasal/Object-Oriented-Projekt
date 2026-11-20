package commands;
import java.util.ArrayDeque;
import java.util.Deque;

/** LABB4: implementera historiken. Fält och metodsignaturer är givna. */
public final class History
  {
  private final Deque<Command> undoStack = new ArrayDeque<>();
  private final Deque<Command> redoStack = new ArrayDeque<>();

  public void execute(Command command)
    {
	  command.execute();
      undoStack.push(command);
      redoStack.clear();
    }

  public void undo()
    {
	  if (undoStack.isEmpty()) {
          return;
      }
      Command command = undoStack.peek();
      command.undo();

      undoStack.pop();
      redoStack.push(command);
	  
    }

  public void redo()
    {
      if (redoStack.isEmpty()) {
          return;
      }
      Command command = redoStack.peek();
      command.execute();

      redoStack.pop();
      undoStack.push(command);
    }

  public int undoCount()
    {
	  return undoStack.size();
    }

  public int redoCount()
    {
	  return redoStack.size();
    }
  }
