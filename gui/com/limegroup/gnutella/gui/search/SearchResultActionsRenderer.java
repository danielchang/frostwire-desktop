/*
 * Created by Angel Leon (@gubatron), Alden Torres (aldenml)
 * Copyright (c) 2011, 2012, FrostWire(R). All rights reserved.
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

package com.limegroup.gnutella.gui.search;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;

import com.frostwire.gui.AlphaIcon;
import com.frostwire.gui.player.MediaPlayer;
import com.frostwire.search.CrawlableSearchResult;
import com.frostwire.search.StreamableSearchResult;
import com.limegroup.gnutella.gui.GUIMediator;
import com.limegroup.gnutella.gui.I18n;

/**
 * 
 * @author gubatron
 * @author aldenml
 * 
 */
public final class SearchResultActionsRenderer extends FWAbstractJPanelTableCellRenderer {
    private final static float BUTTONS_TRANSPARENCY = 0.35f;
    private final static ImageIcon play_solid;
    private final static AlphaIcon play_transparent;
    private final static ImageIcon download_solid;
    private final static AlphaIcon download_transparent;
    private final static ImageIcon details_solid;
    private final static AlphaIcon details_transparent;

    private JLabel labelPlay;
    private JLabel labelPartialDownload;
    private JLabel labelDownload;
    private UISearchResult searchResult;
    private boolean showSolid;
    private SearchResultActionsHolder actionsHolder;

    static {
        play_solid = GUIMediator.getThemeImage("search_result_play_over");
        play_transparent = new AlphaIcon(play_solid, BUTTONS_TRANSPARENCY);
        
        download_solid = GUIMediator.getThemeImage("search_result_download_over");
        download_transparent = new AlphaIcon(download_solid, BUTTONS_TRANSPARENCY);
        
        details_solid = GUIMediator.getThemeImage("search_result_details_over");
        details_transparent = new AlphaIcon(details_solid, BUTTONS_TRANSPARENCY);
    }
    
    public SearchResultActionsRenderer() {
        setupUI();
    }
    
    private void setupUI() {
        setLayout(new GridBagLayout());
        GridBagConstraints c;

        labelPlay = new JLabel(play_transparent);
        labelPlay.setToolTipText(I18n.tr("Play/Preview"));
        labelPlay.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                labelPlay_mouseReleased(e);
            }
        });
        c = new GridBagConstraints();
        c.gridx = GridBagConstraints.RELATIVE;
        c.ipadx = 3;
        add(labelPlay, c);

        labelDownload = new JLabel(download_transparent);
        labelDownload.setToolTipText(I18n.tr("Download"));
        labelDownload.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                labelDownload_mouseReleased(e);
            }
        });
        c = new GridBagConstraints();
        c.gridx = GridBagConstraints.RELATIVE;
        c.ipadx = 3;
        add(labelDownload, c);
        
        labelPartialDownload = new JLabel(details_solid);
        labelPartialDownload.setToolTipText(I18n.tr("Select content to download from this torrent."));
        labelPartialDownload.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                labelPartialDownload_mouseReleased(e);
            }
        });
        c = new GridBagConstraints();
        c.gridx = GridBagConstraints.RELATIVE;
        c.ipadx = 3;
        add(labelPartialDownload, c);
        
        setEnabled(true);
    }
    
    @Override
    protected void updateUIData(Object dataHolder, JTable table, int row, int column) {
        updateUIData((SearchResultActionsHolder) dataHolder, table, row, column);
    }
    
    private void updateUIData(SearchResultActionsHolder value, JTable table, int row, int column) {
        actionsHolder = value;
        searchResult = actionsHolder.getSearchResult();
        showSolid = mouseIsOverRow(table, row);
        updatePlayButton();
        boolean playable = false;
        
        if (searchResult.getSearchResult() instanceof StreamableSearchResult) {
            playable = ((StreamableSearchResult) searchResult.getSearchResult()).getStreamUrl() != null;
        }
        
        labelPlay.setVisible(playable);
        labelDownload.setIcon(showSolid ? download_solid : download_transparent);
        labelDownload.setVisible(true);
        labelPartialDownload.setIcon(showSolid ? details_solid : details_transparent);
        labelPartialDownload.setVisible(searchResult.getSearchResult() instanceof CrawlableSearchResult);
    }

    private void updatePlayButton() {
        labelPlay.setIcon((isStreamableSourceBeingPlayed(searchResult)) ? GUIMediator.getThemeImage("speaker") : (showSolid) ? play_solid : play_transparent);
    }


    private void labelPlay_mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            if (searchResult.getSearchResult() instanceof StreamableSearchResult && !isStreamableSourceBeingPlayed(searchResult)) {
                searchResult.play();
                updatePlayButton();
            }
        }
    }

    private void labelPartialDownload_mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            if (searchResult.getSearchResult() instanceof CrawlableSearchResult) {
                searchResult.download(true);
            }
        }
    }

    private void labelDownload_mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            searchResult.download(false);
        }
    }
 
    private boolean isStreamableSourceBeingPlayed(UISearchResult sr) {
        if (!(sr instanceof StreamableSearchResult)) {
            return false;
        }

        StreamableSearchResult ssr = (StreamableSearchResult) sr;
        return MediaPlayer.instance().isThisBeingPlayed(ssr.getStreamUrl());
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        updatePlayButton();
    }
}