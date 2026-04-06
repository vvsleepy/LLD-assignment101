// A POJO (Plain Old Java Object) is simply an object that holds data 
// and isn't tied to any special frameworks. It just acts as a carrier for our data.
public class Request {
    private String clientId;
    private String headerData; // Any theoretical payload 

    public Request(String clientId, String headerData) {
        this.clientId = clientId;
        this.headerData = headerData;
    }

    public String getClientId() { return clientId; }
    public String getHeaderData() { return headerData; }
}
