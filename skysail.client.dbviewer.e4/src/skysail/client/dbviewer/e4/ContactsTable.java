 
package skysail.client.dbviewer.e4;

import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

public class ContactsTable {
    
    private final TableViewer connectionsViewer;
    
	@Inject
	public ContactsTable(Composite parent) {
	 // Table composite (because of TableColumnLayout)
        final Composite tableComposite = new Composite(parent, SWT.NONE);
        tableComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
                true));
        final TableColumnLayout tableColumnLayout = new TableColumnLayout();
        tableComposite.setLayout(tableColumnLayout);

        // Table viewer
        connectionsViewer = new TableViewer(tableComposite, SWT.FULL_SELECTION);
        connectionsViewer.getTable().setHeaderVisible(true);
        // connectionsViewer.getTable().setLinesVisible(true);
        //connectionsViewer.setComparator(new ContactViewerComparator());

        connectionsViewer
                .addSelectionChangedListener(new ISelectionChangedListener() {
                    public void selectionChanged(SelectionChangedEvent event) {
                        IStructuredSelection selection = (IStructuredSelection) event
                                .getSelection();
                        //selectionService.setSelection(selection.getFirstElement());
                    }
                });

        // First name column
        final TableViewerColumn firstNameColumn = new TableViewerColumn(
                connectionsViewer, SWT.NONE);
        firstNameColumn.getColumn().setText("First Name");
        tableColumnLayout.setColumnData(firstNameColumn.getColumn(),
                new ColumnWeightData(40));

        // Last name column
        final TableViewerColumn lastNameColumn = new TableViewerColumn(
                connectionsViewer, SWT.NONE);
        lastNameColumn.getColumn().setText("Last Name");
        tableColumnLayout.setColumnData(lastNameColumn.getColumn(),
                new ColumnWeightData(60));

        ObservableListContentProvider contentProvider = new ObservableListContentProvider();

        connectionsViewer.setContentProvider(contentProvider);

        IObservableMap[] attributes = BeansObservables.observeMaps(
                contentProvider.getKnownElements(), Contact.class,
                new String[] { "firstName", "lastName" });
        connectionsViewer.setLabelProvider(new ObservableMapLabelProvider(
                attributes));

        connectionsViewer.setInput(ContactsRepositoryFactory.getContactsRepository().getAllContacts());

        GridLayoutFactory.fillDefaults().generateLayout(parent);
	}
	
	@PreDestroy
    void preDestroy() {
        for (Object object : ContactsRepositoryFactory
                .getContactsRepository().getAllContacts()) {
            Contact contact = (Contact) object;
            Image image = contact.getImage();
            if (image != null) {
                image.dispose();
            }
        }
    }
    
    @Focus
    void setFocus() {
        connectionsViewer.getControl().setFocus();
    }
	
	
	@Persist
	public void save() {
		//TODO Your code here
	}
	
}