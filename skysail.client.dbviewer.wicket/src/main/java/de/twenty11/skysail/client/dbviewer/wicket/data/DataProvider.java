package de.twenty11.skysail.client.dbviewer.wicket.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.codehaus.jackson.type.TypeReference;
import org.restlet.engine.converter.ConverterHelper;
import org.restlet.ext.jackson.JacksonConverter;

import de.twenty11.skysail.common.ext.dbviewer.RestfulData;
import de.twenty11.skysail.common.grids.GridData;
import de.twenty11.skysail.common.grids.RowData;
import de.twenty11.skysail.common.responses.SkysailResponse;
import de.twenty11.skysail.common.utils.MyLocalJacksonCustomConverter;
import de.twenty11.skysail.common.utils.RestletUtils;

public class DataProvider extends SortableDataProvider {

    class SortableDataProviderComparator implements Comparator<Data>, Serializable {
        public int compare(final Data o1, final Data o2) {
            // PropertyModel<Comparable> model1 = new PropertyModel<Comparable>(o1, getSort().getProperty());
            // PropertyModel<Comparable> model2 = new PropertyModel<Comparable>(o2, getSort().getProperty());

            int result = 0;// model1.getObject().compareTo(model2.getObject());

            if (!getSort().isAscending()) {
                result = -result;
            }

            return result;
        }
    }

    private List<Data2> list = new ArrayList<Data2>();

    private SortableDataProviderComparator comparator = new SortableDataProviderComparator();

    public DataProvider() {
        // The default sorting
        setSort("first", SortOrder.ASCENDING);

        ConverterHelper myLocalJacksonConverter = new MyLocalJacksonCustomConverter(
                new TypeReference<SkysailResponse<GridData>>() {
                });

        RestletUtils.replaceConverter(JacksonConverter.class, myLocalJacksonConverter);

        try {
            RestfulData restfulData = new DataProxy().getRestfulData();
            SkysailResponse<GridData> data = restfulData.getData();

            if (data != null) {
                GridData gridData = data.getData();
                if (gridData != null) {
                    for (RowData row : gridData.getRows()) {
                        Data2 data2 = new Data2(row.getCells());
                        list.add(data2);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Iterator<Data2> iterator(final int first, final int count) {
        // Get the data
        List<Data2> newList = new ArrayList<Data2>(list);

        // Collections.sort(newList, comparator);

        return newList.subList(first, first + count).iterator();
    }

    public IModel<Data2> model(final Object object) {
        return new AbstractReadOnlyModel<Data2>() {
            @Override
            public Data2 getObject() {
                return (Data2) object;
            }
        };
    }

    public int size() {
        return list.size();
    }

}

class Data implements Serializable {

    private final String name;

    public Data(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Data2 extends HashMap<String, String> implements Serializable {

    public Data2(Map<String, String> cells) {
        super(cells);
    }

}
