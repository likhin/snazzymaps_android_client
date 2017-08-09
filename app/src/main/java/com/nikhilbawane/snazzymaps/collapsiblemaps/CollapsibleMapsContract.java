package com.nikhilbawane.snazzymaps.collapsiblemaps;

import com.nikhilbawane.snazzymaps.BasePresenter;
import com.nikhilbawane.snazzymaps.BaseView;
import com.nikhilbawane.snazzymaps.model.Style;

import java.util.List;

/**
 * Created by Nikhil on 08-08-2017.
 */

public interface CollapsibleMapsContract {

    interface View extends BaseView<Presenter> {

        void initializeRecyclerViewAdapter(List<Style> styles);

    }

    interface Presenter extends BasePresenter {
        void foo();
    }
}
