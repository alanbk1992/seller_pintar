package project.kimora.sellerpintar.network;

import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;

/**
 * Created by msinfo on 09/02/23.
 */
public class MyRetryPolicyWithoutRetry implements RetryPolicy {
    public int CURRENT_TIME_OUT = 20000;

    @Override
    public int getCurrentTimeout()
    {
        return CURRENT_TIME_OUT;
    }

    @Override
    public int getCurrentRetryCount()
    {
        return 0;
    }

    @Override
    public void retry(VolleyError error) throws VolleyError
    {
        throw(error);
    }
}
