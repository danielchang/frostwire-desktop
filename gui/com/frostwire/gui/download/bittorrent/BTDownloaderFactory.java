package com.frostwire.gui.download.bittorrent;

import java.io.File;

import org.gudy.azureus2.core3.download.DownloadManager;
import org.gudy.azureus2.core3.download.impl.DownloadManagerAdapter;
import org.gudy.azureus2.core3.global.GlobalManager;
import org.gudy.azureus2.core3.torrent.TOTorrent;
import org.gudy.azureus2.core3.torrent.TOTorrentException;
import org.gudy.azureus2.core3.util.TorrentUtils;

import com.limegroup.gnutella.SaveLocationException;
import com.limegroup.gnutella.settings.SharingSettings;

public class BTDownloaderFactory {

    private final GlobalManager _globalManager;
    private final File _file;
    
    public BTDownloaderFactory(GlobalManager globalManager, File file) {
        _globalManager = globalManager;
        _file = file;
    }

    public File getSaveFile() {
        return _file;
    }

    public void setSaveFile(File newFile) {
    }
    
    public BTDownloader createDownloader(boolean overwrite) throws SaveLocationException, TOTorrentException {
        
        File saveDir = SharingSettings.TORRENT_DATA_DIR_SETTING.getValue();
        if (!saveDir.exists()) {
            saveDir.mkdirs();
        }

        TOTorrent torrent = TorrentUtils.readFromFile(_file, false);
        
        DownloadManager manager;
        
        if ((manager = _globalManager.getDownloadManager(torrent)) == null) {         
            manager = _globalManager.addDownloadManager(_file.getAbsolutePath(), saveDir.getAbsolutePath());
        }
        
        return createDownloader(manager);
    }

    public static BTDownloader createDownloader(DownloadManager downloadManager) {
        downloadManager.addListener(new DownloadManagerAdapter() {
            @Override
            public void stateChanged(DownloadManager manager, int state) {
                if (state == DownloadManager.STATE_READY) {
                    manager.startDownload();
                } else if (state == DownloadManager.STATE_WAITING) {
                    manager.initialize();
                }
            }
        });

        if (downloadManager.getState() != DownloadManager.STATE_STOPPED) {
            downloadManager.setStateWaiting();
        }
        
        return new BTDownloaderImpl(downloadManager);
    }
}
