package presentationLayer;

import java.awt.Component;
import java.awt.event.ActionListener;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;

public class JButtonEditor extends DefaultCellEditor {
	protected JRowButton button;
    private String label;
    public JButtonEditor(ActionListener listener) {
        super(new JCheckBox());
        button = new JRowButton();
        button.setOpaque(true);
        button.addActionListener(listener);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        if (isSelected) {
            button.setForeground(table.getSelectionForeground());
            button.setBackground(table.getSelectionBackground());
        } else {
            button.setForeground(table.getForeground());
            button.setBackground(table.getBackground());
        }
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        button.setRow(row);
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        return label;
    }

    @Override
    public boolean stopCellEditing() {
        return super.stopCellEditing();
    }
}
