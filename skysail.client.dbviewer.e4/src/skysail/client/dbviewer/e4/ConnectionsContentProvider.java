package skysail.client.dbviewer.e4;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class ConnectionsContentProvider implements ITreeContentProvider {

    private ConnectionsModel model;

    @Override
    public void dispose() {
    }

    @Override
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
      this.model = (ConnectionsModel) newInput;
    }

    @Override
    public Object[] getElements(Object inputElement) {
      return model.getConnections().toArray();
    }

    @Override
    public Object[] getChildren(Object parentElement) {
      if (parentElement instanceof Connection) {
        Connection connection = (Connection) parentElement;
        return connection.getSchemes().toArray();
      }
      return null;
    }

    @Override
    public Object getParent(Object element) {
      return null;
    }

    @Override
    public boolean hasChildren(Object element) {
      if (element instanceof Connection) {
        return true;
      }
      return false;
    }

  } 