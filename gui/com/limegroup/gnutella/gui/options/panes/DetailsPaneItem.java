/*
 * Created by Angel Leon (@gubatron), Alden Torres (aldenml)
 * Copyright (c) 2011, 2012, FrostWire(TM). All rights reserved.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.limegroup.gnutella.gui.options.panes;

import java.io.IOException;

import javax.swing.JCheckBox;

import org.limewire.i18n.I18nMarker;

import com.limegroup.gnutella.gui.BoxPanel;
import com.limegroup.gnutella.gui.I18n;
import com.limegroup.gnutella.gui.LabeledComponent;
import com.limegroup.gnutella.settings.SearchSettings;

/**
 * 
 * @author gubatron
 * @author aldenml
 * 
 */
public class DetailsPaneItem extends AbstractPaneItem {

    public final static String TITLE = I18n.tr("Details Page");

    public final static String DETAILS = I18n.tr("Show details web page after a download starts.");

    private final JCheckBox DETAILS_CHECK_BOX = new JCheckBox();

    public DetailsPaneItem() {
        super(TITLE, "");

        BoxPanel panel = new BoxPanel();
        LabeledComponent comp = new LabeledComponent(I18nMarker.marktr(DETAILS), DETAILS_CHECK_BOX, LabeledComponent.LEFT_GLUE, LabeledComponent.LEFT);
        panel.add(comp.getComponent());

        add(panel);
    }

    @Override
    public boolean applyOptions() throws IOException {
        SearchSettings.SHOW_DETAIL_PAGE_AFTER_DOWNLOAD_START.setValue(DETAILS_CHECK_BOX.isSelected());
        return false;
    }

    @Override
    public void initOptions() {
        DETAILS_CHECK_BOX.setSelected(SearchSettings.SHOW_DETAIL_PAGE_AFTER_DOWNLOAD_START.getValue());
    }

    @Override
    public boolean isDirty() {
        if (SearchSettings.SHOW_DETAIL_PAGE_AFTER_DOWNLOAD_START.getValue() != DETAILS_CHECK_BOX.isSelected()) {
            return true;
        }

        return false;
    }
}
