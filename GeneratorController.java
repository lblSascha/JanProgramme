package picture_generator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GeneratorController implements ActionListener {

	private PictureGen_GUI picGen;

	public GeneratorController(PictureGen_GUI view) {
		this.picGen = view;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Speichern")) {
			PictureGen_GUI.saveCanvas(picGen);
		}
	}
}
