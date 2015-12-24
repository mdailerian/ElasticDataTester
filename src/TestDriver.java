
/**
 * Created by MMAA-local on 12/23/2015.
 */
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

public class TestDriver {
// http://stackoverflow.com/questions/33909389/how-to-write-code-for-search-in-elasticsearch-using-jest-client-in-java
    public static void main(String[] args) {
        System.out.println( "Driver Started...");

        Client client = null;
        try {
            client = TransportClient.builder().build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
//                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("host2"), 9300));

            System.out.println( "Client " + client );

            // create an index, stick some data
            IndexResponse response = client.prepareIndex("twitter", "tweet", "1")
                    .setSource(jsonBuilder()
                                    .startObject()
                                    .field("user", "kimchy")
                                    .field("postDate", new Date())
                                    .field("message", "trying out Elastic     Search")
                                    .endObject()
                    )
                    .execute()
                    .actionGet();

            // query - basic example
            // analysis
            // suggestions

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // on shutdown
        client.close();
    }
}
