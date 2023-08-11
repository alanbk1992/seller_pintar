package project.kimora.sellerpintar.utils;

import project.kimora.sellerpintar.models.ModelStoreSessionData;
import project.kimora.sellerpintar.models.ModelUser;

/**
 * Created by msinfo on 09/02/23.
 */

public class VersionData {
    private ModelStoreSessionData store;

    public VersionData(ModelStoreSessionData store) {
        this.store = store;
    }

    public boolean checkValidDataUser() {
        ModelUser modelUser = store.getUser();
        if(modelUser.GoogleUserID == null)
            modelUser.GoogleUserID = "";
        if(modelUser.GooglePhoto == null)
            modelUser.GooglePhoto = "";
        if(modelUser.Password == null)
            modelUser.Password = "";
        modelUser.version = Constants.userVersion;
        store.storeUser(GSON.toJsonString(modelUser));
        return  true;
    }
}
