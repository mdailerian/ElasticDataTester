
/**
 * Created by MMAA-local on 12/23/2015.
 */
//import com.fasterxml.jackson.core.JsonFactory;
//import com.fasterxml.jackson.core.JsonParser;
//import jdk.nashorn.internal.ir.ObjectNode;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
//import org.elasticsearch.index.mapper.object.ObjectMapper;
//import org.elasticsearch.node.Node;
//import org.elasticsearch.node.NodeBuilder;

//import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
//            http://teknosrc.com/elasticsearch-java-api-get-index-list/
//            http://teknosrc.com/execute-raw-elasticsearch-query-using-transport-client-java-api/
//            http://www.javacodegeeks.com/2013/04/getting-started-with-elasticsearch.html
//            http://docs.spring.io/spring-data/elasticsearch/docs/1.0.0.M1/reference/html/elasticsearch.repositories.html
//      http://stackoverflow.com/questions/33909389/how-to-write-code-for-search-in-elasticsearch-using-jest-client-in-java

//SEE RESULTS: examine sample data with Sense - http://localhost:5601/app/sense
// JAVADOCS: http://javadoc.kyubu.de/elasticsearch/HEAD/allclasses-noframe.html

public class TestDriver {

    public IndexResponse indexDocument( Client client) throws IOException{
        // create an index, stick some data
            IndexResponse response = client.prepareIndex("dailema2", "test" )
                    .setSource(jsonBuilder()
                                    .startObject()
                                    .field("user", "jenga")
                                    .field("postDate", new Date())
                                    .field("message", "Document7")
                                    .endObject()
                    )
                    .execute()
                    .actionGet();

        return response;
    }
    public static void main(String[] args) {
        System.out.println( "Driver Started...");

        Client client = null;
        try {
            client = TransportClient.builder().build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
//                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("host2"), 9300));


            System.out.println( "Client " + client.toString());

            //JsonParser parser = new JsonFactory()
            //        .createParser(new File("C:\\Users\\MMAA-local\\Desktop\\Marti dev\\moviedata.json"));
//            System.out.println( "JSON File " + parser );

            TestDriver td = new TestDriver();
            response = td.indexDocument( client );

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // on shutdown
        client.close();
    }
}
