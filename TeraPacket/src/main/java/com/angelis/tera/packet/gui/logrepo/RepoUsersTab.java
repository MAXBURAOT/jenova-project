package com.angelis.tera.packet.gui.logrepo;

import java.awt.Component;
import java.util.EventObject;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

import com.angelis.tera.packet.logrepo.RepoUser;


@SuppressWarnings("serial")
public class RepoUsersTab extends JPanel
{
    private JTable _userTable;
    private UserTableModel _userTableModel;
    
    public RepoUsersTab()
    {
        _userTableModel = new UserTableModel();
        _userTableModel.addColumn(new TableColumn(0,75,null,new UserTableCellEditor()));
        _userTable = new JTable();
        _userTable.setColumnModel(_userTableModel);
        //this.add(_userTable);
    }
    
    public void addUsers(List<RepoUser> users)
    {
        // TODO...
    }
    
    public void addUser(RepoUser user)
    {
        // TODO...
    }
    
    public void removeUser(RepoUser user)
    {
        // TODO
    }
    
    private class UserTableModel extends DefaultTableColumnModel
    {
        
    }
    
    private class UserTableCellEditor implements TableCellEditor
    {

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
        {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void addCellEditorListener(CellEditorListener l)
        {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void cancelCellEditing()
        {
            // TODO Auto-generated method stub
            
        }

        @Override
        public Object getCellEditorValue()
        {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public boolean isCellEditable(EventObject anEvent)
        {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public void removeCellEditorListener(CellEditorListener l)
        {
            // TODO Auto-generated method stub
            
        }

        @Override
        public boolean shouldSelectCell(EventObject anEvent)
        {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean stopCellEditing()
        {
            // TODO Auto-generated method stub
            return false;
        }
        
    }
}