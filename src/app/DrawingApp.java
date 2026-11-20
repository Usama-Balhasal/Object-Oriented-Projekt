package app;
import model.DrawingDocument;
import model.Selection;
import tools.CircleTool;
import tools.MoveTool;
import tools.RotateTool;
import java.awt.BorderLayout;
import javax.swing.*;
import commands.History;
import commands.GroupCommand;

/** Kopplingspunkt för dokument, verktyg och vy. */
public final class DrawingApp
  {
  private static boolean canSplit(figures.Figure figure)
    {
    return !figure.parts().isEmpty();
    }

  public static void main(String[] args)
    {
    SwingUtilities.invokeLater(() ->
      {
      var document = new DrawingDocument(DemoContent.create());
      var selection = new Selection();
      var panel = new DrawingPanel(document, selection);
      var history = new History();
      var toolbar = new JPanel();
      var status = new JLabel("Verktyg: Flytta");
      JButton move = new JButton("Flytta / markera");
      JButton circle = new JButton("Cirkel");
      move.addActionListener(event ->
        {
        panel.setTool(new MoveTool(document, selection, history));
        status.setText("Verktyg: Flytta");
        });
      circle.addActionListener(event ->
        {
        panel.setTool(new CircleTool(document, history));
        status.setText("Verktyg: Cirkel");
        });
      toolbar.add(move);
      toolbar.add(circle);
      JButton group = new JButton("Gruppera");
      JButton split = new JButton("Dela upp");
      group.addActionListener(event ->
        {
        panel.cancelGesture();
        if (selection.figures().size() >= 2)
          history.execute(new GroupCommand(document, selection, false));
        panel.repaint();
        });
      split.addActionListener(event ->
        {
        panel.cancelGesture();
        if (selection.figures().stream().anyMatch(DrawingApp::canSplit))
          history.execute(new GroupCommand(document, selection, true));
        panel.repaint();
        });
      toolbar.add(group);
      toolbar.add(split);
      JButton undo = new JButton("Ångra");
      JButton redo = new JButton("Gör om");
      undo.addActionListener(event ->
        {
        panel.cancelGesture();
        history.undo();
        selection.clear();
        panel.repaint();
        });
      redo.addActionListener(event ->
        {
        panel.cancelGesture();
        history.redo();
        selection.clear();
        panel.repaint();
        });
      toolbar.add(undo);
      toolbar.add(redo);
      JButton rotate = new JButton("Rotera");
      rotate.setToolTipText("Markera en figur eller grupp och dra runt dess centrum. Shift-klick bygger markering.");
      rotate.addActionListener(event ->
        {
        panel.setTool(new RotateTool(document, selection, history));
        status.setText("Rotera: dra runt markerade figurers centrum; Shift-klick markerar flera. Esc avslutar.");
        });
      toolbar.add(rotate);
      panel.setTool(new MoveTool(document, selection, history));
      JFrame frame = new JFrame("DVG345 – labb 4");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.add(toolbar, BorderLayout.NORTH);
      frame.add(panel, BorderLayout.CENTER);
      frame.add(status, BorderLayout.SOUTH);
      frame.pack();
      frame.setLocationByPlatform(true);
      frame.setVisible(true);
      });
    }
  }
