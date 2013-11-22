package de.twenty11.skysail.products.notes.client.rcp.handlers;

import javax.annotation.PostConstruct;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public class BrowserPart {
	private Text text;
	private Browser browser;

	@PostConstruct
	public void createControls(Composite parent) {
		parent.setLayout(new GridLayout(2, false));

		//text = new Text(parent, SWT.BORDER);
//		text.setMessage("Enter City");
//		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

//		Button button = new Button(parent, SWT.PUSH);
//		button.setText("Search");
//		button.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				browser.setUrl("http://localhost:2015");
//			}
//		});

		browser = new Browser(parent, SWT.NONE);
		browser.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		browser.setUrl("http://localhost:8080/app-web/");

	}

}