/**
 * The Orchestrator receives initial traffic from the client.
 * It strictly coordinates requests out to the RemoteResource.
 * It DOES NOT KNOW that it is securely wrapped in a Rate Limiting Proxy.
 */
public class Orchestrator {
    private RemoteResource apiResource;

    public Orchestrator(RemoteResource apiResource) {
        this.apiResource = apiResource;
    }

    public String routeRequest(Request request) {
        // The orchestrator just calls for data natively.
        // It has no idea the Proxy layer is doing the heavy security lifting.
        return apiResource.fetchData(request);
    }
}
