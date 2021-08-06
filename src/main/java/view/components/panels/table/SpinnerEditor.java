package view.components.panels.table;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.EventObject;

public class SpinnerEditor extends DefaultCellEditor {
    private final JSpinner spinner;
    private final JSpinner.DefaultEditor editor;
    private final JTextField textField;
    private boolean valueSet;

    public SpinnerEditor(boolean isDecimalValue) {
        super(new JTextField());
        if (isDecimalValue) {
            spinner = new JSpinner(new SpinnerNumberModel(0.0, null, null, 1));
        } else {
            spinner = new JSpinner();
        }

        editor = ((JSpinner.DefaultEditor) spinner.getEditor());
        textField = editor.getTextField();

        textField.addActionListener(ae -> stopCellEditing());
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (!valueSet) {
            spinner.setValue(value);
        }
        SwingUtilities.invokeLater(textField::requestFocus);
        return spinner;
    }

    @Override
    public boolean isCellEditable(EventObject eo) {
        if (eo instanceof KeyEvent) {
            KeyEvent ke = (KeyEvent) eo;
            textField.setText(String.valueOf(ke.getKeyChar()));
            valueSet = true;
        } else {
            valueSet = false;
        }
        return true;
    }

    @Override
    public Object getCellEditorValue() {
        return spinner.getValue();
    }

    @Override
    public boolean stopCellEditing() {
        try {
            editor.commitEdit();
            spinner.commitEdit();
        } catch (java.text.ParseException e) {
            JOptionPane.showMessageDialog(null,
                    "Invalid value, discarding.");
        }
        return super.stopCellEditing();
    }
}
