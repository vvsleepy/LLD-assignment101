/**
 * A concrete class simulating an external rate-limited API that costs 
 * money. If traffic hits this point, our internal system is 
 * officially communicating outward.
 */
public class ExternalApiService implements RemoteResource {
    
    public String fetchData(Request request) {
        // Pretend this reaches out to a remote server.
        return "SUCCESS 200: Successfully fetched remote data for client: " + request.getClientId();
    }
}
