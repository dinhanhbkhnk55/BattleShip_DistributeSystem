package com.dinhanh.battleship.response;

public interface RequestListener {

    public void handleContentPackage(ContentPackage cp);

    public boolean isProcessPackage(DataPackage dataP);
}
