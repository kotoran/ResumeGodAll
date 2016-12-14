package com.ajemian.resumegod;

/**
 * Created by Kudo on 10/9/16.
 */

public interface OnRequestListener {
    public enum RequestType{
        Auth,
        UploadImage,
        CreateJobSeekerProfile,
        CreateEmployerProfile,
        RetrieveAllJobSeekers,
        CreateDesignation,
        GetJobSeekerProfile,
        GetAllJobSeekerProfiles
    }

    public void onResponseReceived(RequestType type, Response response);
    public void onResponsesReceived(RequestType type, int resCode, Response[] responses);


}
