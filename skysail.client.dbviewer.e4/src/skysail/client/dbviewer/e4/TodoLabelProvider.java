package skysail.client.dbviewer.e4;

import java.net.URL;


import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.FrameworkUtil;

public class TodoLabelProvider extends LabelProvider {
    
    private static final Image FOLDER = getImage("folder.gif");
    private static final Image FILE = getImage("file.gif");
    
    
    @Override
    public String getText(Object element) {
      if (element instanceof Connection) {
        Connection category = (Connection) element;
        return category.getName();
      }
      return ((Schema) element).getSummary();
    }

    @Override
    public Image getImage(Object element) {
      if (element instanceof Connection) {
        return FOLDER;
      }
      return FILE;
    }

    // Helper Method to load the images
    private static Image getImage(String file) {
        return null;
//      Bundle bundle = FrameworkUtil.getBundle(TodoLabelProvider.class);
//      URL url = FileLocator.find(bundle, new Path("icons/" + file), null);
//      ImageDescriptor image = ImageDescriptor.createFromURL(url);
//      return image.createImage();

    } 
  } 