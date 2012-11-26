 
package skysail.client.dbviewer.e4;

import javax.annotation.PostConstruct;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;


public class ConnectionsTree {
    
    public static final String ID = "de.vogella.jface.treeviewer.view";
    private TreeViewer viewer;

    @PostConstruct
    public void createPartControl(Composite parent) {
      viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
      viewer.setContentProvider(new TodoContentProvider());
      viewer.setLabelProvider(new TodoLabelProvider());
      //viewer.setAutoExpandLevel(2);
      // Provide the input to the ContentProvider
      viewer.setInput(new ConnectionsModel());

      // Add a doubleclicklistener
      viewer.addDoubleClickListener(new IDoubleClickListener() {

        @Override
        public void doubleClick(DoubleClickEvent event) {
          TreeViewer viewer = (TreeViewer) event.getViewer();
          IStructuredSelection thisSelection = (IStructuredSelection) event
              .getSelection();
          Object selectedNode = thisSelection.getFirstElement();
          viewer.setExpandedState(selectedNode,
              !viewer.getExpandedState(selectedNode));
        }
      });

      viewer.getTree().addKeyListener(new KeyAdapter() {
        @Override
        public void keyReleased(final KeyEvent e) {
          if (e.keyCode == SWT.DEL) {
            final IStructuredSelection selection = (IStructuredSelection) viewer
                .getSelection();
            if (selection.getFirstElement() instanceof Schema) {
              Schema o = (Schema) selection.getFirstElement();
              // TODO Delete the selected element from the model
            }

          }
        }
      });

    }

    @Focus
    public void setFocus() {
      viewer.getControl().setFocus();
    }
	
}