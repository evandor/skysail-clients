package skysail.client.dbviewer.e4;

import java.util.ArrayList;
import java.util.List;

public class Connection {
    private String name;
    private int sort;
    private List<Schema> schemes = new ArrayList<Schema>();

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public int getSort() {
      return sort;
    }

    public void setSort(int sort) {
      this.sort = sort;
    }

    public List<Schema> getSchemes() {
        SchemesModel schemesModel = new SchemesModel();
        return schemesModel.getSchemes(this);
    }
  } 
